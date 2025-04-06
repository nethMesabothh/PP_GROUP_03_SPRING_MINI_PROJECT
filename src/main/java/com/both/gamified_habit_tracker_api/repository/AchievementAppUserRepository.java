package com.both.gamified_habit_tracker_api.repository;

import com.both.gamified_habit_tracker_api.model.entity.Achievement;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface AchievementAppUserRepository {

	@Results(id = "achievementMapper", value = {
					@Result(property = "achievementId", column = "achievement_id"),
					@Result(property = "title", column = "title"),
					@Result(property = "description", column = "description"),
					@Result(property = "badge", column = "badge"),
					@Result(property = "xpRequired", column = "xp_required")
	})
	@Select("""
					SELECT a.achievement_id, a.title, a.description, a.badge, a.xp_required
										FROM achievements a
					         INNER JOIN app_user_achievements aua ON a.achievement_id = aua.achievement_id WHERE app_user_id = #{appUserId}
					""")
	List<Achievement> getAllAchievementByUserId(UUID appUserId);


	@Results(id = "achievementMapperId", value = {
					@Result(property = "achievementId", column = "achievement_id"),
					@Result(property = "title", column = "title"),
					@Result(property = "description", column = "description"),
					@Result(property = "badge", column = "badge"),
					@Result(property = "xpRequired", column = "xp_required")
	})
	@Select("""
					SELECT achievement_id FROM app_user_achievements WHERE achievement_id = #{achievementId} AND app_user_id = #{userId}
					""")
	UUID findAchievementById(UUID achievementId, UUID userId);

	@Insert("""
					INSERT INTO app_user_achievements VALUES (default, #{achievementId}, #{appUserId})
					""")
	void addingAchievement(UUID achievementId, UUID appUserId);

}
