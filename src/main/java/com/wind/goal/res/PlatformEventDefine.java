package com.wind.goal.res;


import com.d1xn.ddal.client.base.Res;


import com.d1xn.ddal.client.base.ResId;
import java.sql.Timestamp;

/**
 * @entity PlatformEventDefine
 */
@Res(cluster = "appstore", space = "cms")
public class PlatformEventDefine{
	public static final String FIELD_CEVNETNO = "cEvnetNo";
	public static final String FIELD_CEVENTNAME = "cEventName";
	public static final String FIELD_CEVENTEXPLAIN = "cEventExplain";
	public static final String FIELD_CPARAMTERS = "cParamters";
	public static final String FIELD_DUPDATE = "dUpdate";

	private String CEvnetNo; // 事件号
	private String CEventName; // 事件名称
	private String CEventExplain; // 事件说明
	private String CParamters; // 参数列表
	private Timestamp DUpdate; // 更新时间


	/** default constructor */
	public PlatformEventDefine(){
	}

	/** must constructor */
	public PlatformEventDefine(String CEvnetNo,String CEventName){
		this.CEvnetNo = CEvnetNo;
		this.CEventName = CEventName;
	}

	/** full constructor */
	public PlatformEventDefine(String CEvnetNo,String CEventName,String CEventExplain,String CParamters,Timestamp DUpdate){
		this.CEvnetNo = CEvnetNo;
		this.CEventName = CEventName;
		this.CEventExplain = CEventExplain;
		this.CParamters = CParamters;
		this.DUpdate = DUpdate;
	}

	@ResId
	public String getCEvnetNo(){
		return CEvnetNo;
	}

	public void setCEvnetNo(String CEvnetNo){
		this.CEvnetNo=CEvnetNo;
	}

	public String getCEventName(){
		return CEventName;
	}

	public void setCEventName(String CEventName){
		this.CEventName=CEventName;
	}

	public String getCEventExplain(){
		return CEventExplain;
	}

	public void setCEventExplain(String CEventExplain){
		this.CEventExplain=CEventExplain;
	}

	public String getCParamters(){
		return CParamters;
	}

	public void setCParamters(String CParamters){
		this.CParamters=CParamters;
	}

	public Timestamp getDUpdate(){
		return DUpdate;
	}

	public void setDUpdate(Timestamp DUpdate){
		this.DUpdate=DUpdate;
	}

}

