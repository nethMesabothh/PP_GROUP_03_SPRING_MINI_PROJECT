package com.both.gamified_habit_tracker_api.service;

import com.both.gamified_habit_tracker_api.model.entity.HabitLog;
import com.both.gamified_habit_tracker_api.model.request.HabitLogRequest;

import java.util.List;
import java.util.UUID;

public interface IHabitLogService {

	HabitLog createHabitLogById(HabitLogRequest request);

	List<HabitLog> getAllHabitLogById(UUID habitId, Integer page, Integer size);
}
