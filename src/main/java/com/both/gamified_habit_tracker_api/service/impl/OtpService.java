package com.both.gamified_habit_tracker_api.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

	private final Map<String, OtpDetails> otpStore = new ConcurrentHashMap<>();
	private final Map<String, Integer> failedAttempts = new ConcurrentHashMap<>();

	public String generateOtp(String email) {
		String otp = String.valueOf((int) (Math.random() * 900000 + 100000)); // 6-digit OTP
		otpStore.put(email, new OtpDetails(otp, System.currentTimeMillis()));
		failedAttempts.put(email, 0); // Reset failed attempts when generating a new OTP
		return otp;
	}

	public boolean validateOtp(String email, String otp) {
		OtpDetails details = otpStore.get(email);
		if (details == null) {
			return false; // No OTP found for this email
		}

		long currentTime = System.currentTimeMillis();
		if (currentTime - details.timestamp > 2 * 60 * 1000) { // 2 minutes
			otpStore.remove(email); // Remove expired OTP
			return false;
		}

		boolean isValid = details.otp.equals(otp);
		if (isValid) {
			otpStore.remove(email); // Remove OTP after successful validation
			failedAttempts.remove(email); // Reset failed attempts
		} else {
			int attempts = failedAttempts.getOrDefault(email, 0);
			failedAttempts.put(email, attempts + 1); // Increment failed attempts
		}
		return isValid;
	}

	public int getFailedAttempts(String email) {
		return failedAttempts.getOrDefault(email, 0); // Get the number of failed attempts
	}

	public void removeFailedAttempts(String email) {
		System.out.println("Clean up email");
		failedAttempts.remove(email); // Remove the failed attempts entry
	}

	private static class OtpDetails {
		String otp;
		long timestamp;

		OtpDetails(String otp, long timestamp) {
			this.otp = otp;
			this.timestamp = timestamp;
		}
	}
}
