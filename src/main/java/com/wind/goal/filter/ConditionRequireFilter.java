package com.wind.goal.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wind.goal.FileCache;
import com.wind.goal.ParamterVO;
import com.wind.goal.comparator.ParamComparator.CompareParamVO;
import com.wind.goal.dao.IUserConditionDao;
import com.wind.goal.dao.po.ConditionValue;
import com.wind.goal.dao.po.UserCondition;

/**
 * 达成要求条件收集
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class ConditionRequireFilter extends ConditionFilter {
	private static final Logger logger = Logger.getLogger(ConditionRequireFilter.class);

	private IUserConditionDao userConditionDao; // 用户条件Dao
	private ConditionValueCache conditionDefineCache; // 条件定义缓存

	public ConditionRequireFilter(String conditionFilePath) {
		conditionDefineCache = new ConditionValueCache(conditionFilePath);
	}

	@Override
	public void filte(Object conditions) {
		/*
		 * 获取条件值列表
		 */
		@SuppressWarnings("unchecked")
		final List<Object> filtedConditionIds = (List<Object>) conditions;
		if (filtedConditionIds.isEmpty()) return;
		final Map<Integer, List<ConditionValue>> conditionValuesByCondition = new HashMap<Integer, List<ConditionValue>>();
		List<Integer> conditionIds = new ArrayList<Integer>();
		for (Object filtedConditionId : filtedConditionIds) {
			Integer conditionId = (Integer) filtedConditionId;
			List<ConditionValue> conditionDefines = conditionDefineCache
				.getCondValuesByCondId(conditionId);
			conditionValuesByCondition.put(conditionId, conditionDefines);
			conditionIds.add(conditionId);
		}

		List<UserCondition> userConditions = userConditionDao.batchFindUserCondition(event.getUserId(), conditionIds);
		List<Integer> unUserConditionList = new ArrayList<Integer>(); // 用户条件记录表不存在的条件类别收集
		Map<Integer, String> userCondtionMap = new HashMap<Integer, String>(); // 存在的条件类别收集
		if (userConditions == null) {
			unUserConditionList.addAll(conditionValuesByCondition.keySet());
		} else {
			for (UserCondition userCondition : userConditions) {
				Integer conditionId = userCondition.getConditionId();
				if (conditionValuesByCondition.containsKey(conditionId)) {
					userCondtionMap.put(conditionId, userCondition.getConditionValue());
				} else {
					unUserConditionList.add(conditionId);
				}
			}
		}
		userCondtionUnExistProcess(unUserConditionList, conditionValuesByCondition, filtedConditionIds);
		userConditionExistProcess(userCondtionMap, conditionValuesByCondition, filtedConditionIds);
	}

	/**
	 * 用户条件类别当前记录不存在处理
	 * 
	 * @param unUserConditionList
	 * @param idenConditionMap
	 *            通过身份过滤的条件Map
	 */
	private void userCondtionUnExistProcess(List<Integer> unUserConditionList,
		Map<Integer, List<ConditionValue>> conditionValuesByConId, List<Object> filtedConditionIds) {
		if (unUserConditionList == null || unUserConditionList.isEmpty()) return;
		List<UserCondition> userConditions = new ArrayList<UserCondition>();
		for (Integer conditionId : unUserConditionList) {
			List<ConditionValue> conditionValueList = conditionValuesByConId.get(conditionId); // 查询条件值列表
			/*
			 * 所有相关条件判别是否完成
			 */
			Map<String, Object> currentUserValueMap = new HashMap<String, Object>();// 当前用户条件类别记录值
			Map<ParamterVO, CompareParamVO> totalCompareParams = new HashMap<ParamterVO, CompareParamVO>();
			for (ConditionValue conditionValue : conditionValueList) {
				@SuppressWarnings("unchecked")
				Map<String, Object> satisfyClaimMap = (Map<String, Object>) JSONObject.toBean(JSONObject
					.fromObject(conditionValue.getRequireValue()),
					HashMap.class);
				Map<String, Object> eventParamMap = event.getEventParamMap(); // 事件参数值
				if (conditionComparator.compare(currentUserValueMap, satisfyClaimMap, eventParamMap, totalCompareParams)) {
					filtedConditionIds.add(conditionValue.getConditionId());
				}
			}
			updateUserValueMap(currentUserValueMap, totalCompareParams);
			/*
			 * 更新用户条件类别记录
			 */
			UserCondition userCondition = new UserCondition(event.getUserId(), conditionId, JSONObject.fromObject(
				currentUserValueMap).toString());
			userConditions.add(userCondition);
		}
		userConditionDao.batchAdd(userConditions);
	}

	/**
	 * 用户条件类别当前记录存在处理
	 * 
	 * @param userCondtionMap
	 *            用户条件当前记录Map
	 * @param idenConditionMap
	 *            通过身份过滤的条件Map
	 */
	private void userConditionExistProcess(Map<Integer, String> userCondtionValueMap,
		Map<Integer, List<ConditionValue>> conditionValuesByConId, List<Object> filtedConditionIds) {
		if (userCondtionValueMap == null || userCondtionValueMap.isEmpty()) return;
		List<UserCondition> userConditions = new ArrayList<UserCondition>();
		for (Integer conditionId : userCondtionValueMap.keySet()) {
			List<ConditionValue> conditionValueList = conditionValuesByConId.get(conditionId);
			/*
			 * 所有相关条件判别是否完成
			 */
			@SuppressWarnings("unchecked")
			Map<String, Object> currentUserValueMap = (Map<String, Object>) JSONObject.toBean(JSONObject
				.fromObject(userCondtionValueMap.get(conditionId)), HashMap.class);// 当前用户条件类别记录值
			Map<ParamterVO, CompareParamVO> totalCompareParams = new HashMap<ParamterVO, CompareParamVO>();
			for (ConditionValue conditionValue : conditionValueList) {
				String cRequireValue = conditionValue.getRequireValue();
				if (cRequireValue == null || cRequireValue.isEmpty()) {
					filtedConditionIds.add(conditionValue.getConditionId());
				}
				JSONObject satisfyClaimJson = JSONObject.fromObject(conditionValue.getRequireValue()); // 转换JSON
				@SuppressWarnings("unchecked")
				Map<String, Object> satisfyClaimMap = (Map<String, Object>) JSONObject.toBean(satisfyClaimJson,
					HashMap.class); // 转成当前用户条件数据Map
				Map<String, Object> eventParamMap = event.getEventParamMap(); // 事件参数值
				if (conditionComparator.compare(currentUserValueMap, satisfyClaimMap, eventParamMap, totalCompareParams)) {
					filtedConditionIds.add(conditionValue.getConditionId());
				}
			}
			/*
			 * 有参数变化,更新用户条件类别记录
			 */
			if (updateUserValueMap(currentUserValueMap, totalCompareParams)) {
				UserCondition userCondition = new UserCondition(event.getUserId(), conditionId, JSONObject.fromObject(
					currentUserValueMap).toString());
				userConditions.add(userCondition);
			}
			userConditionDao.batchUpdate(userConditions);
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
	class ConditionValueCache extends FileCache {
		private Map<Integer, List<ConditionValue>> conditionValueMap;

		public ConditionValueCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (conditionValueMap == null) {
				conditionValueMap = new HashMap<Integer, List<ConditionValue>>();
			} else {
				conditionValueMap.clear();
			}
			try {
				String fileContent = this.readFile();
				JSONArray jsonArray = JSONArray.fromObject(fileContent);
				@SuppressWarnings("unchecked")
				List<ConditionValue> list = (List<ConditionValue>) JSONArray.toArray(
					jsonArray, ConditionValue.class);

				if (list != null && !list.isEmpty()) {
					for (ConditionValue c : list) {
						Integer conditionId = c.getConditionId();
						if (conditionValueMap.containsKey(conditionId)) {
							conditionValueMap.get(conditionId).add(c);
						} else {
							List<ConditionValue> conditionValues = new ArrayList<ConditionValue>();
							conditionValues.add(c);
							conditionValueMap.put(conditionId, conditionValues);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		/** 根据条件类别ID获取平台条件定义列表 **/
		public List<ConditionValue> getCondValuesByCondId(Integer conditionID) {
			loadFile();
			return conditionValueMap.get(conditionID);
		}
	}
}
