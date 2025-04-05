package com.both.gamified_habit_tracker_api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchievementRequest {
    private String title;
    private String description;
    private String badge;
    private int xpRequired;
}