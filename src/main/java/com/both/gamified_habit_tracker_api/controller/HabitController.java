package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.model.request.HabitRequest;
import com.both.gamified_habit_tracker_api.model.response.APIResponse;
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
@RequestMapping("api/v1/habits")
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

    @GetMapping("/{habit_id}")
    public ResponseEntity<APIResponse<Habit>> getHabitById(@PathVariable("habit_id") UUID habitId) {

        Habit habit = habitService.getHabitById(habitId);

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
    public ResponseEntity<APIResponse<Habit>> deleteHabitById(@PathVariable("habit_id") UUID habitId) {

        habitService.deleteHabitById(habitId);

        APIResponse<Habit> apiResponse = new APIResponse<>(
                true,
                "Habit deleted successfully",
                HttpStatus.CREATED,
                null,  // use this for now
                LocalDateTime.now()
        );

        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/{habit_id}")
    public ResponseEntity<APIResponse<Habit>> updateHabitById(@PathVariable("habit_id") UUID habitId, @RequestBody HabitRequest request) {
        Habit habit = habitService.updateHabitById(habitId, request);

        APIResponse<Habit> apiResponse = new APIResponse<>(
                true,
                "Habit updated successfully",
                HttpStatus.OK,
                habit,
                LocalDateTime.now()
        );

        return ResponseEntity.ok().body(apiResponse);
    }

}
