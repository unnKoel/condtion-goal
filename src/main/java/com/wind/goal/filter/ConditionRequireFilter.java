package com.wind.goal.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.d1xn.common.base.FileCache;
import com.d1xn.common.json.JSONException;
import com.d1xn.common.json.JSONListObj;
import com.d1xn.common.json.JSONObject;
import com.d1xn.common.log.Log;
import com.d1xn.common.rest.TableVO;
import com.d1xn.common.util.JSONObjUtil;
import com.d1xn.common.vo.JSONResultVO;
import com.d1xn.common.vo.ResultVO;
import com.d1xn.ddal.client.base.DDALAddOperator;
import com.d1xn.ddal.client.base.DDALOperator;
import com.d1xn.ddal.client.base.DDALUpdateOperator;
import com.d1xn.ddal.client.socket.AsynDDALHelper;
import com.d1xn.ddal.client.socket.ddal.Callback;
import com.d1xn.ddal.client.socket.ddal.ResListCallback;
import com.wind.goal.comparator.ParamComparator.CompareParamVO;
import com.wind.goal.res.PlatformConditionDefine;
import com.wind.goal.res.UserCondition;
import com.wind.goal.res.UserConditionId;
import com.wind.goal.vo.ParamterVO;

/**
 * 达成要求条件收集
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class ConditionRequireFilter extends ConditionFilter {
	private AsynDDALHelper asynHelper;
	private ConditionDefineCache conditionDefineCache; // 条件定义缓存

	public ConditionRequireFilter(String conditionFilePath) {
		conditionDefineCache = new ConditionDefineCache(conditionFilePath);
	}

	public AsynDDALHelper getAsynHelper() {
		return asynHelper;
	}

	public void setAsynHelper(AsynDDALHelper asynHelper) {
		this.asynHelper = asynHelper;
	}

	@Override
	public void filte(Object conditions) {
		/**
		 * 获取所属条件类别的条件组
		 */
		@SuppressWarnings("unchecked")
		final List<Object> filtedConditionIds = (List<Object>) conditions;
		if (filtedConditionIds.isEmpty()) return;
		final Map<Integer, List<PlatformConditionDefine>> conditionDefinesByCagory = new HashMap<Integer, List<PlatformConditionDefine>>();
		List<Object> ids = new ArrayList<Object>();
		for (Object filtedConditionId : filtedConditionIds) {
			Integer condCategoryId = (Integer) filtedConditionId;
			List<PlatformConditionDefine> conditionDefines = conditionDefineCache
				.getConditionDefineByCategoryId(condCategoryId);
			conditionDefinesByCagory.put(condCategoryId, conditionDefines);
			ids.add(new UserConditionId(event.getUserId(), condCategoryId));
		}
		/**
		 * 查询用户条件记录参数
		 */
		asynHelper.gets(UserCondition.class, ids, new ResListCallback<UserCondition>() {

			@Override
			public void succeed(List<UserCondition> userConditions) throws Exception {
				List<Integer> unUserConditionList = new ArrayList<Integer>(); // 用户条件记录表不存在的条件类别收集
				Map<Integer, String> userCondtionMap = new HashMap<Integer, String>(); // 存在的条件类别收集
				if (userConditions == null) {
					unUserConditionList.addAll(conditionDefinesByCagory.keySet());
				} else {
					for (UserCondition userCondition : userConditions) {
						Integer conCategoryId = userCondition.getId().getICategoryId();
						if (conditionDefinesByCagory.containsKey(conCategoryId)) {
							userCondtionMap.put(conCategoryId, userCondition.getCSatisfyClaim());
						} else {
							unUserConditionList.add(conCategoryId);
						}
					}
				}
				filtedConditionIds.clear(); // 清空收集器，放入满足条件参数的条件ID组
				List<DDALOperator> operatorList = new ArrayList<DDALOperator>();
				userCondtionUnExistProcess(unUserConditionList, conditionDefinesByCagory, filtedConditionIds, operatorList);
				userConditionExistProcess(userCondtionMap, conditionDefinesByCagory, filtedConditionIds, operatorList);
				asynHelper.batchSave(operatorList, new Callback() {
					@Override
					public void succeed(JSONResultVO result) throws Exception {
						if (result.isErrorVO()) {
							Log.error(this.getClass(), result.getErrorMsg());
							return;
						}
					}

					@Override
					public void failure(ResultVO result) throws Exception {
						Log.error(this.getClass(), result.getErrorMsg());
					}
				});
			}

			@Override
			public void failure(ResultVO result) throws Exception {
				Log.error(this.getClass(), result.getErrorMsg());
			}
		});
	}

	/**
	 * 用户条件类别当前记录不存在处理
	 * 
	 * @param unUserConditionList
	 * @param idenConditionMap
	 *            通过身份过滤的条件Map
	 */
	private void userCondtionUnExistProcess(List<Integer> unUserConditionCategoryList,
		Map<Integer, List<PlatformConditionDefine>> conditionDefinesByCagory, List<Object> filtedConditionIds,
		List<DDALOperator> operatorList) {
		if (unUserConditionCategoryList == null || unUserConditionCategoryList.isEmpty()) return;
		/**
		 * 查询属于条件类别的条件列表
		 */
		for (Integer conditionCategoryId : unUserConditionCategoryList) {
			List<PlatformConditionDefine> conditionDefineList = conditionDefinesByCagory.get(conditionCategoryId);
			try {
				/**
				 * 所有相关条件判别是否完成
				 */
				Map<String, Object> currentUserValueMap = new HashMap<String, Object>();// 当前用户条件类别记录值
				Map<ParamterVO, CompareParamVO> totalCompareParams = new HashMap<ParamterVO, CompareParamVO>();
				for (PlatformConditionDefine conditionDefine : conditionDefineList) {
					JSONObject satisfyClaimJson = new JSONObject(conditionDefine.getCRequireValue()); // 转换JSON
					Map<String, Object> satisfyClaimMap = satisfyClaimJson.getMap(); // 转成当前用户条件数据Map
					Map<String, Object> eventParamMap = event.getEventParamMap(); // 事件参数值
					if (conditionComparator.compare(currentUserValueMap, satisfyClaimMap, eventParamMap, totalCompareParams)) {
						filtedConditionIds.add(conditionDefine.getId().getIConditionId());
					}
				}
				updateUserValueMap(currentUserValueMap, totalCompareParams);
				/**
				 * 更新用户条件类别记录
				 */
				UserConditionId id = new UserConditionId(event.getUserId(), conditionCategoryId);
				UserCondition userCondition = new UserCondition(id, new JSONObject(currentUserValueMap).toString());
				operatorList.add(new DDALAddOperator(userCondition));
			} catch (JSONException e) {
				Log.error(this.getClass(), e);
			}
		}
	}

	/**
	 * 用户条件类别当前记录存在处理
	 * 
	 * @param userCondtionMap
	 *            用户条件当前记录Map
	 * @param idenConditionMap
	 *            通过身份过滤的条件Map
	 */
	private void userConditionExistProcess(Map<Integer, String> userCondtionCategoryMap,
		Map<Integer, List<PlatformConditionDefine>> conditionDefinesByCagory, List<Object> filtedConditionIds,
		List<DDALOperator> operatorList) {
		if (userCondtionCategoryMap == null || userCondtionCategoryMap.isEmpty()) return;
		for (Integer conditionCategoryId : userCondtionCategoryMap.keySet()) {
			List<PlatformConditionDefine> conditionDefineList = conditionDefinesByCagory.get(conditionCategoryId);
			try {
				/**
				 * 所有相关条件判别是否完成
				 */
				Map<String, Object> currentUserValueMap = new JSONObject(userCondtionCategoryMap.get(conditionCategoryId))
					.getMap();// 当前用户条件类别记录值
				Map<ParamterVO, CompareParamVO> totalCompareParams = new HashMap<ParamterVO, CompareParamVO>();
				for (PlatformConditionDefine conditionDefine : conditionDefineList) {
					String cRequireValue = conditionDefine.getCRequireValue();
					if (cRequireValue == null || cRequireValue.isEmpty()) {
						filtedConditionIds.add(conditionDefine.getId().getIConditionId());
					}
					JSONObject satisfyClaimJson = new JSONObject(conditionDefine.getCRequireValue()); // 转换JSON
					Map<String, Object> satisfyClaimMap = satisfyClaimJson.getMap(); // 转成当前用户条件数据Map
					Map<String, Object> eventParamMap = event.getEventParamMap(); // 事件参数值
					if (conditionComparator.compare(currentUserValueMap, satisfyClaimMap, eventParamMap, totalCompareParams)) {
						filtedConditionIds.add(conditionDefine.getId().getIConditionId());
					}
				}
				/**
				 * 有参数变化,更新用户条件类别记录
				 */
				if (updateUserValueMap(currentUserValueMap, totalCompareParams)) {
					UserConditionId id = new UserConditionId(event.getUserId(), conditionCategoryId);
					UserCondition userCondition = new UserCondition(id, new JSONObject(currentUserValueMap).toString());
					operatorList.add(new DDALUpdateOperator(id, userCondition));
				}
			} catch (JSONException e) {
				Log.error(this.getClass(), e);
			}
		}
	}

	/**
	 * 更新用户当前参数记录值
	 * 
	 * @param currentUserValueMap
	 * 
	 * @param compareParams
	 */
	private boolean updateUserValueMap(Map<String, Object> currentUserValueMap, Map<ParamterVO, CompareParamVO> compareParams) {
		boolean isParamChange = false;
		for (Map.Entry<ParamterVO, CompareParamVO> compareParam : compareParams.entrySet()) {
			String cName = compareParam.getKey().getCName();
			Object lastCurUserValue = currentUserValueMap.get(compareParam.getKey().getCName());
			Object nowCurUserValue = compareParam.getValue().getNowCurUserValue();
			if (nowCurUserValue != null && !nowCurUserValue.equals(lastCurUserValue)) {
				currentUserValueMap.put(cName, nowCurUserValue);
				isParamChange = true;
			}
		}
		return isParamChange;
	}

	/**
	 * 条件本地缓存
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-16
	 */
	class ConditionDefineCache extends FileCache {
		private Map<Integer, List<PlatformConditionDefine>> conditionMap;

		public ConditionDefineCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (conditionMap == null) {
				conditionMap = new HashMap<Integer, List<PlatformConditionDefine>>();
			} else {
				conditionMap.clear();
			}
			try {
				JSONObject json = JSONObjUtil.file2JsonObject(getFile());
				JSONListObj listObj = new JSONListObj(json);
				TableVO<PlatformConditionDefine> table = new TableVO<PlatformConditionDefine>(PlatformConditionDefine.class,
					listObj);
				List<PlatformConditionDefine> list = table.getObjectList();
				if (list != null && !list.isEmpty()) {
					for (PlatformConditionDefine c : list) {
						Integer ICategoryId = c.getId().getICategoryId();
						if (conditionMap.containsKey(ICategoryId)) {
							conditionMap.get(ICategoryId).add(c);
						} else {
							List<PlatformConditionDefine> conditionDefines = new ArrayList<PlatformConditionDefine>();
							conditionDefines.add(c);
							conditionMap.put(ICategoryId, conditionDefines);
						}
					}
				}
			} catch (Exception e) {
				Log.error(this.getClass(), e);
			}
		}

		/** 根据条件类别ID获取平台条件定义列表 **/
		public List<PlatformConditionDefine> getConditionDefineByCategoryId(Integer condCategoryID) {
			loadFile();
			return conditionMap.get(condCategoryID);
		}
	}
}
