package com.wind.goal.comparator;

import java.util.Map;

/**
 * 条件比较器
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-20
 */
public interface ConditionComparator extends Comparator {

	public boolean compare(Map<String, Object> currentUserValueMap, Map<String, Object> satisfyClaimMap,
		Map<String, Object> eventParamMap);
}
