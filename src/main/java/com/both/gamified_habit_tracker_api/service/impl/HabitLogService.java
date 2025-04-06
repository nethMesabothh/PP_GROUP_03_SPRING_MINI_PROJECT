package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.model.entity.HabitLog;
import com.both.gamified_habit_tracker_api.model.request.HabitLogRequest;
import com.both.gamified_habit_tracker_api.repository.AppUserRepository;
import com.both.gamified_habit_tracker_api.repository.HabitLogRepository;
import com.both.gamified_habit_tracker_api.repository.HabitRepository;
import com.both.gamified_habit_tracker_api.service.IHabitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitLogService implements IHabitLogService {

	private final HabitLogRepository habitLogRepository;
	private final AppUserRepository appUserRepository;
	private final HabitRepository habitRepository;


	@Override
	public HabitLog createHabitLogById(HabitLogRequest request) {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();


		Habit habitByHabit = habitRepository.getHabitById(request.getHabitId());

		if (habitByHabit == null) {
			throw new RuntimeException("HabitId not found");
		}

		appUserRepository.increaseXpAndLevelByUserId(userId);


		return habitLogRepository.createHabitLogById(request);
	}

	@Override
	public List<HabitLog> getAllHabitLogById(UUID habitId) {
		return habitLogRepository.getAllHabitLogById(habitId);
	}
}
