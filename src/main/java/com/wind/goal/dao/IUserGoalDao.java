package com.wind.goal.dao;

import java.util.List;

import com.wind.goal.dao.po.UserGoal;

public interface IUserGoalDao {

	/**
	 * 批量查找用户目标
	 * 
	 * @param userId
	 * @param goalKeys
	 * @return
	 */
	public List<UserGoal> batchFindUserGoal(Integer userId, List<String> goalKeys);

	/**
	 * 批量添加用户目标
	 * 
	 * @param userGoals
	 * @return
	 */
	public int[] batchAdd(List<UserGoal> userGoals);

	/**
	 * 批量更新用户目标
	 * 
	 * @param userGoals
	 * @return
	 */
	public int[] batchUpdate(List<UserGoal> userGoals);
}
