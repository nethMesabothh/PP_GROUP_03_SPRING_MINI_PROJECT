package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.Achievement;
import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Habit;
import com.both.gamified_habit_tracker_api.model.entity.HabitLog;
import com.both.gamified_habit_tracker_api.model.request.HabitLogRequest;
import com.both.gamified_habit_tracker_api.repository.*;
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
	private final AchievementRepository achievementRepository;
	private final AchievementAppUserRepository achievementAppUserRepository;


	@Override
	public HabitLog createHabitLogById(HabitLogRequest request) {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();
		Habit habitByHabit = habitRepository.getHabitById(request.getHabitId(), userId);

		System.out.println(habitByHabit);

		if (habitByHabit == null) {
			throw new RuntimeException("HabitId not found");
		}

		AppUser appUserByXp = appUserRepository.increaseXpAndLevelByUserId(userId);


		System.out.println(appUserByXp);

		List<Achievement> achievements = achievementRepository.getAllAchievements(1,100);

		for (Achievement achievement : achievements) {
			UUID achievementId = achievementAppUserRepository.findAchievementById(achievement.getAchievementId(), userId);
			if (appUserByXp.getXp() >= achievement.getXpRequired() && achievementId == null) {
				achievementAppUserRepository.addingAchievement(achievement.getAchievementId(), userId);
			}
		}


		return habitLogRepository.createHabitLogById(request, userId);
	}

	@Override
	public List<HabitLog> getAllHabitLogById(UUID habitId, Integer page, Integer size) {

		int offset = (page - 1) * size;

		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();
		return habitLogRepository.getAllHabitLogById(habitId, userId, offset, size);
	}
}
