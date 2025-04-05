package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.model.response.APIResponse;
import com.both.gamified_habit_tracker_api.service.impl.HabitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/habit")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class HabitController {


	private final HabitService habitService;

	@GetMapping
	public ResponseEntity<APIResponse<List<Habit>>> getAllHabits() {

		List<Habit> habits = habitService.getAllHabits();
		APIResponse<List<Habit>> apiResponse = new APIResponse<>(
						true,
						"All habits have been fetched successfully!",
						HttpStatus.OK,
						habits,
						LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiResponse);
	}
}
