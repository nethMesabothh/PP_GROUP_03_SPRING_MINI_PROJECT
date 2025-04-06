package com.both.gamified_habit_tracker_api.repository;

import com.both.gamified_habit_tracker_api.config.UUIDTypeHandler;
import com.both.gamified_habit_tracker_api.model.entity.Achievement;
import com.both.gamified_habit_tracker_api.model.request.AchievementRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AchievementRepository {

	@Results(id = "achievementMapper", value = {
					@Result(property = "achievementId", column = "achievement_id", javaType = UUID.class, jdbcType = JdbcType.OTHER, typeHandler = UUIDTypeHandler.class),
					@Result(property = "title", column = "title"),
					@Result(property = "description", column = "description"),
					@Result(property = "badge", column = "badge"),
					@Result(property = "xpRequired", column = "xp_required")
	})
	@Select("""
					SELECT * FROM achievements
					""")
	List<Achievement> getAllAchievements();


	@Results(id = "achievementByUserIdMapper", value = {
					@Result(property = "achievementId", column = "achievement_id"),
					@Result(property = "xpRequired", column = "xp_required")
	})
	@Select("""
					SELECT * FROM achievements
					""")
	List<Achievement> getAchievementById(UUID achievementId);


}
