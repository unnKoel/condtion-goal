package com.wind.goal.article.callback;

import com.d1xn.common.log.Log;
import com.wind.goal.article.ArticleHandlerCallback;
import com.wind.platform.base.StatisticFacade;
import com.wind.platform.center.vo.ActivityConstants;

/**
 * 蜗牛币处理回调
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-25
 */
public class SnailCoinHandlerCallback implements ArticleHandlerCallback {

	private Long userId;
	private Integer snailCoin;

	public SnailCoinHandlerCallback() {
		super();
	}

	public SnailCoinHandlerCallback(Long userId, Integer snailCoin) {
		super();
		this.userId = userId;
		this.snailCoin = snailCoin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getSnailCoin() {
		return snailCoin;
	}

	public void setSnailCoin(Integer snailCoin) {
		this.snailCoin = snailCoin;
	}

	@Override
	public void execute() {
		if(Log.isInfoEnabled()){
			Log.info(this.getClass(), "execute...");
		}
		if (snailCoin > 0) {
			StatisticFacade.getInstance().sendNewCurrencyInfo(userId, ActivityConstants.APP_ID_SNAIL_STORE, snailCoin);
		} else {
			StatisticFacade.getInstance().sendConsumCurrencyInfo(userId, ActivityConstants.APP_ID_SNAIL_STORE, snailCoin);
		}
	}
}
