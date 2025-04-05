package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.entity.Achievement;
import com.both.gamified_habit_tracker_api.model.request.AchievementRequest;
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

	@Override
	public List<Achievement> getAllAchievements() {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
		UUID userId = appUser.getAppUserId();
		return achievementRepository.getAllAchievements();
	}


	@Override
	public List<Achievement> getAchievementByUserId() {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UUID appUserId = appUser.getAppUserId();
		return achievementRepository.getAchievementByUserId(appUserId);
	}


	@Override
	public Achievement saveAchievement(AchievementRequest request) {
		AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		UUID userId = appUser.getAppUserId();
		return achievementRepository.saveAchievement(request);
	}

	@Override
	public void deleteAchievementById(UUID AchievementId) {
		achievementRepository.deleteAchievementById(AchievementId);
	}

	@Override
	public Achievement updateAchievementById(UUID AchievementId, AchievementRequest request) {
		return achievementRepository.updateAchievementById(AchievementId, request);
	}


}