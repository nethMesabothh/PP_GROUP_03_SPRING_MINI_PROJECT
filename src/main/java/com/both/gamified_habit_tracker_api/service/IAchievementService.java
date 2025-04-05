package com.both.gamified_habit_tracker_api.service;


import com.both.gamified_habit_tracker_api.model.entity.Achievement;
import com.both.gamified_habit_tracker_api.model.request.AchievementRequest;

import java.util.List;
import java.util.UUID;

public interface IAchievementService {

    List<Achievement> getAllAchievements();

    List<Achievement> getAchievementByUserId();

    Achievement saveAchievement(AchievementRequest request);

    void deleteAchievementById(UUID achievementId);

    Achievement updateAchievementById(UUID achievementId, AchievementRequest request);
}