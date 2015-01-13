package com.wind.goal.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.rmi.runtime.Log;

import com.wind.goal.res.PlatformConditionCategoryDefine;

/**
 * 条件特征收集
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class ConditionFeatureFilter extends ConditionFilter {

	private ConditionCategoryDefineCache conditionCache;

	public ConditionFeatureFilter(String conditionCategoryFilePath) {
		conditionCache = new ConditionCategoryDefineCache(conditionCategoryFilePath);
	}

	@Override
	public void filte(Object conditions) {
		@SuppressWarnings("unchecked")
		List<Object> filtedConditionIds = (List<Object>) conditions;
		String eventNO = event.getEventNO();
		/**
		 * 根据事件号查询所属条件
		 */
		List<PlatformConditionCategoryDefine> conditionCategoryList = conditionCache
			.getConditionCategoryDefineByEventNO(eventNO);
		if (conditionCategoryList == null || conditionCategoryList.isEmpty()) return;
		for (PlatformConditionCategoryDefine conditionCategory : conditionCategoryList) {
			String cIdentifyParams = conditionCategory.getCIdentifyParams();
			if (null == cIdentifyParams || cIdentifyParams.isEmpty()) {
				filtedConditionIds.add(conditionCategory.getICategoryId());
				continue;
			}
			try {
				JSONObject json = new JSONObject(cIdentifyParams); // 转换JSON
				Map<String, Object> cIdentifyParamMap = json.getMap(); // 转成身份参数Map
				Map<String, Object> eventParamMap = event.getEventParamMap(); // 事件参数（由事件发生者传过来）
				Map<String, Object> currentUserValueMap = new HashMap<String, Object>();
				/**
				 * 通过身份参数找出符合的条件类别
				 */
				if (conditionComparator.compare(currentUserValueMap, cIdentifyParamMap, eventParamMap, null)) {
					filtedConditionIds.add(conditionCategory.getICategoryId());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 条件类别本地缓存
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-16
	 */
	class ConditionCategoryDefineCache extends FileCache {
		private Map<Integer, PlatformConditionCategoryDefine> conditionCategory;

		public ConditionCategoryDefineCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (conditionCategory == null) {
				conditionCategory = new HashMap<Integer, PlatformConditionCategoryDefine>();
			} else {
				conditionCategory.clear();
			}
			try {
				JSONObject json = JSONObjUtil.file2JsonObject(getFile());
				JSONListObj listObj = new JSONListObj(json);
				TableVO<PlatformConditionCategoryDefine> table = new TableVO<PlatformConditionCategoryDefine>(
					PlatformConditionCategoryDefine.class, listObj);
				List<PlatformConditionCategoryDefine> list = table.getObjectList();
				if (list != null && !list.isEmpty()) {
					for (PlatformConditionCategoryDefine c : list) {
						conditionCategory.put(c.getICategoryId(), c);
					}
				}
			} catch (Exception e) {
				Log.error(this.getClass(), e);
			}
		}

		/** 获取平台条件类别定义 **/
		public PlatformConditionCategoryDefine getConditionDefineByCondID(Integer condCategoryID) {
			loadFile();
			return conditionCategory.get(condCategoryID);
		}

		/** 根 据事件号获取平台条件类别定义 **/
		public List<PlatformConditionCategoryDefine> getConditionCategoryDefineByEventNO(String eventNO) {
			loadFile();
			List<PlatformConditionCategoryDefine> list = new ArrayList<PlatformConditionCategoryDefine>();
			if (conditionCategory.isEmpty()) return list;
			for (Integer condID : conditionCategory.keySet()) {
				String _eventNO = conditionCategory.get(condID).getCEvnetNo();
				if (!eventNO.equals(_eventNO)) continue;
				list.add(conditionCategory.get(condID));
			}
			return list;
		}
	}
}
