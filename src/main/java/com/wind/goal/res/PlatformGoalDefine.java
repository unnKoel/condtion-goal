package com.wind.goal.res;


import com.d1xn.ddal.client.base.Res;


import com.d1xn.ddal.client.base.ResId;
import java.sql.Timestamp;

/**
 * @entity PlatformGoalDefine
 */
@Res(cluster = "appstore", space = "cms")
public class PlatformGoalDefine{
	public static final String FIELD_CKEY = "cKey";
	public static final String FIELD_IREQUIREID = "iRequireId";
	public static final String FIELD_SNAME = "sName";
	public static final String FIELD_IAWARD = "iAward";
	public static final String FIELD_DUPDATE = "dUpdate";

	private String CKey; // 目标Key
	private Integer IRequireId; // 条件ID
	private String SName; // 成就名称
	private Integer IAward; // 成就奖励
	private Timestamp DUpdate; // 更新时间


	/** default constructor */
	public PlatformGoalDefine(){
	}

	/** must constructor */
	public PlatformGoalDefine(String CKey,String SName,Integer IAward){
		this.CKey = CKey;
		this.SName = SName;
		this.IAward = IAward;
	}

	/** full constructor */
	public PlatformGoalDefine(String CKey,Integer IRequireId,String SName,Integer IAward,Timestamp DUpdate){
		this.CKey = CKey;
		this.IRequireId = IRequireId;
		this.SName = SName;
		this.IAward = IAward;
		this.DUpdate = DUpdate;
	}

	@ResId
	public String getCKey(){
		return CKey;
	}

	public void setCKey(String CKey){
		this.CKey=CKey;
	}

	public Integer getIRequireId(){
		return IRequireId;
	}

	public void setIRequireId(Integer IRequireId){
		this.IRequireId=IRequireId;
	}

	public String getSName(){
		return SName;
	}

	public void setSName(String SName){
		this.SName=SName;
	}

	public Integer getIAward(){
		return IAward;
	}

	public void setIAward(Integer IAward){
		this.IAward=IAward;
	}

	public Timestamp getDUpdate(){
		return DUpdate;
	}

	public void setDUpdate(Timestamp DUpdate){
		this.DUpdate=DUpdate;
	}

}

