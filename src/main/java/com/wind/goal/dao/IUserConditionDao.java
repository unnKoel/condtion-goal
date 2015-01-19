package com.wind.goal.dao;

import java.util.List;
import com.wind.goal.dao.po.UserCondition;

public interface IUserConditionDao {

	/**
	 * 批量查询用户相关条件
	 * 
	 * @param userId
	 *            用户Id
	 * @param conditionIds
	 *            条件Id组
	 * @return
	 */
	public List<UserCondition> batchFindUserCondition(Integer userId, List<Integer> conditionIds);

	/**
	 * 批量添加用户相关条件
	 * 
	 * @param userConditions
	 *            用户条件列表
	 * @return
	 */
	public int[] batchAdd(List<UserCondition> userConditions);

	/**
	 * 批量更新用户相关条件
	 * 
	 * @param userConditions
	 *            用户条件列表
	 * @return
	 */
	public int[] batchUpdate(List<UserCondition> userConditions);
}
