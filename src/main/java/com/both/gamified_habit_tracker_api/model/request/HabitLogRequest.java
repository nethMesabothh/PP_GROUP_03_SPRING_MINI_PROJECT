package com.both.gamified_habit_tracker_api.model.request;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitLogRequest {
	private String status;
	private UUID habitId;
}
