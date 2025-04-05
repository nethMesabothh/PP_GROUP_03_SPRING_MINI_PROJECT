package com.both.gamified_habit_tracker_api.config;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.UUID;

@Component
public class UUIDTypeHandler extends BaseTypeHandler<UUID> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
		ps.setObject(i, parameter, Types.OTHER);
	}

	@Override
	public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getObject(columnName, UUID.class);
	}

	@Override
	public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getObject(columnIndex, UUID.class);
	}

	@Override
	public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getObject(columnIndex, UUID.class);
	}
}
