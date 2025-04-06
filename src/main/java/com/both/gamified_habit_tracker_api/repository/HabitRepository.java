package com.both.gamified_habit_tracker_api.repository;

import com.both.gamified_habit_tracker_api.config.UUIDTypeHandler;
import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.model.request.HabitRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.ShiftLeft;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Mapper
public interface HabitRepository {

    @Results(id="habitMapper", value = {
            @Result(property = "habitId", column = "habit_id", javaType = UUID.class, jdbcType = JdbcType.OTHER, typeHandler = UUIDTypeHandler.class),
            @Result(property = "isActive", column = "is_active"),
            @Result(property = "createdAt", column = "created_at", javaType = OffsetDateTime.class),
            @Result(property = "appUserResponse", column = "app_user_id", one = @One(
                    select = "com.both.gamified_habit_tracker_api.repository.AppUserRepository.getUserById"
            ))
    })
    @Select("""
        SELECT * FROM habits
    """)
    List<Habit> getAllHabits();


    @ResultMap("habitMapper")
    @Select("""
        SELECT * FROM habits WHERE habit_id = #{habitId}
    """)
    Habit getHabitById(UUID habitId);


    @ResultMap("habitMapper")
    @Select("""
        INSERT INTO habits (habit_id, title, description, frequency, is_active, created_at, app_user_id)
        VALUES (uuid_generate_v4(), #{req.title}, #{req.description}, #{req.frequency}, true, CURRENT_TIMESTAMP, #{userId})
        RETURNING *
    """)
    Habit saveHabit(@Param("req") HabitRequest request, UUID userId);

    @Delete("""
        DELETE FROM habits WHERE habit_id = #{habitId}
    """)
    void deleteHabitById(UUID habitId);

    @ResultMap("habitMapper")
    @Select("""
        UPDATE habits SET title = #{req.title}, description = #{req.description}, frequency = #{req.frequency} WHERE habit_id = #{habitId} RETURNING *
    """)
    Habit updateHabitById(UUID habitId,@Param("req") HabitRequest request);
}
