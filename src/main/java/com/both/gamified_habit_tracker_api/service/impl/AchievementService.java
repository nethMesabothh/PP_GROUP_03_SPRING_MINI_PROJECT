package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Achievement;
import com.both.gamified_habit_tracker_api.model.request.AchievementRequest;
import com.both.gamified_habit_tracker_api.repository.AchievementAppUserRepository;
import com.both.gamified_habit_tracker_api.repository.AchievementRepository;
import com.both.gamified_habit_tracker_api.service.IAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementService implements IAchievementService {

	private final AchievementRepository achievementRepository;
	private final AchievementAppUserRepository achievementAppUserRepository;


	@Override
	public List<Achievement> getAllAchievements(Integer page, Integer size) {
		int offset = (page - 1) * size;
		return achievementRepository.getAllAchievements(offset, size);
	}

	@Override
	public List<Achievement> getAchievementByUserId() {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID appUserId = appUser.getAppUserId();
		List<Achievement> achievement = achievementAppUserRepository.getAllAchievementByUserId(appUserId);
		System.out.println(achievement);
		return achievement;
	}


}
