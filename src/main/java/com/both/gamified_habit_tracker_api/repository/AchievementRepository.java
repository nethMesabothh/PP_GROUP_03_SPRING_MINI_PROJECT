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
            @Result(property = "achievementId", column = "achievement_id", javaType = UUID.class, jdbcType = JdbcType.OTHER, typeHandler = UUIDTypeHandler.class),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "badge", column = "badge"),
            @Result(property = "xpRequired", column = "xp_required")
    })
    @Select("""
    SELECT a.*
    FROM achievements a
    JOIN app_user_achievements aua ON a.achievement_id = aua.achievement_id
    WHERE aua.app_user_id = #{appUserID, typeHandler=com.both.gamified_habit_tracker_api.config.UUIDTypeHandler}
    """)
    List<Achievement> getAchievementByUserId(@Param("appUserID") UUID appUserID);

//'a7bb8f13-f1a4-47f9-b3de-194f0c0aa030'

    @Insert("""
        INSERT INTO achievements (title, description, badge, xp_required)
        VALUES (#{title}, #{description}, #{badge}, #{xpRequired})
    """)
    Achievement saveAchievement(@Param("req") AchievementRequest request);


    @Delete("""
        DELETE FROM achievements WHERE achievement_id = #{achievementId}
    """)
    void deleteAchievementById(UUID achievementId);


    @Update("""
        UPDATE achievements
        SET title = #{req.title},
            description = #{req.description},
            badge = #{req.badge},
            xp_required = #{req.xpRequired}
        WHERE achievement_id = #{achievementId}
    """)
    @ResultMap("achievementMapper")
    Achievement updateAchievementById(UUID achievementId, @Param("req") AchievementRequest request);
}
