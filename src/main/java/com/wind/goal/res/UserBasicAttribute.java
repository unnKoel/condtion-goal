package com.wind.goal.res;



import com.d1xn.ddal.client.base.Res;


import com.d1xn.ddal.client.base.ResId;
import java.util.Date;
/**
 * @entity UserBasicAttribute
 */
@Res(cluster = "appstore", space = "platform")
public class UserBasicAttribute{
	public static final String FIELD_NUSERID = "nUserId";
	public static final String FIELD_SNICKNAME = "sNickname";
	public static final String FIELD_CEMAIL = "cEmail";
	public static final String FIELD_CPHOTO = "cPhoto";
	public static final String FIELD_DBIRTHDAY = "dBirthday";
	public static final String FIELD_IPOINTS = "iPoints";
	public static final String FIELD_ICURRENCY = "iCurrency";
	public static final String FIELD_CSEX = "cSex";
	public static final String FIELD_IVIP = "iVip";
	public static final String FIELD_CSNAILPACKAGE = "cSnailPackage";
	public static final String FIELD_CPHONE = "cPhone";
	public static final String FIELD_CEMAILSTATUS = "cEmailStatus";
	public static final String FIELD_CAGE = "cAge";

	private Long NUserId; // 用户ID
	private String SNickname; // 昵称
	private String CEmail; // 邮箱
	private String CPhoto; // 头像
	private Date DBirthday; // 生日
	private Integer IPoints; // 积分
	private Integer ICurrency; // 蜗牛币
	private String CSex; // 性别，1：男，2：女
	private Integer IVip; // VIP等级
	private String CSnailPackage; // 是否开通蜗牛沃包，0：未开通，1：已开通
	private String CPhone; // 绑定的手机号码
	private String CEmailStatus; // 箱邮绑定状态，0：未绑定，1：绑定
	private String CAge; // 年龄，1：16-20，2：21-25，3：26-30，4：31-35，5：36-40，6：41-45，7：46-50，8：51-55，9：56-60


	/** default constructor */
	public UserBasicAttribute(){
	}

	/** must constructor */
	public UserBasicAttribute(Long NUserId,Integer IPoints,Integer ICurrency,Integer IVip,String CSnailPackage){
		this.NUserId = NUserId;
		this.IPoints = IPoints;
		this.ICurrency = ICurrency;
		this.IVip = IVip;
		this.CSnailPackage = CSnailPackage;
	}

	/** full constructor */
	public UserBasicAttribute(Long NUserId,String SNickname,String CEmail,String CPhoto,Date DBirthday,Integer IPoints,Integer ICurrency,String CSex,Integer IVip,String CSnailPackage,String CPhone,String CEmailStatus,String CAge){
		this.NUserId = NUserId;
		this.SNickname = SNickname;
		this.CEmail = CEmail;
		this.CPhoto = CPhoto;
		this.DBirthday = DBirthday;
		this.IPoints = IPoints;
		this.ICurrency = ICurrency;
		this.CSex = CSex;
		this.IVip = IVip;
		this.CSnailPackage = CSnailPackage;
		this.CPhone = CPhone;
		this.CEmailStatus = CEmailStatus;
		this.CAge = CAge;
	}

	@ResId
	public Long getNUserId(){
		return NUserId;
	}

	public void setNUserId(Long NUserId){
		this.NUserId=NUserId;
	}

	public String getSNickname(){
		return SNickname;
	}

	public void setSNickname(String SNickname){
		this.SNickname=SNickname;
	}

	public String getCEmail(){
		return CEmail;
	}

	public void setCEmail(String CEmail){
		this.CEmail=CEmail;
	}

	public String getCPhoto(){
		return CPhoto;
	}

	public void setCPhoto(String CPhoto){
		this.CPhoto=CPhoto;
	}

	public Date getDBirthday(){
		return DBirthday;
	}

	public void setDBirthday(Date DBirthday){
		this.DBirthday=DBirthday;
	}

	public Integer getIPoints(){
		return IPoints;
	}

	public void setIPoints(Integer IPoints){
		this.IPoints=IPoints;
	}

	public Integer getICurrency(){
		return ICurrency;
	}

	public void setICurrency(Integer ICurrency){
		this.ICurrency=ICurrency;
	}

	public String getCSex(){
		return CSex;
	}

	public void setCSex(String CSex){
		this.CSex=CSex;
	}

	public Integer getIVip(){
		return IVip;
	}

	public void setIVip(Integer IVip){
		this.IVip=IVip;
	}

	public String getCSnailPackage(){
		return CSnailPackage;
	}

	public void setCSnailPackage(String CSnailPackage){
		this.CSnailPackage=CSnailPackage;
	}

	public String getCPhone(){
		return CPhone;
	}

	public void setCPhone(String CPhone){
		this.CPhone=CPhone;
	}

	public String getCEmailStatus(){
		return CEmailStatus;
	}

	public void setCEmailStatus(String CEmailStatus){
		this.CEmailStatus=CEmailStatus;
	}

	public String getCAge(){
		return CAge;
	}

	public void setCAge(String CAge){
		this.CAge=CAge;
	}

}
