package com.both.gamified_habit_tracker_api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

	private UUID appUserId;
	private String username;
	private String email;
	private int level;
	private int xp;
	private String profileImage;
	private boolean isVerified;
	private OffsetDateTime createdAt;

}
