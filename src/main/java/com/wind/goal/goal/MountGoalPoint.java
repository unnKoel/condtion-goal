package com.wind.goal.goal;

import java.util.List;
import com.wind.goal.event.Event;

/**
 * 挂载目标点
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public abstract class MountGoalPoint {
	protected Event event; // 事件
	protected List<Object> completeConditionIds; // 完成的条件

	public abstract void completeUserGoal();

	public List<Object> getFiltedConditionIds() {
		return completeConditionIds;
	}

	public void setFiltedConditionIds(List<Object> completeConditionIds) {
		this.completeConditionIds = completeConditionIds;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
}
