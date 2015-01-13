package com.wind.goal.article;

import sun.rmi.runtime.Log;

import com.wind.goal.res.UserBasicAttribute;

/**
 * 物品处理 - 蜗牛积分
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-24
 */
public class SnailIntegralHandler extends AbstractHandler {
	private Integer integral;

	public SnailIntegralHandler(ArticleType articleType, String lockKey, Long userId, AsynDDALHelper asynHelper,
			IResultCallback call, ArticleHandlerCallback aHCallback) {
		super(articleType, lockKey, userId, asynHelper, call, aHCallback);
	}

	public SnailIntegralHandler() {
	}

	@Override
	public void handle() {
		if(Log.isInfoEnabled()){
			Log.info(this.getClass(), "handle...");
		}
		try {
			UserBasicAttribute userBasicAttr = new UserBasicAttribute();
			userBasicAttr.setNUserId(getUserId());
			userBasicAttr.setIPoints(integral);
			addDDALOperator(new DDALUpdateOperator(getUserId(), userBasicAttr, true));

			batchSave();
		} catch (Exception e) {
			if (getLockKey() != null) {
				MemCachedLockUtil.unLock(getLockKey());
			}
			Log.error(this.getClass(), e);
		}
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
}
