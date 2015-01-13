package com.wind.goal.res;


import com.d1xn.ddal.client.base.ResId;
import com.d1xn.common.base.IPojo;
/**
 * @entity PlatformConditionDefineId
 */
@ResId
public class PlatformConditionDefineId implements IPojo
{
	public static final String FIELD_ICONDITIONID = "iConditionId";
	public static final String FIELD_ICATEGORYID = "iCategoryId";

	private Integer IConditionId; // 条件ID
	private Integer ICategoryId; // 条件类别ID


	/** default constructor */
	public PlatformConditionDefineId(){
	}

	/** full constructor */
	public PlatformConditionDefineId(Integer IConditionId,Integer ICategoryId){
		this.IConditionId = IConditionId;
		this.ICategoryId = ICategoryId;
	}

	public Integer getIConditionId(){
		return IConditionId;
	}

	public void setIConditionId(Integer IConditionId){
		this.IConditionId=IConditionId;
	}

	public Integer getICategoryId(){
		return ICategoryId;
	}

	public void setICategoryId(Integer ICategoryId){
		this.ICategoryId=ICategoryId;
	}

}

