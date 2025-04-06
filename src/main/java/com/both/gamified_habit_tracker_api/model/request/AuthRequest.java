package com.both.gamified_habit_tracker_api.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {


	private String identifier;

	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must be at least 6 characters")
	@Pattern(
					regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
					message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
	)
	private String password;

}
