package com.wind.goal.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.wind.goal.ConditionFilterException;
import com.wind.goal.comparator.Comparator;
import com.wind.goal.comparator.DefaultParamComparator;
import com.wind.goal.comparator.ParamComparator;
import com.wind.goal.comparator.SelfDefinedParamComparator;
import com.wind.goal.comparator.ParamComparator.CompareParamVO;
import com.wind.goal.event.Event;
import com.wind.goal.event.EventCacheDAO;
import com.wind.goal.handle.EventHandler;
import com.wind.goal.vo.ParamterVO;

/**
 * 条件过滤基类
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public abstract class ConditionFilter implements EventHandler {
	protected Event event;
	private Object conditions; // 条件收集器
	protected ConditionComparator conditionComparator = new ConditionComparator(); // 条件比较器
	protected ParamComparator defaultComparator = new DefaultParamComparator(); // 参数默认比较器
	private Map<String, SelfDefinedParamComparator> paramComparator; // 自定义参数比较注册

	@Override
	public void handle(Event event) {
		this.event = event;
		filte(conditions);
	}

	@Override
	public boolean isHanle(Event event) {
		return true;
	}

	/**
	 * 返回符合要求的条件组
	 * 
	 * @param conditions
	 *            条件组
	 * @return 符合要求的条件组
	 * @throws ConditionFilterException
	 */
	protected abstract void filte(Object conditions);

	public void setConditions(Object conditions) {
		this.conditions = conditions;
	}

	/**
	 * 条件比较器
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-20
	 */
	public class ConditionComparator implements Comparator {
		// private Map<String, SelfDefinedParamComparator> paramComparator; //
		// 自定义参数比较器
		// private ParamComparator defaultComparator; // 默认参数比较器

		public boolean compare(Map<String, Object> currentUserValueMap, Map<String, Object> satisfyClaimMap,
			Map<String, Object> eventParamMap, Map<ParamterVO, CompareParamVO> totalCompareParams) {
			if (satisfyClaimMap.isEmpty()) return true;
			List<Boolean> isSatifys = new ArrayList<Boolean>();
			for (String conditionParamKey : satisfyClaimMap.keySet()) {
				/**
				 * 收集比较参数
				 */
				Map<ParamterVO, CompareParamVO> compareParams = compareParamCollect(conditionParamKey, currentUserValueMap,
					satisfyClaimMap, eventParamMap);
				if (totalCompareParams != null) {
					totalCompareParams.putAll(compareParams);
				}
				/**
				 * 如果参数注册了自定义比较器
				 */
				if (paramComparator.containsKey(conditionParamKey)) {
					isSatifys.add(paramComparator.get(conditionParamKey).compare(compareParams, event));
				} else {
					/**
					 * 没注册自定义比较器，使用默认比较器比较
					 */
					isSatifys.add(defaultComparator.compare(compareParams, event));
				}
				/**
				 * 更新
				 */
			}
			// updateUserValueMap(currentUserValueMap, totalCompareParams);
			if (!isSatifys.contains(false)) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * 收集比较参数
		 * 
		 * @param conditionParamKey
		 *            参数
		 * @param currentUserValueMap
		 *            当前用户参数Map
		 * @param satisfyClaimMap
		 *            要求参数Map
		 * @param eventParamMap
		 *            事件传过来的参数Map
		 * @return 收集的比较参数Map
		 */
		private Map<ParamterVO, CompareParamVO> compareParamCollect(String conditionParamKey,
			Map<String, Object> currentUserValueMap, Map<String, Object> satisfyClaimMap, Map<String, Object> eventParamMap) {
			ParamterVO paramterVO = EventCacheDAO.getEventParameterByName(conditionParamKey);
			String[] relativeParams = paramterVO.getRelativeParams();
			Map<ParamterVO, CompareParamVO> compareParams = new HashMap<ParamterVO, CompareParamVO>();
			Object satisfyValue = satisfyClaimMap.get(conditionParamKey);
			Object userCurrValue = currentUserValueMap.get(conditionParamKey);
			Object eventValue = eventParamMap.get(conditionParamKey);
			CompareParamVO compareParamVO = new CompareParamVO(userCurrValue, satisfyValue, eventValue);
			compareParams.put(paramterVO, compareParamVO);
			// 存在关联参数,收集关联比较参数
			if (relativeParams != null && relativeParams.length > 0) {
				for (String relativeParamKey : relativeParams) {
					userCurrValue = currentUserValueMap.get(relativeParamKey);
					satisfyValue = satisfyClaimMap.remove(relativeParamKey);
					eventValue = eventParamMap.get(relativeParamKey);
					compareParamVO = new CompareParamVO(userCurrValue, satisfyValue, eventValue);
					compareParams.put(paramterVO, compareParamVO);
				}
			}
			return compareParams;
		}
	}

	public ConditionComparator getConditionComparator() {
		return conditionComparator;
	}

	public void setConditionComparator(ConditionComparator conditionComparator) {
		this.conditionComparator = conditionComparator;
	}

	public ParamComparator getDefaultComparator() {
		return defaultComparator;
	}

	public void setDefaultComparator(ParamComparator defaultComparator) {
		this.defaultComparator = defaultComparator;
	}

	public Map<String, SelfDefinedParamComparator> getParamComparator() {
		return paramComparator;
	}

	public void setParamComparator(Map<String, SelfDefinedParamComparator> paramComparator) {
		this.paramComparator = paramComparator;
	}
}
