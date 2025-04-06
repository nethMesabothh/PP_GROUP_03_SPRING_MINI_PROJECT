package com.both.gamified_habit_tracker_api.service;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Profile;
import com.both.gamified_habit_tracker_api.model.request.AppUserRequest;
import com.both.gamified_habit_tracker_api.model.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAppUserService extends UserDetailsService {
	void registerUser(RegisterRequest request);

	void verifyUser(String email);

	void deleteUserIsInValid(String email);

	AppUser findUserByEmail(String email);

	Profile getUser();

	Profile updateUser(AppUserRequest request);

	Profile deleteCurrentUser();
}
