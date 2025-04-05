package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.repository.HabitRepository;
import com.both.gamified_habit_tracker_api.service.IHabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
		return habitRepository.getAllHabits();
	}


}
