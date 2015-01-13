package com.wind.goal.article;

import com.d1xn.common.log.Log;
import com.d1xn.common.util.MemCachedLockUtil;
import com.d1xn.ddal.client.base.DDALUpdateOperator;
import com.d1xn.ddal.client.socket.AsynDDALHelper;
import com.d1xn.ddal.client.socket.base.IResultCallback;
import com.wind.goal.res.UserBasicAttribute;

/**
 * 蜗牛币处理
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-24
 */
public class SnailCoinHandler extends AbstractHandler {

	private Integer snailCoin;

	public SnailCoinHandler(ArticleType articleType, String lockKey, Long userId, AsynDDALHelper asynHelper,
			IResultCallback call, ArticleHandlerCallback callback) {
		super(articleType, lockKey, userId, asynHelper, call, callback);
	}

	public SnailCoinHandler(ArticleType articleType, String lockKey, Long userId, AsynDDALHelper asynHelper,
			IResultCallback call) {
		super(articleType, lockKey, userId, asynHelper, call);
	}

	public SnailCoinHandler() {}

	@Override
	public void handle() {
		if(Log.isInfoEnabled()){
			Log.info(this.getClass(), "handle...");
		}
		try {
			UserBasicAttribute userBasicAttr = new UserBasicAttribute();
			userBasicAttr.setNUserId(getUserId());
			userBasicAttr.setICurrency(snailCoin);
			addDDALOperator(new DDALUpdateOperator(getUserId(), userBasicAttr, true));

			batchSave();
		} catch (Exception e) {
			if (getLockKey() != null) {
				MemCachedLockUtil.unLock(getLockKey());
			}
			Log.error(this.getClass(), e);
		}
	}

	public Integer getSnailCoin() {
		return snailCoin;
	}

	public void setSnailCoin(Integer snailCoin) {
		this.snailCoin = snailCoin;
	}
}
