package com.both.gamified_habit_tracker_api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Achievement {
    private UUID achievementId;
    private String title;
    private String description;
    private String badge;
    private int xpRequired;
}