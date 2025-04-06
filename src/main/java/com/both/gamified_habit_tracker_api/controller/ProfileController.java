package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.HabitLog;
import com.both.gamified_habit_tracker_api.model.entity.Profile;
import com.both.gamified_habit_tracker_api.model.request.AppUserRequest;
import com.both.gamified_habit_tracker_api.model.response.APIDeleteResponse;
import com.both.gamified_habit_tracker_api.model.response.APIResponse;
import com.both.gamified_habit_tracker_api.service.impl.AppUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/profile")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProfileController {

	private final AppUserService appUserService;

	@GetMapping
	public ResponseEntity<APIResponse<Profile>> getUser() {
		Profile appUser = appUserService.getUser();

		APIResponse<Profile> response = new APIResponse<>(true, "User profile fetched successfully!", HttpStatus.CREATED, appUser, LocalDateTime.now());

		return ResponseEntity.ok().body(response);
	}

	@PutMapping
	public ResponseEntity<APIResponse<Profile>> updateUser(@RequestBody AppUserRequest request) {
		Profile appUser = appUserService.updateUser(request);

		APIResponse<Profile> response = new APIResponse<>(true, "User profile updated successfully!", HttpStatus.CREATED, appUser, LocalDateTime.now());

		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUser() {
		appUserService.deleteCurrentUser();

		APIDeleteResponse<Profile> response = new APIDeleteResponse<>(true, "User deleted successfully!", HttpStatus.OK , LocalDateTime.now());

		return ResponseEntity.ok().body(response);
	}

}
