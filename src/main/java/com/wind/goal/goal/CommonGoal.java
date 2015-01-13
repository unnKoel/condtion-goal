package com.wind.goal.goal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.d1xn.common.log.Log;
import com.d1xn.common.vo.JSONResultVO;
import com.d1xn.common.vo.ResultVO;
import com.d1xn.ddal.client.base.DDALAddOperator;
import com.d1xn.ddal.client.base.DDALOperator;
import com.d1xn.ddal.client.base.DDALUpdateOperator;
import com.d1xn.ddal.client.socket.AsynDDALHelper;
import com.d1xn.ddal.client.socket.ddal.Callback;
import com.d1xn.ddal.client.socket.ddal.ResCallback;
import com.d1xn.ddal.client.socket.ddal.ResListCallback;
import com.wind.goal.cache.CommonGoalCacheDAO;
import com.wind.goal.res.UserGoal;
import com.wind.goal.res.UserGoalId;

/**
 * 通用目标表挂载点处理
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class CommonGoal extends MountGoalPoint {
	private AsynDDALHelper asynHelper;

	@Override
	public void completeUserGoal() {
		/**
		 * 根据完成的条件查询所有完成的目标
		 */
		final List<String> completeGoalPKs = new ArrayList<String>();
		for (Object completeConditionId : completeConditionIds) {
			List<String> goalPKs = CommonGoalCacheDAO.getGoalIdsByConId((Integer) completeConditionId);
			completeGoalPKs.addAll(goalPKs);
		}
		// 如果没有目标挂载在满足的条件上，则直接返回
		if (completeGoalPKs.isEmpty()) return;
		Long userId = event.getUserId();
		if (userId == null) return;
		try {
			final List<DDALOperator> operatorList = new ArrayList<DDALOperator>();
			getUserAchieve(new ResCallback<Map<String, UserGoal>>() {
				@Override
				public void succeed(Map<String, UserGoal> goalMap) throws Exception {
					for (String goalId : goalMap.keySet()) {// 更新目的为完成状态
						completeGoalPKs.remove(goalId);
						UserGoal userGoal = goalMap.get(goalId);
						userGoal.setCStatus(UserGoalStatus.GET_ABLE.value);
						userGoal.setDTouchTime(new Timestamp(new Date().getTime()));
						operatorList.add(new DDALUpdateOperator(userGoal.getId(), userGoal));
					}
					if (!completeGoalPKs.isEmpty()) {
						for (String completeGoal : completeGoalPKs) {
							UserGoalId goalSysUserGoalId = new UserGoalId(event.getUserId(), completeGoal);
							UserGoal userGoal = new UserGoal();
							userGoal.setId(goalSysUserGoalId);
							userGoal.setCStatus(UserGoalStatus.GET_ABLE.value);
							userGoal.setDTouchTime(new Timestamp(new Date().getTime()));
							operatorList.add(new DDALAddOperator(userGoal));
						}
					}
					asynHelper.batchSave(operatorList, new Callback() {

						@Override
						public void succeed(JSONResultVO result) throws Exception {
							if (result.isErrorVO()) {
								Log.error(this.getClass(), result.getErrorMsg());
								return;
							}
						}

						@Override
						public void failure(ResultVO result) throws Exception {
							Log.error(this.getClass(), result.getErrorMsg());
						}
					});
				}

				@Override
				public void failure(ResultVO result) throws Exception {
					Log.error(this.getClass(), result.getErrorMsg());
				}
			}, userId, completeGoalPKs);
		} catch (Exception e) {
			Log.error(this.getClass(), e);
		}
	}

	/**
	 * 获取用户目标记录
	 * 
	 * @param userId
	 *            用户ID
	 * @param 目标Ids
	 *            用户
	 * @throws Exception
	 */
	private void getUserAchieve(final ResCallback<Map<String, UserGoal>> call, Long userId, List<String> completeGoalPKs)
		throws Exception {
		final Map<String, UserGoal> map = new HashMap<String, UserGoal>();
		if (completeGoalPKs == null || completeGoalPKs.size() < 1) {
			call.failure(new ResultVO(null, map));
			return;
		}
		List<Object> pks = new ArrayList<Object>();
		for (String pk : completeGoalPKs) {
			pks.add(new UserGoalId(userId, pk));
		}
		asynHelper.gets(UserGoal.class, pks, new ResListCallback<UserGoal>() {
			@Override
			public void succeed(List<UserGoal> list) throws Exception {
				if (list != null && !list.isEmpty()) {
					for (UserGoal obj : list) {
						map.put(obj.getId().getITaskKey(), obj);
					}
				}
				call.succeed(map);
			}

			@Override
			public void failure(ResultVO result) throws Exception {
				call.failure(result);
			}
		});
	}

	public enum UserGoalStatus {
		GET_ABLE("1"), GET_UNABLE("2");

		public String value;

		private UserGoalStatus(String value) {
			this.value = value;
		}
	}

	public AsynDDALHelper getAsynHelper() {
		return asynHelper;
	}

	public void setAsynHelper(AsynDDALHelper asynHelper) {
		this.asynHelper = asynHelper;
	}
}
