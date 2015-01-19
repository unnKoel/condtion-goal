package com.wind.goal.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.wind.goal.dao.po.UserCondition;

public class UserConditionDao extends JdbcDaoSupport implements IUserConditionDao {

	@Override
	public List<UserCondition> batchFindUserCondition(Integer userId, List<Integer> conditionIds)
	{
		if (userId == null || conditionIds == null || conditionIds.isEmpty())
			throw new IllegalArgumentException();
		StringBuffer sql = new StringBuffer("SELECT * FROM usercondition WHERE userId=" + userId + " AND " + "conditionId IN (");
		for (Integer conditionId : conditionIds)
		{
			sql = sql.append(conditionId + ",");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		return getJdbcTemplate().query(sql.toString(), new RowMapper<UserCondition>() {

			@Override
			public UserCondition mapRow(ResultSet rs, int rowNum) throws SQLException {
				UserCondition userCondition = new UserCondition();
				userCondition.setUserId(rs.getInt("userId"));
				userCondition.setConditionId(rs.getInt("conditionId"));
				userCondition.setConditionValue(rs.getString("conditionValue"));
				return userCondition;
			}
		});
	}

	@Override
	public int[] batchAdd(final List<UserCondition> userConditions) {
		if (userConditions == null || userConditions.isEmpty())
			throw new IllegalArgumentException();
		String sql = "INSERT INTO usercondition(userId,conditionId,conditionValue) VALUES(?,?,?)";
		return getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				UserCondition userCondition = userConditions.get(i);
				ps.setInt(1, userCondition.getUserId());
				ps.setInt(2, userCondition.getConditionId());
				ps.setString(3, userCondition.getConditionValue());
			}

			@Override
			public int getBatchSize() {
				return userConditions.size();
			}
		});
	}

	@Override
	public int[] batchUpdate(final List<UserCondition> userConditions) {
		if (userConditions == null || userConditions.isEmpty())
			throw new IllegalArgumentException();
		String sql = "UPDATE usercondition SET conditionValue = ? WHERE userId = ? AND conditionId= ?";
		return getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				UserCondition userCondition = userConditions.get(i);
				ps.setString(1, userCondition.getConditionValue());
				ps.setInt(2, userCondition.getUserId());
				ps.setInt(3, userCondition.getConditionId());
			}

			@Override
			public int getBatchSize() {
				return userConditions.size();
			}
		});
	}
}
