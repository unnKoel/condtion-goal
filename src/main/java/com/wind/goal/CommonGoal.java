package com.wind.goal;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wind.goal.dao.IUserGoalDao;
import com.wind.goal.dao.po.UserGoal;

/**
 * 通用目标表挂载点处理
 * 
 * @author zhouyanjun
 * @version 1.0 2014-3-14
 */
public class CommonGoal extends MountGoalPoint {
	private IUserGoalDao userGoalDao;

	@Override
	public void completeUserGoal() {
		/*
		 * 根据完成的条件查询所有完成的目标
		 */
		final List<String> completeGoalPKs = new ArrayList<String>();
		for (Object completeConditionId : completeConditionIds) {
			List<String> goalPKs = CommonGoalCacheDAO.getGoalIdsByConId((Integer) completeConditionId);
			completeGoalPKs.addAll(goalPKs);
		}
		// 如果没有目标挂载在满足的条件上，则直接返回
		if (completeGoalPKs.isEmpty()) return;
		Integer userId = event.getUserId();
		if (userId == null) return;
		List<UserGoal> userGoals = userGoalDao.batchFindUserGoal(userId, completeGoalPKs);
		Map<String, UserGoal> goalMap = new HashMap<String, UserGoal>();
		List<UserGoal> addUserGoals = new ArrayList<UserGoal>(); // 未存在的添加
		List<UserGoal> updateUserGoals = new ArrayList<UserGoal>(); // 已经存在的更新
		if (userGoals != null && !userGoals.isEmpty()) {
			for (UserGoal obj : userGoals) {
				goalMap.put(obj.getKey(), obj);
			}
			for (String goalId : goalMap.keySet()) {// 更新目的为完成状态
				completeGoalPKs.remove(goalId);
				UserGoal userGoal = goalMap.get(goalId);
				userGoal.setStatus(UserGoalStatus.GET_ABLE.value);
				userGoal.setTouchTime(new Date(System.currentTimeMillis()));
				updateUserGoals.add(userGoal);
			}
			if (!completeGoalPKs.isEmpty()) {
				for (String completeGoal : completeGoalPKs) {
					UserGoal userGoal = new UserGoal();
					userGoal.setUserId(event.getUserId());
					userGoal.setKey(completeGoal);
					userGoal.setStatus(UserGoalStatus.GET_ABLE.value);
					userGoal.setTouchTime(new Date(System.currentTimeMillis()));
					addUserGoals.add(userGoal);
				}
			}
		}
		userGoalDao.batchAdd(addUserGoals);
		userGoalDao.batchUpdate(updateUserGoals);
	}

	public enum UserGoalStatus {
		GET_ABLE("1"), GET_UNABLE("2");

		public String value;

		private UserGoalStatus(String value) {
			this.value = value;
		}
	}

	public IUserGoalDao getUserGoalDao() {
		return userGoalDao;
	}

	public void setUserGoalDao(IUserGoalDao userGoalDao) {
		this.userGoalDao = userGoalDao;
	}
}
