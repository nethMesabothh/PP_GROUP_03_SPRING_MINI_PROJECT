package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.model.request.HabitRequest;
import com.both.gamified_habit_tracker_api.repository.HabitRepository;
import com.both.gamified_habit_tracker_api.service.IHabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HabitService implements IHabitService {

	private final HabitRepository habitRepository;


	@Override
	public List<Habit> getAllHabits() {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();

		return habitRepository.getAllHabits(userId);
	}

	@Override
	public Habit getHabitById(UUID habitId) {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();
		return habitRepository.getHabitById(habitId, userId);
	}


	@Override
	public Habit saveHabit(HabitRequest request) {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();
		return habitRepository.saveHabit(request, userId);
	}

	@Override
	public void deleteHabitById(UUID habitId) {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();
		habitRepository.deleteHabitById(habitId, userId);
	}

	@Override
	public Habit updateHabitById(UUID habitId, HabitRequest request) {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();
		return habitRepository.updateHabitById(habitId, request, userId);
	}


}
