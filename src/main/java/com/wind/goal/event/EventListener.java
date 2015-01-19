package com.wind.goal.event;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import com.wind.goal.handle.EventHandler;

/**
 * 事件监控器
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-10
 */
public class EventListener {
	private static final Logger logger = Logger.getLogger(EventListener.class);
	private static ExecutorService threadPool; // 线程池
	private static List<EventHandler> handleList;

	public static void onEvent(final Event event) {
		if (threadPool != null) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						handleEvent(event);
					} catch (Exception e) {
						logger.error(this.getClass(), e);
					}
				}
			});
		} else {
			handleEvent(event);
		}
	}

	private static void handleEvent(Event event) {
		for (EventHandler eventHandler : handleList) {
			if (eventHandler.isHanle(event)) {
				eventHandler.handle(event);
			}
		}
	}

	public static ExecutorService getThreadPool() {
		return threadPool;
	}

	public static void setThreadPool(ExecutorService threadPool) {
		EventListener.threadPool = threadPool;
	}

	public void setHandleList(List<EventHandler> handleList) {
		EventListener.handleList = handleList;
	}
}
