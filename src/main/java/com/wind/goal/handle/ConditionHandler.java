package com.wind.goal.handle;

import java.util.ArrayList;
import java.util.List;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.d1xn.common.log.Log;
import com.wind.goal.event.Event;
import com.wind.goal.filter.ConditionFilter;
import com.wind.goal.goal.MountGoalPoint;

/**
 * 条件达成处理器
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-13
 */
public class ConditionHandler implements EventHandler {
	private List<ConditionFilter> filters; // 条件收集器（如果不设置，则不进行条件收集，直接处理挂载目标）
	private List<MountGoalPoint> mountGoalPoints; // 挂载目标
	private ThreadPoolTaskExecutor threadPool; // 线程池

	@Override
	public boolean isHanle(Event evnet) {
		return true;
	}

	@Override
	public void handle(Event event) {
		/**
		 * 过滤收集出完成的条件
		 */
		List<Object> filtedConditionIds = new ArrayList<Object>(); // 条件搜集(收集完成的条件)
		if (filters != null) {
			for (ConditionFilter filter : filters) {
				filter.setConditions(filtedConditionIds);
				filter.handle(event);
			}
			if (filtedConditionIds.isEmpty()) return;
		}

		/**
		 * 对挂载在条件上的各类目标处理
		 */
		if (mountGoalPoints != null && !mountGoalPoints.isEmpty()) {
			for (final MountGoalPoint mountGoalPoint : mountGoalPoints) {
				mountGoalPoint.setFiltedConditionIds(filtedConditionIds); // 完成的条件设置到挂载目标
				mountGoalPoint.setEvent(event); // 设置事件
				if (threadPool != null) {
					threadPool.execute(new Runnable() {
						@Override
						public void run() {
							try {
								mountGoalPoint.completeUserGoal();
							} catch (Exception e) {
								Log.error(this.getClass(), e);
							}
						}
					});
				} else {
					mountGoalPoint.completeUserGoal(); // 完成挂载目标
				}
			}
		}
	}

	public ThreadPoolTaskExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
		this.threadPool = threadPool;
	}

	public List<ConditionFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<ConditionFilter> filters) {
		this.filters = filters;
	}

	public List<MountGoalPoint> getMountGoalPoints() {
		return mountGoalPoints;
	}

	public void setMountGoalPoints(List<MountGoalPoint> mountGoalPoints) {
		this.mountGoalPoints = mountGoalPoints;
	}
}
