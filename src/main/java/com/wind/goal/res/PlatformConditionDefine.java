package com.wind.goal.res;


import com.d1xn.ddal.client.base.Res;


import com.d1xn.ddal.client.base.ResId;
import java.sql.Timestamp;

/**
 * @entity PlatformConditionDefine
 */
@Res(cluster = "appstore", space = "cms")
public class PlatformConditionDefine{
	public static final String FIELD_CREQUIREDESC = "cRequireDesc";
	public static final String FIELD_CREQUIREVALUE = "cRequireValue";
	public static final String FIELD_DUPDATE = "dUpdate";

	private PlatformConditionDefineId id; // id
	private String CRequireDesc; // 条件描述
	private String CRequireValue; // 条件满足值
	private Timestamp DUpdate; // 更新时间


	/** default constructor */
	public PlatformConditionDefine(){
	}

	/** must constructor */
	public PlatformConditionDefine(PlatformConditionDefineId id,String CRequireDesc,String CRequireValue){
		this.id = id;
		this.CRequireDesc = CRequireDesc;
		this.CRequireValue = CRequireValue;
	}

	/** full constructor */
	public PlatformConditionDefine(PlatformConditionDefineId id,String CRequireDesc,String CRequireValue,Timestamp DUpdate){
		this.id = id;
		this.CRequireDesc = CRequireDesc;
		this.CRequireValue = CRequireValue;
		this.DUpdate = DUpdate;
	}

	@ResId
	public PlatformConditionDefineId getId(){
		return id;
	}

	public void setId(PlatformConditionDefineId id){
		this.id=id;
	}

	public String getCRequireDesc(){
		return CRequireDesc;
	}

	public void setCRequireDesc(String CRequireDesc){
		this.CRequireDesc=CRequireDesc;
	}

	public String getCRequireValue(){
		return CRequireValue;
	}

	public void setCRequireValue(String CRequireValue){
		this.CRequireValue=CRequireValue;
	}

	public Timestamp getDUpdate(){
		return DUpdate;
	}

	public void setDUpdate(Timestamp DUpdate){
		this.DUpdate=DUpdate;
	}

}

