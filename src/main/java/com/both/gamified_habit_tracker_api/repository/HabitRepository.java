package com.both.gamified_habit_tracker_api.repository;

import com.both.gamified_habit_tracker_api.config.UUIDTypeHandler;
import com.both.gamified_habit_tracker_api.model.entity.Habit;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {

    @Results(id="habitMapper", value = {
            @Result(property = "habitId", column = "habit_id", javaType = UUID.class, jdbcType = JdbcType.OTHER, typeHandler = UUIDTypeHandler.class),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "createdAt", column = "created_at", javaType = OffsetDateTime.class),
//            @Result(property = "appUserResponse", column = "app_user_id", one = @One(
//                    select = "com.both.gamified_habit_tracker_api.repository.AppUserRepository.getUserById"
//            ))
    })
    @Select("""
        SELECT * FROM habits
    """)
    List<Habit> getAllHabits();
}
