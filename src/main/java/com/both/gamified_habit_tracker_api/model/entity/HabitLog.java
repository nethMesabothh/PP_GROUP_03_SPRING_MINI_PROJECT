package com.both.gamified_habit_tracker_api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitLog {
	private UUID habitLogId;
	private String logDate;
	private String status;
	private Integer xpEarned;
	private Habit habit;
}
