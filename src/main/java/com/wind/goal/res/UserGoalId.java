package com.wind.goal.res;


import com.d1xn.ddal.client.base.ResId;
import com.d1xn.common.base.IPojo;
/**
 * @entity UserGoalId
 */
@ResId
public class UserGoalId implements IPojo
{
	public static final String FIELD_IID = "iId";
	public static final String FIELD_ITASKKEY = "iTaskKey";

	private Long IId; // 用户ID
	private String ITaskKey; // 目标key


	/** default constructor */
	public UserGoalId(){
	}

	/** full constructor */
	public UserGoalId(Long IId,String ITaskKey){
		this.IId = IId;
		this.ITaskKey = ITaskKey;
	}

	public Long getIId(){
		return IId;
	}

	public void setIId(Long IId){
		this.IId=IId;
	}

	public String getITaskKey(){
		return ITaskKey;
	}

	public void setITaskKey(String ITaskKey){
		this.ITaskKey=ITaskKey;
	}

}

