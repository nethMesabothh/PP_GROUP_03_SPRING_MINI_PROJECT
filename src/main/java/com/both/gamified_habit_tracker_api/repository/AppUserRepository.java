package com.both.gamified_habit_tracker_api.repository;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Profile;
import com.both.gamified_habit_tracker_api.model.request.AppUserRequest;
import com.both.gamified_habit_tracker_api.model.request.HabitLogRequest;
import com.both.gamified_habit_tracker_api.model.request.RegisterRequest;
import org.apache.ibatis.annotations.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Mapper
public interface AppUserRepository {

	@Results(id = "appUserMapper", value = {
					@Result(property = "appUserId", column = "app_user_id"),
					@Result(property = "username", column = "username"),
					@Result(property = "email", column = "email"),
					@Result(property = "profileImage", column = "profile_image"),
					@Result(property = "isVerified", column = "is_verified"),
					@Result(property = "createdAt", column = "created_at", javaType = OffsetDateTime.class)
	})
	@Select("""
					SELECT * FROM app_users WHERE email = #{identifier} OR username = #{identifier}
					""")
	AppUser getUserByIdentifier(String identifier);

	@Results(id = "profileMapper", value = {
					@Result(property = "appUserId", column = "app_user_id"),
					@Result(property = "username", column = "username"),
					@Result(property = "email", column = "email"),
					@Result(property = "level", column = "level"),
					@Result(property = "xp", column = "xp"),
					@Result(property = "profileImage", column = "profile_image"),
					@Result(property = "isVerified", column = "is_verified"),
					@Result(property = "createdAt", column = "created_at", javaType = OffsetDateTime.class)
	})
	@Select("SELECT * FROM app_users WHERE app_user_id = #{userId}")
	Profile getUserById(UUID userId);

	@Select("SELECT COUNT(*) FROM app_users WHERE email = #{email}")
	boolean existsByEmail(String email);

	@Select("SELECT COUNT(*) FROM app_users WHERE username = #{username}")
	boolean existsByUsername(String username);

	@ResultMap("appUserMapper")
	@Select("""
					INSERT INTO app_users VALUES (default, #{req.username}, #{req.email}, #{passwordEncrypt}, 0, 0, #{req.profileImage}, false, default) RETURNING  *
					""")
	AppUser save(@Param("req") RegisterRequest request, String passwordEncrypt);

	@ResultMap("appUserMapper")
	@Select("""
					SELECT * FROM app_users WHERE email = #{email}
					""")
	AppUser findByEmail(String email);

	@ResultMap("appUserMapper")
	@Select("""
					UPDATE app_users SET is_verified = true WHERE email = #{email}
					""")
	void updateVerify(String email);


	@ResultMap("appUserMapper")
	@Select("""
					DELETE FROM app_users WHERE email = #{email}
					""")
	void deleteUser(String email);

	@ResultMap("appUserMapper")
	@Select("""
					UPDATE app_users SET xp = xp + 10, level = FLOOR((xp + 10) / 100) WHERE app_user_id = #{userId} RETURNING *
					""")
	AppUser increaseXpAndLevelByUserId(UUID userId);


	@ResultMap("profileMapper")
	@Select("""
					UPDATE app_users SET username = #{req.username}, profile_image = #{req.profileImage} WHERE app_user_id = #{userId} RETURNING *
					""")
	Profile updateUser(@Param("req") AppUserRequest request, UUID userId);

	@Delete("DELETE FROM habits WHERE app_user_id = #{userId}")
	void deleteHabitsByUserId(UUID userId);

	@Select("""
					DELETE FROM app_users WHERE app_user_id = #{userId} RETURNING *
					""")
	Profile deleteCurrentUser(UUID userId);
}
