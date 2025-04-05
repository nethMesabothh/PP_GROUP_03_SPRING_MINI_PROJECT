package com.both.gamified_habit_tracker_api.service;


import com.both.gamified_habit_tracker_api.model.entity.Habit;

import java.util.List;

public interface IHabitService {

    List<Habit> getAllHabits();
}
