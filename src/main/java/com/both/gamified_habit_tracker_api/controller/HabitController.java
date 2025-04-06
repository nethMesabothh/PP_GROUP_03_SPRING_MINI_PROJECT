package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.model.entity.Profile;
import com.both.gamified_habit_tracker_api.model.request.HabitRequest;
import com.both.gamified_habit_tracker_api.model.response.APIDeleteResponse;
import com.both.gamified_habit_tracker_api.model.response.APIResponse;
import com.both.gamified_habit_tracker_api.model.response.APIResponseError;
import com.both.gamified_habit_tracker_api.service.impl.HabitService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/habits")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class HabitController {


	private final HabitService habitService;

	@GetMapping
	public ResponseEntity<?> getAllHabits(@RequestParam(defaultValue = "1") @Positive Integer page ,
										  @RequestParam(defaultValue = "10") @Positive Integer size) {


		List<Habit> habits = habitService.getAllHabits(page, size);

		if (habits.isEmpty()) {
			APIResponseError apiResponseError = new APIResponseError(false,
							"No habits found!",
							HttpStatus.NOT_FOUND,
							LocalDateTime.now());

			return ResponseEntity.ok().body(apiResponseError);
		}

		APIResponse<List<Habit>> apiResponse = new APIResponse<>(
						true,
						"All habits have been fetched successfully!",
						HttpStatus.OK,
						habits,
						LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiResponse);
	}

	@GetMapping("/{habit_id}")
	public ResponseEntity<?> getHabitById(@PathVariable("habit_id") UUID habitId) {

		Habit habit = habitService.getHabitById(habitId);

		if (habit == null) {
			APIResponseError apiResponseError = new APIResponseError(false,
					"Habit not found!",
					HttpStatus.NOT_FOUND,
					LocalDateTime.now());

			return ResponseEntity.ok().body(apiResponseError);
		}

		APIResponse<Habit> apiResponse = new APIResponse<>(
						true,
						"Habit fetched successfully!",
						HttpStatus.OK,
						habit,
						LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiResponse);
	}


	@PostMapping
	public ResponseEntity<APIResponse<Habit>> saveHabit(@RequestBody HabitRequest request) {
		Habit habit = habitService.saveHabit(request);

		APIResponse<Habit> apiResponse = new APIResponse<>(
						true,
						"Habit created successfully",
						HttpStatus.CREATED,
						habit,
						LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiResponse);
	}

	@DeleteMapping("/{habit_id}")
	public ResponseEntity<?> deleteHabitById(@PathVariable("habit_id") UUID habitId) {

		Habit habit = habitService.getHabitById(habitId);

		if (habit == null) {
			APIResponseError apiResponseError = new APIResponseError(false,
					"Habit not found!",
					HttpStatus.NOT_FOUND,
					LocalDateTime.now());

			return ResponseEntity.ok().body(apiResponseError);
		}

		habitService.deleteHabitById(habitId);


		APIDeleteResponse<Habit> apiDeleteResponse = new APIDeleteResponse<>(
				true,
				"Habit deleted successfully",
				HttpStatus.OK,
				LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiDeleteResponse);
	}

	@PutMapping("/{habit_id}")
	public ResponseEntity<?> updateHabitById(@PathVariable("habit_id") UUID habitId, @RequestBody HabitRequest request) {
		Habit habit = habitService.getHabitById(habitId);

		if (habit == null) {
			APIResponseError apiResponseError = new APIResponseError(false,
					"Habit not found!",
					HttpStatus.NOT_FOUND,
					LocalDateTime.now());

			return ResponseEntity.ok().body(apiResponseError);
		}

			Habit updatedHabit =  habitService.updateHabitById(habitId, request);
		APIResponse<Habit> apiResponse = new APIResponse<>(
						true,
						"Habit updated successfully",
						HttpStatus.OK,
						updatedHabit,
						LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiResponse);
	}

}
