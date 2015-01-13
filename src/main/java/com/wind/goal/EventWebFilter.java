package com.wind.goal;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wind.goal.event.Event;
import com.wind.goal.event.EventListener;

/**
 * 事件Web过滤器(用于Web拦截请求，并触发事件)
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-19
 */
public class EventWebFilter implements Filter {
	/**
	 * 事件监听器
	 */
	private EventListener eventListener;
	/**
	 * url注册到事件Map(key：请求地址 value:事件信息)
	 */
	private Map<String, RegisterEvent> urlEventMap;

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException, ServletException {
		if (urlEventMap == null || urlEventMap.isEmpty() || eventListener == null) {
			chain.doFilter(req, rsp);
			return;
		}
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rsp;
		String uri = request.getRequestURI();
		/**
		 * 取得用户信息
		 */
		UserVO user = (UserVO) request.getAttribute(IdentityManager.REQUEST_USER);
		if (user == null) {
			chain.doFilter(request, response);
			return;
		}
		/**
		 * 如果请求注册了事件
		 */
		if (urlEventMap.containsKey(uri)) {
			RegisterEvent eventInfo = urlEventMap.get(uri);
			Event event = new Event(uri); // 设置事件源
			event.setEventNO(eventInfo.getEventNO()); // 设置事件号
			event.setUserId(user.getNUserId()); // 设置用户ID
			List<EventParam> claimEventParam = eventInfo.getParams(); // 要求的事件参数
			/**
			 * 收集待触发事件的参数与参数值
			 */
			Map<String, Object> eventParam = new HashMap<String, Object>(); // 收集事件参数与参数值
			for (EventParam claimEvent : claimEventParam) {
				String paramName = claimEvent.getParamName();
				// 如果要求从请求参数中取
				Object paramValue = null;
				if (claimEvent.isRequest) {
					paramValue = request.getParameter(paramName);
				} else {
					paramValue = claimEvent.getParamValue();
				}
				if (paramValue == null) {
					chain.doFilter(request, response);
					return;
				}
				eventParam.put(paramName, paramValue);
			}
			event.setEventParamMap(eventParam);
			EventListener.onEvent(event);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String totalClass = filterConfig.getInitParameter("eventListener");
		if (totalClass != null && totalClass.toLowerCase().startsWith("spring:")) {
			eventListener = (EventListener) BeanUtil.getBean(totalClass.substring(7));
		}
	}

	@Override
	public void destroy() {}

	/**
	 * 
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-19
	 */
	public class RegisterEvent {
		private String EventNO;
		private List<EventParam> params;

		public RegisterEvent() {}

		public RegisterEvent(String eventNO, List<EventParam> params) {
			super();
			EventNO = eventNO;
			this.params = params;
		}

		public String getEventNO() {
			return EventNO;
		}

		public void setEventNO(String eventNO) {
			EventNO = eventNO;
		}

		public List<EventParam> getParams() {
			return params;
		}

		public void setParams(List<EventParam> params) {
			this.params = params;
		}
	}

	/**
	 * 事件参数定义
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-19
	 */
	public class EventParam {
		private boolean isRequest = false; // 是否从请求参数里取
		private String paramName; // 参数Name
		private Object paramValue; // 参数值 （如果从请求参数里取，则为null）

		public EventParam() {}

		public EventParam(String paramName) {
			super();
			this.isRequest = true;
			this.paramName = paramName;
		}

		public EventParam(String paramName, Object paramValue) {
			super();
			this.paramName = paramName;
			this.paramValue = paramValue;
		}

		public boolean isRequest() {
			return isRequest;
		}

		public void setRequest(boolean isRequest) {
			this.isRequest = isRequest;
		}

		public String getParamName() {
			return paramName;
		}

		public void setParamName(String paramName) {
			this.paramName = paramName;
		}

		public Object getParamValue() {
			return paramValue;
		}

		public void setParamValue(Object paramValue) {
			this.paramValue = paramValue;
		}
	}
}
