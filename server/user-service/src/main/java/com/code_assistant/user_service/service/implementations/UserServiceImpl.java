package com.code_assistant.user_service.service.implementations;

import com.code_assistant.user_service.config.Authentication.CustomUserDetails;
import com.code_assistant.user_service.dto.AuthenticationResponse;
import com.code_assistant.user_service.dto.UserDto;
import com.code_assistant.user_service.exception.wrapper.ResourceAlreadyExistException;
import com.code_assistant.user_service.exception.wrapper.ResourceNotFoundException;
import com.code_assistant.user_service.helper.mappers.UserMapper;
import com.code_assistant.user_service.helper.util.JwtUtil;
import com.code_assistant.user_service.model.Users;
import com.code_assistant.user_service.repository.UserRepository;
import com.code_assistant.user_service.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
		@Autowired
		private UserRepository userRepository;
		@Autowired
		private PasswordEncoder passwordEncoder;
		@Autowired
		private JwtUtil jwtUtil;
		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		public List<UserDto> findAll(){
			System.out.println("findAll");
			return  this.userRepository.findAll()
					.stream()
					.map(UserMapper::map)
					.collect(Collectors.toList());
		}

		@Override
		public UserDto findById(Long id){
			return this.userRepository.findById(id)
					.map(UserMapper::map)
					.orElseThrow(() -> new ResourceNotFoundException (String.format("No user found with the provided ID: %s", id)));
		}


		@Override
		public AuthenticationResponse login(UserDto userDto) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
			);

			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			String jwtToken = jwtUtil.generateToken(userDetails);
			String refreshToken = jwtUtil.generateRefreshToken(userDetails);

			return AuthenticationResponse.builder()
					.accessToken(jwtToken)
					.refreshToken(refreshToken)
					.build();
		}


		@Override
		public UserDto update(UserDto userDto){
			Users customer = this.userRepository.findById(userDto.getId())
					.orElseThrow(() -> new ResourceNotFoundException(
							String.format("Cannot update customer:: No customer found with the provided ID: %s", userDto.getId())
					));
			if (!passwordEncoder.matches(userDto.getPassword(), customer.getPassword())) {
				throw new IllegalStateException("Wrong password");
			}

			return UserMapper.map(this.userRepository
					.save(UserMapper.map(userDto)));
		}
	@Override
	public AuthenticationResponse register(UserDto userDto) {
		if (userRepository.existsByEmail(userDto.getEmail())) {
			throw new ResourceAlreadyExistException("email already exist "+userDto.getEmail());
		}

		Users user = UserMapper.map(userDto);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
 		Users savedUser = userRepository.save(user);
		String jwtToken = jwtUtil.generateToken(UserMapper.mapToCustomUserDetails(savedUser));
		String refreshToken = jwtUtil.generateRefreshToken(UserMapper.mapToCustomUserDetails(savedUser));

		return AuthenticationResponse.builder()
				.accessToken(jwtToken)
				.refreshToken(refreshToken)
				.build();
	}
	@Override
	public AuthenticationResponse refreshToken(String refreshToken ) {

		final String userEmail = jwtUtil.extractEmail (refreshToken);

		// Validate the extracted email and the token
		if (userEmail != null) {
			CustomUserDetails customUserDetails = this.userRepository.findByEmail(userEmail)
					.map(UserMapper::mapToCustomUserDetails)
					.orElseThrow(() -> new ResourceNotFoundException(
							String.format("User not found with email: %s", userEmail)
					));

			if (jwtUtil.isTokenValid (refreshToken, customUserDetails)) {
				// Generate new tokens if the refresh token is valid
				String newJwtToken = jwtUtil.generateToken (customUserDetails);
				String newRefreshToken = jwtUtil.generateRefreshToken (customUserDetails);

				return AuthenticationResponse.builder ()
						.accessToken (newJwtToken)
						.refreshToken (newRefreshToken)
						.build ();
			}else {
				throw new RuntimeException("Refresh token is invalid or expired");
			}
		} else {
			throw new RuntimeException("Invalid refresh token");
		}
    }

	@Override
		public void deleteById(Long id) {
			this.userRepository.deleteById(id);
		}

		@Override
		public boolean existsById(Long id){
			return this.userRepository.findById(id)
					.isPresent();
		}


}

