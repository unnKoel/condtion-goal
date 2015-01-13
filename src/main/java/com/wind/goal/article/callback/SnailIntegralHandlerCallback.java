package com.wind.goal.article.callback;

import com.d1xn.common.log.Log;
import com.wind.goal.article.ArticleHandlerCallback;
import com.wind.platform.base.StatisticFacade;

/**
 * 积分处理回调
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-25
 */
public class SnailIntegralHandlerCallback implements ArticleHandlerCallback{

	private Long userId;
	private Integer integral;

	public SnailIntegralHandlerCallback(Long userId, Integer integral) {
		this.userId = userId;
		this.integral = integral;
	}

	public SnailIntegralHandlerCallback() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	@Override
	public void execute() {
		if(Log.isInfoEnabled()){
			Log.info(this.getClass(), "execute...");
		}
		if (integral >= 0) {
			StatisticFacade.getInstance().sendNewScoreInfo(userId, integral);
		} else {
			StatisticFacade.getInstance().sendScoreInfo(userId, integral);
		}
	}
}
