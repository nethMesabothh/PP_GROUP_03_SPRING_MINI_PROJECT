package com.both.gamified_habit_tracker_api.repository;

import com.both.gamified_habit_tracker_api.config.UUIDTypeHandler;
import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.request.RegisterRequest;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.OffsetDateTime;
import java.util.UUID;

@Mapper
public interface AppUserRepository {

	@Results(id = "appUserMapper", value = {
					@Result(property = "appUserId", column = "app_user_id"),
					@Result(property = "profileImage", column = "profile_image"),
					@Result(property = "isVerified", column = "is_verified"),
					@Result(property = "createdAt", column = "created_at", javaType = OffsetDateTime.class)
	})
	@Select("""
					SELECT * FROM app_users WHERE email = #{identifier} OR username = #{identifier}
					""")
	AppUser getUserByIdentifier(String identifier);

	@ResultMap("appUserMapper")
	@Select("SELECT * FROM app_users WHERE app_user_id = #{userId}")
	AppUser getUserById(UUID userId);

	@Select("SELECT COUNT(*) FROM app_users WHERE email = #{email}")
	boolean existsByEmail(String email);

	@Select("SELECT COUNT(*) FROM app_users WHERE username = #{username}")
	boolean existsByUsername(String username);

	@ResultMap("appUserMapper")
	@Select("""
					INSERT INTO app_users VALUES (default, #{req.username}, #{req.email}, #{passwordEncrypt}, 1, 0, #{req.profileImage}, false, default) RETURNING  *
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
}
