package com.both.gamified_habit_tracker_api.service;


import com.both.gamified_habit_tracker_api.model.entity.Achievement;
import com.both.gamified_habit_tracker_api.model.request.AchievementRequest;

import java.util.List;
import java.util.UUID;

public interface IAchievementService {

	List<Achievement> getAllAchievements(Integer page, Integer size);



	List<Achievement> getAchievementByUserId();
}
