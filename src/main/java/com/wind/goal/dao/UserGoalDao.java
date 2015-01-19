package com.wind.goal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.wind.goal.dao.po.UserGoal;

public class UserGoalDao extends JdbcDaoSupport implements IUserGoalDao {

	@Override
	public List<UserGoal> batchFindUserGoal(Integer userId, List<String> goalKeys) {
		if (userId == null || goalKeys == null || goalKeys.isEmpty())
			throw new IllegalArgumentException();
		StringBuffer sql = new StringBuffer("SELECT * FROM usergoal WHERE userId=" + userId + " AND " + "key IN (");
		for (String goalKey : goalKeys)
		{
			sql = sql.append(goalKey + ",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return getJdbcTemplate().query(sql.toString(), new RowMapper<UserGoal>() {

			@Override
			public UserGoal mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserGoal userGoal = new UserGoal();
				userGoal.setUserId(rs.getInt("userId"));
				userGoal.setKey(rs.getString("key"));
				userGoal.setStatus(rs.getString("status"));
				userGoal.setGetTime(rs.getDate("getTime"));
				userGoal.setTouchTime(rs.getDate("touchTime"));
				return userGoal;
			}
		});
	}

	@Override
	public int[] batchAdd(final List<UserGoal> userGoals) {
		if (userGoals == null || userGoals.isEmpty())
			throw new IllegalArgumentException();
		String sql = "INSERT INTO usergoal(userId,key,status,touchTime,getTime) VALUES(?,?,?,?,?)";
		return getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				UserGoal userGoal = userGoals.get(i);
				ps.setInt(1, userGoal.getUserId());
				ps.setString(2, userGoal.getKey());
				ps.setString(3, userGoal.getStatus());
				ps.setDate(4, userGoal.getTouchTime());
				ps.setDate(5, userGoal.getGetTime());
			}

			@Override
			public int getBatchSize() {
				return userGoals.size();
			}
		});
	}

	@Override
	public int[] batchUpdate(final List<UserGoal> userGoals) {
		if (userGoals == null || userGoals.isEmpty())
			throw new IllegalArgumentException();
		String sql = "UPDATE usergoal SET status = ? , touchTime = ? , getTime = ? WHERE userId = ? AND key= ?";
		return getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				UserGoal userGoal = userGoals.get(i);
				ps.setString(1, userGoal.getStatus());
				ps.setDate(2, userGoal.getTouchTime());
				ps.setDate(3, userGoal.getGetTime());
				ps.setInt(4, userGoal.getUserId());
				ps.setString(5, userGoal.getKey());
			}

			@Override
			public int getBatchSize() {
				return userGoals.size();
			}
		});
	}
}
