package com.wind.goal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.wind.goal.FileCache;
import com.wind.goal.dao.po.Goal;

/**
 * 通用目标缓存DAO
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class CommonGoalCacheDAO {
	private static final Logger logger = Logger.getLogger(CommonGoalCacheDAO.class);
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
		private Map<String, Goal> commonGoalMapByPK;

		public CommonGoalCache(String filePath) {
			super(filePath);
		}

		@Override
		protected void loadData() {
			if (commonGoalMapByPK == null) {
				commonGoalMapByPK = new HashMap<String, Goal>();
			} else {
				commonGoalMapByPK.clear();
			}
			if (comonGoalMapByConId == null) {
				comonGoalMapByConId = new HashMap<Integer, List<String>>();
			} else {
				comonGoalMapByConId.clear();
			}
			try {
				String fileContent = this.readFile();
				JSONArray jsonArray = JSONArray.fromObject(fileContent);
				@SuppressWarnings("unchecked")
				List<Goal> list = (List<Goal>) JSONArray.toArray(jsonArray,
					Goal.class);
				if (list != null && !list.isEmpty()) {
					for (Goal p : list) {
						commonGoalMapByPK.put(p.getKey(), p);
						Integer conditionId = p.getConditionId();
						if (comonGoalMapByConId.keySet().contains(conditionId)) {
							List<String> goalIdList = comonGoalMapByConId.get(conditionId);
							goalIdList.add(p.getKey());
						} else {
							List<String> goalIdList = new ArrayList<String>();
							goalIdList.add(p.getKey());
							comonGoalMapByConId.put(conditionId, goalIdList);
						}
					}
				}
			} catch (Exception e) {
				logger.error(this.getClass(), e);
			}
		}

		public Goal getGoalByPK(String cKey) {
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
