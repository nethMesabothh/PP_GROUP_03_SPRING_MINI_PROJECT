package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.model.entity.HabitLog;
import com.both.gamified_habit_tracker_api.model.request.HabitLogRequest;
import com.both.gamified_habit_tracker_api.model.response.APIResponse;
import com.both.gamified_habit_tracker_api.model.response.APIResponseError;
import com.both.gamified_habit_tracker_api.service.impl.HabitLogService;
import com.both.gamified_habit_tracker_api.service.impl.HabitService;
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
	private final HabitService habitService;

	@PostMapping
	public ResponseEntity<APIResponse<HabitLog>> createHabitLogById(@RequestBody HabitLogRequest request) {
		HabitLog habitLog = habitLogService.createHabitLogById(request);

		APIResponse<HabitLog> response = new APIResponse<>(true, "Habit log created successfully!", HttpStatus.CREATED, habitLog, LocalDateTime.now());

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/{habit-id}")
	public ResponseEntity<?> getAllHabitLogById(@PathVariable("habit-id") UUID habitId) {

		Habit habit = habitService.getHabitById(habitId);

		if (habit == null) {
			APIResponseError apiResponseError = new APIResponseError(
					false,
					"Habit doesn't exist!",
					HttpStatus.NOT_FOUND,
					LocalDateTime.now());

			return ResponseEntity.ok().body(apiResponseError);
		}

		List<HabitLog> habitLog = habitLogService.getAllHabitLogById(habitId);

		if (habitLog.isEmpty()) {
			APIResponseError apiResponseError = new APIResponseError(
					false,
					"No habit logs found!",
					HttpStatus.NOT_FOUND,
					LocalDateTime.now());

			return ResponseEntity.ok().body(apiResponseError);
		}

		APIResponse<List<HabitLog>> response = new APIResponse<>(true, "Habit log created successfully!", HttpStatus.CREATED, habitLog, LocalDateTime.now());

		return ResponseEntity.ok().body(response);
	}

}
