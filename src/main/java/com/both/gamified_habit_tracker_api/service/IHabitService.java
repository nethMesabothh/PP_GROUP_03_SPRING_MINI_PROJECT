package com.both.gamified_habit_tracker_api.service;


import com.both.gamified_habit_tracker_api.model.entity.Habit;

import java.util.List;
import java.util.UUID;

public interface IHabitService {

    List<Habit> getAllHabits();

    Habit getHabitById(UUID habitId);
}
