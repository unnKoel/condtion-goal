package com.wind.goal.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.wind.goal.FileCache;
import com.wind.goal.dao.po.Condition;

/**
 * 条件特征收集
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class ConditionFeatureFilter extends ConditionFilter {
	private static final Logger logger = Logger.getLogger(ConditionFeatureFilter.class);
	private ConditionCache conditionCache;

	public ConditionFeatureFilter(String conditionCategoryFilePath) {
		conditionCache = new ConditionCache(conditionCategoryFilePath);
	}

	@Override
	public void filte(Object conditions) {
		@SuppressWarnings("unchecked")
		List<Object> filtedConditionIds = (List<Object>) conditions;
		String eventNO = event.getEventNO();
		/*
		 * 根据事件号查询所属条件
		 */
		List<Condition> conditionList = conditionCache
			.getConditionByEventNO(eventNO);
		if (conditionList == null || conditionList.isEmpty()) return;
		for (Condition condition : conditionList) {
			String identifyParams = condition.getIdentifyParams();
			if (null == identifyParams || identifyParams.isEmpty()) {
				filtedConditionIds.add(condition.getConditionId());
				continue;
			}
			JSONObject json = JSONObject.fromObject(identifyParams); // 转成JSON
			@SuppressWarnings("unchecked")
			Map<String, Object> cIdentifyParamMap = (Map<String, Object>) JSONObject.toBean(json, HashMap.class); // 转成身份参数Map
			Map<String, Object> eventParamMap = event.getEventParamMap(); // 事件参数（由事件发生者传过来）
			Map<String, Object> currentUserValueMap = new HashMap<String, Object>();
			/*
			 * 通过身份参数找出符合的条件
			 */
			if (conditionComparator.compare(currentUserValueMap, cIdentifyParamMap, eventParamMap, null)) {
				filtedConditionIds.add(condition.getConditionId());
			}
		}
	}

	/**
	 * 条件本地缓存
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-16
	 */
	class ConditionCache extends FileCache {
		private Map<Integer, Condition> conditionMap;

		public ConditionCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			try {
				if (conditionMap == null) {
					conditionMap = new HashMap<Integer, Condition>();
				} else {
					conditionMap.clear();
				}
				String fileContent = this.readFile();
				JSONArray jsonArray = JSONArray.fromObject(fileContent);
				@SuppressWarnings("unchecked")
				List<Condition> list = (List<Condition>) JSONArray.toArray(
					jsonArray, Condition.class);
				if (list != null && !list.isEmpty()) {
					for (Condition c : list) {
						conditionMap.put(c.getConditionId(), c);
					}
				}
			} catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
		}

		/** 获取条件 **/
		public Condition getConditionByCondID(Integer conditionID) {
			loadFile();
			return conditionMap.get(conditionID);
		}

		/** 根据事件号获取条件 **/
		public List<Condition> getConditionByEventNO(String eventNO) {
			loadFile();
			List<Condition> list = new ArrayList<Condition>();
			if (conditionMap.isEmpty()) return list;
			for (Integer condID : conditionMap.keySet()) {
				String _eventNO = conditionMap.get(condID).getEventNo();
				if (!eventNO.equals(_eventNO)) continue;
				list.add(conditionMap.get(condID));
			}
			return list;
		}
	}
}
