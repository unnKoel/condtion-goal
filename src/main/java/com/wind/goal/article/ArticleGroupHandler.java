package com.wind.goal.article;

import sun.rmi.runtime.Log;

/**
 * 组物品处理器
 * 
 * @author mayf
 * @version 1.0 2014-4-1
 */

public class ArticleGroupHandler extends AbstractHandler {

	public ArticleGroupHandler(ArticleType articleType, String lockKey, Long userId, AsynDDALHelper asynHelper,
			IResultCallback call, ArticleHandlerCallback callback) {
		super(articleType, lockKey, userId, asynHelper, call, callback);
	}

	public ArticleGroupHandler(ArticleType articleType, String lockKey, Long userId, AsynDDALHelper asynHelper,
			IResultCallback call) {
		super(articleType, lockKey, userId, asynHelper, call);
	}

	@Override
	public void handle() throws Exception {
		if (Log.isInfoEnabled()) {
			Log.info(this.getClass(), "handle...");
		}
		batchSave();
	}
}
