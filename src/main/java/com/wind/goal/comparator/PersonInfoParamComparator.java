package com.wind.goal.comparator;

import java.util.Date;
import java.util.Map;
import com.d1xn.ddal.client.socket.AsynDDALHelper;
import com.d1xn.ddal.client.socket.ddal.imp.SyncResCallback;
import com.wind.goal.event.Event;
import com.wind.goal.vo.ParamterVO;
import com.wind.res.platform.user.UserBasicAttribute;

/**
 * 个人信息比较
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-21
 */
public class PersonInfoParamComparator implements SelfDefinedParamComparator {
	private AsynDDALHelper asynHelper;

	@Override
	public boolean compare(Map<ParamterVO, CompareParamVO> compareParams, Event event) {
		CompareParamVO compareParam = compareParams.get(new ParamterVO("is_person_info"));
		Object lastUserInfo = compareParam.getLastCurUserValue();
		boolean iscomplete = false;
		Long userId = event.getUserId();
		if (event.getUserId() == null) {
			return false;
		}
		if (lastUserInfo == null || (Boolean) lastUserInfo == false) {
			SyncResCallback<UserBasicAttribute> sync = new SyncResCallback<UserBasicAttribute>();
			asynHelper.get(UserBasicAttribute.class, userId, sync);
			UserBasicAttribute userAttribute = sync.getResult();
			String sex = userAttribute.getCSex();
			Date birtchDay = userAttribute.getDBirthday();
			String photo = userAttribute.getCPhoto();
			if (sex != null && birtchDay != null && photo != null) {
				iscomplete = true;
				compareParam.setNowCurUserValue(true);
			} else {
				compareParam.setNowCurUserValue(false);
			}
		} else if ((Boolean) lastUserInfo == true) {
			iscomplete = false;
		}
		return iscomplete;
	}

	public AsynDDALHelper getAsynHelper() {
		return asynHelper;
	}

	public void setAsynHelper(AsynDDALHelper asynHelper) {
		this.asynHelper = asynHelper;
	}
}
