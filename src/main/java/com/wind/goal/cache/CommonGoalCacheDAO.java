package com.wind.goal.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.rmi.runtime.Log;

import com.wind.goal.res.PlatformGoalDefine;

/**
 * 通用目标缓存DAO
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class CommonGoalCacheDAO {
	private CommonGoalCache commonGoalCache;
	private String filePath;

	private static final CommonGoalCacheDAO instance = new CommonGoalCacheDAO();

	public void init() {
		commonGoalCache = new CommonGoalCache(filePath);
	}

	/** 构造函数 **/
	private CommonGoalCacheDAO() {
		super();
	}

	/** 对外接口 */
	public static CommonGoalCacheDAO getInstance() {
		return instance;
	}

	public static List<String> getGoalIdsByConId(Integer conId) {
		return getInstance().getCommonGoalCache().getGoalIdsByConId(conId);
	}

	/**
	 * 通用目标缓存
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-13
	 */
	class CommonGoalCache extends FileCache {
		private Map<Integer, List<String>> comonGoalMapByConId; // 条件ID上注册的目标
		private Map<String, PlatformGoalDefine> commonGoalMapByPK;

		public CommonGoalCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (commonGoalMapByPK == null) {
				commonGoalMapByPK = new HashMap<String, PlatformGoalDefine>();
			} else {
				commonGoalMapByPK.clear();
			}
			if (comonGoalMapByConId == null) {
				comonGoalMapByConId = new HashMap<Integer, List<String>>();
			} else {
				comonGoalMapByConId.clear();
			}
			try {
				JSONObject json = JSONObjUtil.file2JsonObject(getFile());
				JSONListObj listObj = new JSONListObj(json);
				TableVO<PlatformGoalDefine> table = new TableVO<PlatformGoalDefine>(PlatformGoalDefine.class, listObj);
				List<PlatformGoalDefine> list = table.getObjectList();
				if (list != null && !list.isEmpty()) {
					for (PlatformGoalDefine p : list) {
						commonGoalMapByPK.put(p.getCKey(), p);
						Integer iConditionId = p.getIRequireId();
						if (comonGoalMapByConId.keySet().contains(iConditionId)) {
							List<String> goalIdList = comonGoalMapByConId.get(iConditionId);
							goalIdList.add(p.getCKey());
						} else {
							List<String> goalIdList = new ArrayList<String>();
							goalIdList.add(p.getCKey());
							comonGoalMapByConId.put(iConditionId, goalIdList);
						}
					}
				}
			} catch (Exception e) {
				Log.error(this.getClass(), e);
			}
		}

		public PlatformGoalDefine getGoalByPK(String cKey) {
			this.loadFile();
			return commonGoalMapByPK.get(cKey);
		}

		public List<String> getGoalIdsByConId(Integer conId) {
			this.loadFile();
			return comonGoalMapByConId.get(conId);
		}
	}

	public CommonGoalCache getCommonGoalCache() {
		return commonGoalCache;
	}

	public void setCommonGoalCache(CommonGoalCache commonGoalCache) {
		this.commonGoalCache = commonGoalCache;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
