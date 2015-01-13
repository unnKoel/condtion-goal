package com.wind.goal.article;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.d1xn.common.log.Log;
import com.d1xn.common.util.MemCachedLockUtil;
import com.d1xn.common.vo.ResultVO;
import com.d1xn.ddal.client.base.DDALOperator;
import com.d1xn.ddal.client.socket.AsynDDALHelper;
import com.d1xn.ddal.client.socket.base.IResultCallback;
import com.d1xn.ddal.client.socket.ddal.UpdateCallback;

/**
 * 物品处理抽象类
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-24
 */
public abstract class AbstractHandler implements ArticleHandler {

	private String lockKey; // 锁
	protected ArticleHandlerCallback callback; // 物品处理回调
	protected AsynDDALHelper asynHelper;
	protected IResultCallback call;
	protected List<DDALOperator> ddalOperatorList = new CopyOnWriteArrayList<DDALOperator>(); // 数据库操作
	private ArticleType articleType;
	private AbstractHandler nextHandler;
	private Long userId;
//	private boolean isTransaction = true; //是否事务处理

	protected AbstractHandler() {}

	protected AbstractHandler(ArticleType articleType, String lockKey, Long userId, AsynDDALHelper asynHelper,
			IResultCallback call, ArticleHandlerCallback callback) {
		this.articleType = articleType;
		this.userId = userId;
		this.lockKey = lockKey;
		this.asynHelper = asynHelper;
		this.call = call;
		this.callback = callback;
	}

	public AbstractHandler(ArticleHandlerCallback callback) {
		this.callback = callback;
	}

	public AbstractHandler(ArticleType articleType, Long userId, ArticleHandlerCallback callback) {
		this.articleType = articleType;
		this.userId = userId;
		this.callback = callback;
	}

	public AbstractHandler(ArticleType articleType, String lockKey, Long userId, AsynDDALHelper asynHelper, IResultCallback call) {
		this.articleType = articleType;
		this.userId = userId;
		this.lockKey = lockKey;
		this.asynHelper = asynHelper;
		this.call = call;
	}

	public void batchSave() throws Exception {
		if (getNextHandler() != null) {
			getNextHandler().setCallback(callback);
			getNextHandler().setDdalOperatorList(ddalOperatorList);
			getNextHandler().handle();
		} else{
			asynHelper.batchSave(ddalOperatorList, new UpdateCallback() {
				@Override
				public void succeed(int row) throws Exception {
					try {
						if (row > 0) {
							call.execute(new ResultVO("true"));
							if (callback != null) {
								callback.execute();
							}
						} else {
							call.execute(new ResultVO("false"));
						}
					} finally {
						if (lockKey != null) {
							MemCachedLockUtil.unLock(lockKey);
						}
					}
				}

				@Override
				public void failure(ResultVO result) throws Exception {
					try {
						Log.error(this.getClass(), result.getErrorMsg());
					} finally {
						MemCachedLockUtil.unLock(lockKey);
					}
				}
			});
		}
	}

	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}

	public void addDDALOperator(DDALOperator ddalOperator) {
		ddalOperatorList.add(ddalOperator);
	}

	public void addDDALOperators(List<DDALOperator> ddalOperators) {
		ddalOperatorList.addAll(ddalOperators);
	}

	public List<DDALOperator> getDdalOperatorList() {
		return ddalOperatorList;
	}

	public void setDdalOperatorList(List<DDALOperator> ddalOperatorList) {
		this.ddalOperatorList = ddalOperatorList;
	}

	/**
	 * 物品类型
	 * 
	 * @author zhouyanjun
	 * @version 1.0 2014-3-25
	 */
	public enum ArticleType {
		AWARD("1"), EXCHANGER("2"), SPREE("3"), ACTIVITY("4"); // 奖品，兑换品，礼包，活动
		public String value;

		private ArticleType(String value) {
			this.value = value;
		}
	}

	public AsynDDALHelper getAsynHelper() {
		return asynHelper;
	}

	public void setAsynHelper(AsynDDALHelper asynHelper) {
		this.asynHelper = asynHelper;
	}

	public IResultCallback getCall() {
		return call;
	}

	public void setCall(IResultCallback call) {
		this.call = call;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLockKey() {
		return lockKey;
	}

	public ArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleType articleType) {
		this.articleType = articleType;
	}

	public AbstractHandler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(AbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	public ArticleHandlerCallback getCallback() {
		return callback;
	}

	public void setCallback(ArticleHandlerCallback callback) {
		this.callback = callback;
	}

//	public boolean isTransaction() {
//		return isTransaction;
//	}
//
//	public void setTransaction(boolean isTransaction) {
//		this.isTransaction = isTransaction;
//	}
}
