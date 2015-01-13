package com.wind.goal.article;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.d1xn.common.log.Log;
import com.d1xn.common.util.MemCachedLockUtil;
import com.d1xn.common.vo.ResultVO;
import com.d1xn.ddal.client.base.DDALAddOperator;
import com.d1xn.ddal.client.base.DDALUpdateOperator;
import com.d1xn.ddal.client.socket.AsynDDALHelper;
import com.d1xn.ddal.client.socket.base.IResultCallback;
import com.d1xn.ddal.client.socket.ddal.ResListCallback;
import com.d1xn.ddal.core.config.Condition;
import com.wind.core.common.PlatformErrorCode;
import com.wind.goal.res.ActivityCenterUserArticle;
import com.wind.goal.res.ActivityCenterUserArticleId;
import com.wind.goal.res.PlatformArticleCode;
import com.wind.goal.res.PlatformArticleCodeId;

/**
 * 奖品实物（兑换码）处理
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-24
 */
public class AwardEntityHandler extends AbstractHandler {
	public static final String NAME = "exchange";

	private Integer articleId;

	public AwardEntityHandler(ArticleType articleType, String lockKey, Long userId, AsynDDALHelper asynHelper,
			IResultCallback call, ArticleHandlerCallback aHCallback) {
		super(articleType, lockKey, userId, asynHelper, call, aHCallback);
	}

	public AwardEntityHandler() {
	}

	@Override
	public void handle() throws Exception {
		if(Log.isInfoEnabled()){
			Log.info(this.getClass(), "handle...");
		}
		if (getLockKey() == null) {
			setLockKey("exchange_" + articleId); // 锁键
		}
		final String cloneNowLockKey = getLockKey();
		boolean isLock = MemCachedLockUtil.lock(getLockKey(), 5);
		if (isLock) {
			Log.error(this.getClass(), "Locked award key=" + getLockKey());
			call.execute(new ResultVO(PlatformErrorCode.SNP_ACTIVITY_GOODS_PROCESS_FAIL));
		} else {
			/**
			 * 查询可用兑换码
			 */
			List<Condition> filter = new ArrayList<Condition>();
			filter.add(new Condition(PlatformArticleCodeId.FIELD_SGOODSID, articleId));
			filter.add(new Condition(PlatformArticleCode.FIELD_CSTATUS, "0"));
			this.getAsynHelper().query(PlatformArticleCode.class, filter, new ResListCallback<PlatformArticleCode>() {
				@Override
				public void succeed(List<PlatformArticleCode> obj) throws Exception {
					try {
						if (obj == null || obj.isEmpty()) {
							try {
								Log.warn(this.getClass(), "goods code is empty=" + articleId);
								call.execute(new ResultVO(PlatformErrorCode.SNP_ACTIVITY_GOODS_EMPTY));
								return;
							} finally {
								MemCachedLockUtil.unLock(cloneNowLockKey);
							}
						}
						// 更新兑换码状态
						PlatformArticleCode articleCode = obj.get(0);
						articleCode.setCStatus("1");
						addDDALOperator(new DDALUpdateOperator(articleCode.getId(), articleCode));

						// 添加用户奖品
						String code = articleCode.getId().getSCode();
						ActivityCenterUserArticleId id = new ActivityCenterUserArticleId();
						id.setNUserId(getUserId());
						ActivityCenterUserArticle userArticle = new ActivityCenterUserArticle();
						userArticle.setId(id);
						userArticle.setCStatus("1");
						userArticle.setSKeyCode(code);
						userArticle.setIGoodsId(articleId);
						userArticle.setCType(getArticleType().value);
						userArticle.setDGetTime(new Timestamp(new Date().getTime()));
						addDDALOperator(new DDALAddOperator(userArticle));

						setLockKey(cloneNowLockKey);
						batchSave();
					} catch (Exception e) {
						MemCachedLockUtil.unLock(cloneNowLockKey);
						Log.error(this.getClass(), e);
					}
				}

				@Override
				public void failure(ResultVO result) throws Exception {
					try {
						Log.error(this.getClass(), result.getErrorMsg());
					} finally {
						MemCachedLockUtil.unLock(cloneNowLockKey);
					}
				}
			});
		}
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
}
