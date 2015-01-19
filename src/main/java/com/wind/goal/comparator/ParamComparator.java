package com.wind.goal.comparator;

import java.util.Map;

import com.wind.goal.ParamterVO;
import com.wind.goal.event.Event;

/**
 * 参数比较器接口
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-20
 */
public interface ParamComparator extends Comparator {

	/**
	 * 参数比较
	 * 
	 * @param compareParams
	 * @param event
	 *            事件
	 * @return 返回
	 */
	boolean compare(Map<ParamterVO, CompareParamVO> compareParams, Event event);

	/**
	 * 参数比较VO
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-20
	 */
	public class CompareParamVO {
		private Object lastCurUserValue; // 上次用户参数当前值
		private Object conditionValue; // 参数条件值
		private Object eventValue; // 事件参数
		private Object nowCurUserValue; // 本次用户参数当前值
		private boolean isUpdateCurValue = false; // 用户参数当前值是否更新

		public CompareParamVO() {}

		public CompareParamVO(Object lastCurUserValue, Object conditionValue, Object eventValue) {
			super();
			this.lastCurUserValue = lastCurUserValue;
			this.conditionValue = conditionValue;
			this.eventValue = eventValue;
		}

		public Object getLastCurUserValue() {
			return lastCurUserValue;
		}

		public void setLastCurUserValue(Object lastCurUserValue) {
			this.lastCurUserValue = lastCurUserValue;
		}

		public Object getNowCurUserValue() {
			return nowCurUserValue;
		}

		public void setNowCurUserValue(Object nowCurUserValue) {
			this.nowCurUserValue = nowCurUserValue;
		}

		public Object getConditionValue() {
			return conditionValue;
		}

		public void setConditionValue(Object conditionValue) {
			this.conditionValue = conditionValue;
		}

		public Object getEventValue() {
			return eventValue;
		}

		public void setEventValue(Object eventValue) {
			this.eventValue = eventValue;
		}

		public boolean isUpdateCurValue() {
			return isUpdateCurValue;
		}

		public void setUpdateCurValue(boolean isUpdateCurValue) {
			this.isUpdateCurValue = isUpdateCurValue;
		}
	}
}
