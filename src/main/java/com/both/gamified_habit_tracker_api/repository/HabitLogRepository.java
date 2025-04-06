package com.both.gamified_habit_tracker_api.repository;

import com.both.gamified_habit_tracker_api.config.UUIDTypeHandler;
import com.both.gamified_habit_tracker_api.model.entity.HabitLog;
import com.both.gamified_habit_tracker_api.model.request.HabitLogRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitLogRepository {

	@Results(id = "habitLogMapper", value = {
					@Result(property = "habitLogId", column = "habit_log_id"),
//					@Result(property = "habitId", column = "habit_id"),
					@Result(property = "logDate", column = "log_date"),
					@Result(property = "xpEarned", column = "xp_earned"),
					@Result(property = "habit", column = "habit_id", one = @One(
									select = "com.both.gamified_habit_tracker_api.repository.HabitRepository.getHabitById"
					))
	})
	@Select("""
					INSERT INTO habit_logs VALUES (default, default, #{req.status}, 10, #{req.habitId}) RETURNING *
					""")
	HabitLog createHabitLogById(@Param("req") HabitLogRequest request);

	@ResultMap("habitLogMapper")
	@Select("""
					SELECT * FROM habit_logs WHERE habit_id = #{habitId}
					""")
	List<HabitLog> getAllHabitLogById(UUID habitId);
}
