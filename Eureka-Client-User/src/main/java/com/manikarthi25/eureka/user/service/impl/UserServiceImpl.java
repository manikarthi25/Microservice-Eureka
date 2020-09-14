package com.manikarthi25.eureka.user.service.impl;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.manikarthi25.eureka.user.dto.UserDTO;
import com.manikarthi25.eureka.user.entity.UserEO;
import com.manikarthi25.eureka.user.repo.UserRepo;
import com.manikarthi25.eureka.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepo userRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDTO addUser(UserDTO userDTO) {

		userDTO.setUserId(UUID.randomUUID().toString());
		userDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEO userEO = modelMapper.map(userDTO, UserEO.class);

		userRepo.save(userEO);
		UserDTO user = modelMapper.map(userEO, UserDTO.class);
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEO userEO = userRepo.findByEmail(username);

		if (userEO == null)
			throw new UsernameNotFoundException(username);

		return new User(userEO.getEmail(), userEO.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEO userEO = userRepo.findByEmail(email);

		if (userEO == null)
			throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEO, UserDTO.class);
	}

}
