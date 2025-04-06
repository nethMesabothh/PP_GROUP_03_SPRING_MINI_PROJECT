package com.both.gamified_habit_tracker_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/habit-log")
public class HabitLogController {

	@GetMapping
	public String GetAllHabitLogByHabitId(){
		return "Hello";
	}

}
