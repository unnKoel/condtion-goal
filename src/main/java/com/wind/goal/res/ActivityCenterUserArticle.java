package com.wind.goal.res;

import com.d1xn.ddal.client.base.Res;
import com.d1xn.ddal.client.base.ResId;
import java.sql.Timestamp;

/**
 * @entity ActivityCenterUserArticle
 */
@Res(cluster = "appstore", space = "platform")
public class ActivityCenterUserArticle {
	public static final String FIELD_IGOODSID = "iGoodsId";
	public static final String FIELD_CTYPE = "cType";
	public static final String FIELD_SKEYCODE = "sKeyCode";
	public static final String FIELD_CSTATUS = "cStatus";
	public static final String FIELD_DGETTIME = "dGetTime";

	private ActivityCenterUserArticleId id; // id
	private Integer IGoodsId; // 物品ID
	private String CType; // 物品来源, 1:奖品； 2：兑换; 3:礼包
	private String SKeyCode; // cdkey 或 领奖编码
	private String CStatus; // 1:已领取； 0：未领取
	private Timestamp DGetTime; // 物品获取时间

	/** default constructor */
	public ActivityCenterUserArticle() {}

	/** must constructor */
	public ActivityCenterUserArticle(ActivityCenterUserArticleId id, Integer IGoodsId, String CType, String CStatus,
			Timestamp DGetTime) {
		this.id = id;
		this.IGoodsId = IGoodsId;
		this.CType = CType;
		this.CStatus = CStatus;
		this.DGetTime = DGetTime;
	}

	/** full constructor */
	public ActivityCenterUserArticle(ActivityCenterUserArticleId id, Integer IGoodsId, String CType, String SKeyCode,
			String CStatus, Timestamp DGetTime) {
		this.id = id;
		this.IGoodsId = IGoodsId;
		this.CType = CType;
		this.SKeyCode = SKeyCode;
		this.CStatus = CStatus;
		this.DGetTime = DGetTime;
	}

	@ResId
	public ActivityCenterUserArticleId getId() {
		return id;
	}

	public void setId(ActivityCenterUserArticleId id) {
		this.id = id;
	}

	public Integer getIGoodsId() {
		return IGoodsId;
	}

	public void setIGoodsId(Integer IGoodsId) {
		this.IGoodsId = IGoodsId;
	}

	public String getCType() {
		return CType;
	}

	public void setCType(String CType) {
		this.CType = CType;
	}

	public String getSKeyCode() {
		return SKeyCode;
	}

	public void setSKeyCode(String SKeyCode) {
		this.SKeyCode = SKeyCode;
	}

	public String getCStatus() {
		return CStatus;
	}

	public void setCStatus(String CStatus) {
		this.CStatus = CStatus;
	}

	public Timestamp getDGetTime() {
		return DGetTime;
	}

	public void setDGetTime(Timestamp DGetTime) {
		this.DGetTime = DGetTime;
	}

}
