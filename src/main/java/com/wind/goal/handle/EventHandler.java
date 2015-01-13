package com.wind.goal.handle;

import com.wind.goal.event.Event;

/**
 * 事件处理器接口
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-13
 */
public interface EventHandler {
	/**
	 * 是否处理事件
	 * 
	 * @param evnet
	 *            事件
	 * @return 是否处理
	 */
	public boolean isHanle(Event event);

	/**
	 * 处理事件
	 * 
	 * @param event
	 *            事件
	 */
	public void handle(Event event);
}
