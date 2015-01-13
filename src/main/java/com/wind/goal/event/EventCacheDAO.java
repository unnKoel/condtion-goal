package com.wind.goal.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.d1xn.common.base.FileCache;
import com.d1xn.common.json.JSONListObj;
import com.d1xn.common.json.JSONObject;
import com.d1xn.common.log.Log;
import com.d1xn.common.rest.TableVO;
import com.d1xn.common.util.JSONObjUtil;
import com.wind.goal.res.PlatformEventDefine;
import com.wind.goal.res.PlatformParameterDefine;
import com.wind.goal.vo.ParamterVO;

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
	public static PlatformEventDefine getEventByeventNO(String eventNO) {
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
		PlatformParameterDefine paramterDefine = getInstance().getEventParamCache().getEventParamByName(paramName);
		ParamterVO paramterVO = paramterDefine.getParamterVO();
		return paramterVO;
	}

	class EventCache extends FileCache {
		private Map<String, PlatformEventDefine> eventMap;

		public EventCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (eventMap == null) {
				eventMap = new HashMap<String, PlatformEventDefine>();
			} else {
				eventMap.clear();
			}
			try {
				JSONObject json = JSONObjUtil.file2JsonObject(getFile());
				JSONListObj listObj = new JSONListObj(json);
				TableVO<PlatformEventDefine> table = new TableVO<PlatformEventDefine>(PlatformEventDefine.class, listObj);
				List<PlatformEventDefine> list = table.getObjectList();
				if (list != null && !list.isEmpty()) {
					for (PlatformEventDefine c : list) {
						eventMap.put(c.getCEvnetNo(), c);
					}
				}
			} catch (Exception e) {
				Log.error(this.getClass(), e);
			}
		}

		/** 获取平台条件定义 **/
		public PlatformEventDefine getEventByEventNO(String EventNO) {
			loadFile();
			return eventMap.get(EventNO);
		}
	}

	class EventParamCache extends FileCache {
		private Map<String, PlatformParameterDefine> eventParamMap;

		public EventParamCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (eventParamMap == null) {
				eventParamMap = new HashMap<String, PlatformParameterDefine>();
			} else {
				eventParamMap.clear();
			}
			try {
				JSONObject json = JSONObjUtil.file2JsonObject(getFile());
				JSONListObj listObj = new JSONListObj(json);
				TableVO<PlatformParameterDefine> table = new TableVO<PlatformParameterDefine>(PlatformParameterDefine.class,
					listObj);
				List<PlatformParameterDefine> list = table.getObjectList();
				if (list != null && !list.isEmpty()) {
					for (PlatformParameterDefine c : list) {
						eventParamMap.put(c.getCName(), c);
					}
				}
			} catch (Exception e) {
				Log.error(this.getClass(), e);
			}
		}

		/** 获取平台条件定义 **/
		public PlatformParameterDefine getEventParamByName(String paramName) {
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
