package com.wind.goal.res;


import com.d1xn.ddal.client.base.Res;


import com.d1xn.ddal.client.base.ResId;
/**
 * @entity PlatformArticleCode
 */
@Res(cluster = "appstore", space = "cms")
public class PlatformArticleCode{
	public static final String FIELD_CSTATUS = "cStatus";

	private PlatformArticleCodeId id; // id
	private String CStatus; // 兑换状态0:未兑换； 1：已兑换


	/** default constructor */
	public PlatformArticleCode(){
	}

	/** must constructor */
	public PlatformArticleCode(PlatformArticleCodeId id,String CStatus){
		this.id = id;
		this.CStatus = CStatus;
	}

	@ResId
	public PlatformArticleCodeId getId(){
		return id;
	}

	public void setId(PlatformArticleCodeId id){
		this.id=id;
	}

	public String getCStatus(){
		return CStatus;
	}

	public void setCStatus(String CStatus){
		this.CStatus=CStatus;
	}

}

