package com.wind.goal.event;

import java.util.List;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.d1xn.common.log.Log;
import com.wind.goal.handle.EventHandler;

/**
 * 事件监控器
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-10
 */
public class EventListener {
	private static ThreadPoolTaskExecutor threadPool;
	private static List<EventHandler> handleList;

	public static void onEvent(final Event event) {
		if (threadPool != null) {
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					try {
						handleEvent(event);
					} catch (Exception e) {
						Log.error(this.getClass(), e);
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

	public ThreadPoolTaskExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
		EventListener.threadPool = threadPool;
	}

	public void setHandleList(List<EventHandler> handleList) {
		EventListener.handleList = handleList;
	}
}
