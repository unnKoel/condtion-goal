package com.wind.goal.res;


import com.d1xn.ddal.client.base.ResId;
import com.d1xn.common.base.IPojo;
/**
 * @entity PlatformArticleCodeId
 */
@ResId
public class PlatformArticleCodeId implements IPojo
{
	public static final String FIELD_SCODE = "sCode";
	public static final String FIELD_SGOODSID = "sGoodsId";

	private String SCode; // 兑换码
	private Integer SGoodsId; // 物品ID


	/** default constructor */
	public PlatformArticleCodeId(){
	}

	/** full constructor */
	public PlatformArticleCodeId(String SCode,Integer SGoodsId){
		this.SCode = SCode;
		this.SGoodsId = SGoodsId;
	}

	public String getSCode(){
		return SCode;
	}

	public void setSCode(String SCode){
		this.SCode=SCode;
	}

	public Integer getSGoodsId(){
		return SGoodsId;
	}

	public void setSGoodsId(Integer SGoodsId){
		this.SGoodsId=SGoodsId;
	}

}

