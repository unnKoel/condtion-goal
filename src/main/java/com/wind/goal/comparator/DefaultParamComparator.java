package com.wind.goal.comparator;

import java.util.Map;
import com.wind.goal.event.Event;
import com.wind.goal.vo.ParamterVO;

/**
 * 默认参数比较器
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-20
 */
public class DefaultParamComparator implements ParamComparator {

	@Override
	public boolean compare(Map<ParamterVO, CompareParamVO> compareParams, Event event) {
		for (Map.Entry<ParamterVO, CompareParamVO> compareParam : compareParams.entrySet()) {
			ParamterVO paramterVO = compareParam.getKey();
			CompareParamVO compareParamVO = compareParam.getValue();
			if (paramterVO.compare(compareParamVO) == false) {
				return false;
			}
		}
		return true;
	}
}
