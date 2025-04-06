package com.both.gamified_habit_tracker_api.controller;

import com.both.gamified_habit_tracker_api.jwt.JwtService;
import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.request.AuthRequest;
import com.both.gamified_habit_tracker_api.model.request.RegisterRequest;
import com.both.gamified_habit_tracker_api.model.response.APIResponseAuth;
import com.both.gamified_habit_tracker_api.service.impl.AppUserService;
import com.both.gamified_habit_tracker_api.service.impl.EmailService;
import com.both.gamified_habit_tracker_api.service.impl.OtpService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {

	private final AppUserService appUserService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final OtpService otpService;
	private final EmailService emailService;

	private void authenticate(String email, String password) throws Exception {
		try {
			System.out.println("Attempting to authenticate user: " + email);
			Authentication authentication = authenticationManager.authenticate(
							new UsernamePasswordAuthenticationToken(email, password)
			);
			System.out.println(authentication);
			System.out.println("User authenticated successfully: " + email);
		} catch (DisabledException e) {
			System.out.println("User is disabled: " + email);
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid credentials for user: " + email);
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (Exception e) {
			System.out.println("Unexpected error during authentication: " + e.getMessage());
			throw e;
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) throws Exception {
		authenticate(request.getIdentifier(), request.getPassword());
		final UserDetails userDetails = appUserService.loadUserByUsername(request.getIdentifier());
		System.out.println("UserDetails loaded: " + userDetails);
		final String token = jwtService.generateToken(userDetails);
		System.out.println("Generated JWT token: " + token);
		Map<String, String> payload = Map.of("token", token);

		APIResponseAuth<Map<String, String>> apiResponse = new APIResponseAuth<>(
						true,
						"success",
						HttpStatus.OK,
						payload,
						LocalDateTime.now()
		);

		return ResponseEntity.ok().body(apiResponse);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		try {
			// Call the service to register the user
			appUserService.registerUser(request);

			String otp = otpService.generateOtp(request.getEmail());
			emailService.sendOtpEmail(request.getEmail(), otp);

			// Return a success response
			return ResponseEntity.ok(new APIResponseAuth<>(
							true,
							"User registered successfully",
							HttpStatus.CREATED,
							null,
							LocalDateTime.now()
			));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
		}
	}

	@PostMapping("/verify")
	public ResponseEntity<?> verifyOpt(@RequestParam String email, @RequestParam String otp) {
		boolean isValid = otpService.validateOtp(email, otp);

		if (!isValid) {
			int failedAttempts = otpService.getFailedAttempts(email);
			System.out.println(failedAttempts);
			if (failedAttempts >= 2) { // Allow up to 2 failed attempts
				appUserService.deleteUserIsInValid(email);
				otpService.removeFailedAttempts(email); // Clean up failed attempts
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account deleted due to too many failed OTP attempts.");
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP. Attempts left: " + (2 - failedAttempts));
		}


		appUserService.verifyUser(email);

		return ResponseEntity.ok(new APIResponseAuth<>(
						true,
						"Email verified successfully.",
						HttpStatus.OK,
						null,
						LocalDateTime.now()
		));
	}

	@PostMapping("/resend-otp")
	public ResponseEntity<?> resendOtp(@RequestParam String email) {
		try {
			AppUser user = appUserService.findUserByEmail(email);

			String otp = otpService.generateOtp(user.getEmail());
			emailService.sendOtpEmail(user.getEmail(), otp);

			return ResponseEntity.ok(new APIResponseAuth<>(
							true,
							"New OTP sent successfully. Please check your email.",
							HttpStatus.OK,
							null,
							LocalDateTime.now()
			));

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
