package com.wind.goal.comparator;

import java.sql.Timestamp;
import java.util.Map;

import com.wind.goal.event.Event;
import com.wind.goal.vo.ParamterVO;

/**
 * 日期参数比较器,条件设置多少天
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-20
 */
public class DaysParamComparator implements SelfDefinedParamComparator {

	@Override
	public boolean compare(Map<ParamterVO, CompareParamVO> compareParams, Event event) {
		CompareParamVO compareParamVO = compareParams.get(new ParamterVO("days"));
		Integer days = (Integer) compareParamVO.getConditionValue();
		Object lastCurUserValue = compareParamVO.getLastCurUserValue();
		if (days.equals(1)) {
			Timestamp zeroHourOfToday = BaseUtil.getZeroHourOfToday();
			compareParamVO.setNowCurUserValue(zeroHourOfToday.toString());
			if (!zeroHourOfToday.equals(lastCurUserValue)) {
				compareParamVO.setUpdateCurValue(true);
			}
			if (lastCurUserValue == null) {
				return true;
			} else {
				return BaseUtil.compareDate2((String) lastCurUserValue, zeroHourOfToday.toString());
			}
		}
		return false;
	}
}
