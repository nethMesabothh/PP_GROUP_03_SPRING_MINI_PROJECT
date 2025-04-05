package com.both.gamified_habit_tracker_api.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	private String username;
	private String email;
	private String password;
	private String profileImage;
}
