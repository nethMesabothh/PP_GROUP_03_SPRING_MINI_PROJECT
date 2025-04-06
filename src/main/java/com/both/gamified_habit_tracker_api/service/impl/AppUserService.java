package com.both.gamified_habit_tracker_api.service.impl;

import com.both.gamified_habit_tracker_api.model.entity.AppUser;
import com.both.gamified_habit_tracker_api.model.request.RegisterRequest;
import com.both.gamified_habit_tracker_api.repository.AppUserRepository;
import com.both.gamified_habit_tracker_api.service.IAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService {

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
		AppUser appUser = appUserRepository.getUserByIdentifier(identifier);
		if (appUser == null) {
			throw new UsernameNotFoundException("User not found with identifier: " + identifier);
		}

		if(!appUser.isVerified()){
			throw new UsernameNotFoundException("User is not verify yet " + identifier);
		}

		return appUser;
	}


	public void registerUser(RegisterRequest request) {
		if (appUserRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		if (appUserRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username already exists");
		}

		String passwordEncrypt = passwordEncoder.encode(request.getPassword());

		appUserRepository.save(request, passwordEncrypt);
	}

	@Override
	public void verifyUser(String email) {
		AppUser appUser = appUserRepository.findByEmail(email);

		if(appUser == null){
			throw new RuntimeException("User not found");
		}

		appUserRepository.updateVerify(email);
	}

	@Override
	public void deleteUserIsInValid(String email) {
		appUserRepository.deleteUser(email);
	}

	@Override
	public AppUser findUserByEmail(String email) {
		AppUser appUser = appUserRepository.findByEmail(email);
		if(appUser == null || appUser.isVerified()){
			throw new IllegalArgumentException("User not found or already verified.");
		}
		return appUser;
	}


}
