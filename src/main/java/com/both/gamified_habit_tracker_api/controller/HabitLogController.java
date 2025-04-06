package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.model.entity.HabitLog;
import com.both.gamified_habit_tracker_api.model.request.HabitLogRequest;
import com.both.gamified_habit_tracker_api.model.response.APIResponse;
import com.both.gamified_habit_tracker_api.service.impl.HabitLogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/habit-log")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class HabitLogController {

	private final HabitLogService habitLogService;

	@PostMapping
	public ResponseEntity<APIResponse<HabitLog>> createHabitLogById(@RequestBody HabitLogRequest request) {
		HabitLog habitLog = habitLogService.createHabitLogById(request);

		APIResponse<HabitLog> response = new APIResponse<>(true, "Habit log created successfully!", HttpStatus.CREATED, habitLog, LocalDateTime.now());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{habit-id}")
	public ResponseEntity<APIResponse<List<HabitLog>>> getAllHabitLogById(@PathVariable("habit-id") UUID habitId) {
		List<HabitLog> habitLog = habitLogService.getAllHabitLogById(habitId);

		APIResponse<List<HabitLog>> response = new APIResponse<>(true, "Habit log created successfully!", HttpStatus.CREATED, habitLog, LocalDateTime.now());

		return ResponseEntity.ok().body(response);
	}

}
