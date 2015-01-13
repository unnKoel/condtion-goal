package com.wind.goal.article.callback;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.d1xn.common.log.Log;
import com.wind.goal.article.ArticleHandlerCallback;

/**
 * 组处理回调
 * 
 * @author mayf
 * @version 1.0 2014-3-25
 */
public class SnailGroupHandlerCallback implements ArticleHandlerCallback{

	private List<ArticleHandlerCallback> groupCallbacks = new CopyOnWriteArrayList<ArticleHandlerCallback>();

	public SnailGroupHandlerCallback() {
	}

	@Override
	public void execute() {
		if(Log.isInfoEnabled()){
			Log.info(this.getClass(), "execute...");
		}
		if(groupCallbacks != null && !groupCallbacks.isEmpty()){
			for (int i = 0; i < groupCallbacks.size(); i++) {
				groupCallbacks.get(i).execute();
			}
		}
	}
	
	/**
	 * 添加GroupCallback
	 * @param callback
	 */
	public void addGroupCallback(ArticleHandlerCallback callback) {
		groupCallbacks.add(callback);
	}
}
