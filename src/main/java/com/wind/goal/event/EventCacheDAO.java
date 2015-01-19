package com.wind.goal.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.wind.goal.FileCache;
import com.wind.goal.ParamterVO;
import com.wind.goal.dao.po.Parameter;

/**
 * 事件相关Cache，包括事件、事件参数缓存
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class EventCacheDAO {

	// 缓存文件
	private String eventFilePath;
	private String eventParamFilePath;

	// 缓存对象
	private EventCache eventCache;
	private EventParamCache eventParamCache;

	// 静态实例
	private static final EventCacheDAO instance = new EventCacheDAO();
	private static final Logger logger = Logger.getLogger(EventCacheDAO.class);

	/** 构造函数 **/
	private EventCacheDAO() {
		super();
	}

	/** 对外接口 */
	public static EventCacheDAO getInstance() {
		return instance;
	}

	/** 初始化本地缓存 */
	public void init() {
		eventCache = new EventCache(eventFilePath);
		eventParamCache = new EventParamCache(eventParamFilePath);
	}

	/**
	 * 根据事件ID获取事件
	 * 
	 * @param eventNO
	 *            事件号
	 * @return 事件
	 */
	public static Event getEventByeventNO(String eventNO) {
		return getInstance().getEventCache().getEventByEventNO(eventNO);
	}

	/**
	 * 根据事件参数名获取参数
	 * 
	 * @param paramName
	 *            参数名
	 * @return 参数
	 */
	public static ParamterVO getEventParameterByName(String paramName) {
		Parameter paramterDefine = getInstance().getEventParamCache().getEventParamByName(paramName);
		ParamterVO paramterVO = paramterDefine.getParamterVO();
		return paramterVO;
	}

	class EventCache extends FileCache {
		private Map<String, Event> eventMap;

		public EventCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (eventMap == null) {
				eventMap = new HashMap<String, Event>();
			} else {
				eventMap.clear();
			}
			try {
				String fileContent = this.readFile();
				JSONArray jsonArray = JSONArray.fromObject(fileContent);
				@SuppressWarnings("unchecked")
				List<Event> list = (List<Event>) JSONArray.toArray(jsonArray,
					Event.class);
				if (list != null && !list.isEmpty()) {
					for (Event c : list) {
						eventMap.put(c.getEventNO(), c);
					}
				}
			} catch (Exception e) {
				logger.error(this.getClass(), e);
			}
		}

		/** 获取平台条件定义 **/
		public Event getEventByEventNO(String EventNO) {
			loadFile();
			return eventMap.get(EventNO);
		}
	}

	class EventParamCache extends FileCache {
		private Map<String, Parameter> eventParamMap;

		public EventParamCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (eventParamMap == null) {
				eventParamMap = new HashMap<String, Parameter>();
			} else {
				eventParamMap.clear();
			}
			try {
				String fileContent = this.readFile();
				JSONArray jsonArray = JSONArray.fromObject(fileContent);
				@SuppressWarnings("unchecked")
				List<Parameter> list = (List<Parameter>) JSONArray.toArray(jsonArray,
					Parameter.class);
				if (list != null && !list.isEmpty()) {
					for (Parameter c : list) {
						eventParamMap.put(c.getName(), c);
					}
				}
			} catch (Exception e) {
				logger.error(this.getClass(), e);
			}
		}

		/** 获取平台条件定义 **/
		public Parameter getEventParamByName(String paramName) {
			loadFile();
			return eventParamMap.get(paramName);
		}
	}

	public EventCache getEventCache() {
		return eventCache;
	}

	public void setEventCache(EventCache eventCache) {
		this.eventCache = eventCache;
	}

	public EventParamCache getEventParamCache() {
		return eventParamCache;
	}

	public void setEventParamCache(EventParamCache eventParamCache) {
		this.eventParamCache = eventParamCache;
	}

	public String getEventFilePath() {
		return eventFilePath;
	}

	public void setEventFilePath(String eventFilePath) {
		this.eventFilePath = eventFilePath;
	}

	public String getEventParamFilePath() {
		return eventParamFilePath;
	}

	public void setEventParamFilePath(String eventParamFilePath) {
		this.eventParamFilePath = eventParamFilePath;
	}
}
