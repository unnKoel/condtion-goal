package com.wind.goal.res;


import com.d1xn.ddal.client.base.Res;


import com.d1xn.ddal.client.base.ResId;
/**
 * @entity PlatformArticleClassifyDefine
 */
@Res(cluster = "appstore", space = "cms")
public class PlatformArticleClassifyDefine{
	public static final String FIELD_CARTICLETYPE = "cArticleType";
	public static final String FIELD_CHANDLECLASS = "cHandleClass";

	private String CArticleType; // 物品分类名
	private String CHandleClass; // 物品处理类


	/** default constructor */
	public PlatformArticleClassifyDefine(){
	}

	/** must constructor */
	public PlatformArticleClassifyDefine(String CArticleType,String CHandleClass){
		this.CArticleType = CArticleType;
		this.CHandleClass = CHandleClass;
	}

	@ResId
	public String getCArticleType(){
		return CArticleType;
	}

	public void setCArticleType(String CArticleType){
		this.CArticleType=CArticleType;
	}

	public String getCHandleClass(){
		return CHandleClass;
	}

	public void setCHandleClass(String CHandleClass){
		this.CHandleClass=CHandleClass;
	}

}

