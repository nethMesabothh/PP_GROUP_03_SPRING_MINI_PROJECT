package com.both.gamified_habit_tracker_api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/habit")
@SecurityRequirement(name = "bearerAuth")
public class HabitController {

    @GetMapping
    public String getAllHabits() {
        return "hi";
    }
}
