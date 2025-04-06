package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.model.entity.Achievement;
import com.both.gamified_habit_tracker_api.model.response.APIResponse;
import com.both.gamified_habit_tracker_api.service.impl.AchievementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/achievement")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AchievementController {


	private final AchievementService achievementService;

	@GetMapping
	public ResponseEntity<APIResponse<List<Achievement>>> getAllAchievements() {
		List<Achievement> achievements = achievementService.getAllAchievements();
		APIResponse<List<Achievement>> apiResponse = new APIResponse<>(
						true,
						"All achievements have been fetched successfully!",
						HttpStatus.OK,
						achievements,
						LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiResponse);
	}

	@GetMapping("/app-user-id")
	public ResponseEntity<APIResponse<List<Achievement>>> getAchievementByUserId() {
		List<Achievement> achievements = achievementService.getAchievementByUserId();
		APIResponse<List<Achievement>> apiResponse = new APIResponse<>(
						true,
						"All achievements have been fetched successfully!",
						HttpStatus.OK,
						achievements,
						LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiResponse);
	}
}
