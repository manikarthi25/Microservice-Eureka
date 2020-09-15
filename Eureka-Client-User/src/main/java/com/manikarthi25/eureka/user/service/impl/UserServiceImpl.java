package com.manikarthi25.eureka.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.manikarthi25.eureka.user.dto.UserDTO;
import com.manikarthi25.eureka.user.entity.UserEO;
import com.manikarthi25.eureka.user.repo.AlbumServiceClient;
import com.manikarthi25.eureka.user.repo.UserRepo;
import com.manikarthi25.eureka.user.response.model.AlbumResponseModel;
import com.manikarthi25.eureka.user.service.UserService;

import feign.FeignException;

@Service
public class UserServiceImpl implements UserService {

	private UserRepo userRepo;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	// private RestTemplate restTemplate;
	private Environment environment;
	AlbumServiceClient albumServiceClient;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UserServiceImpl(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder,
			AlbumServiceClient albumServiceClient, Environment environment) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		// this.restTemplate = restTemplate;
		this.environment = environment;
		this.albumServiceClient = albumServiceClient;
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

	@Override
	public UserDTO getUserDetailsByUserId(String userId) {
		UserEO userEO = userRepo.findByUserId(userId);

		if (userEO == null)
			throw new UsernameNotFoundException("User Not Found");
		UserDTO userDTO = new ModelMapper().map(userEO, UserDTO.class);
		/*
		 * String alubumUrl = String.format(environment.getProperty("alubum.url"),
		 * userId); ResponseEntity<List<AlbumResponseModel>> albumResponseModelList =
		 * restTemplate.exchange(alubumUrl, HttpMethod.GET, null, new
		 * ParameterizedTypeReference<List<AlbumResponseModel>>() { });
		 * List<AlbumResponseModel> albumsList = albumResponseModelList.getBody();
		 */
		List<AlbumResponseModel> albumsList = null;
		try {
			albumsList = albumServiceClient.getAlbums(userId);
		} catch (FeignException e) {
			logger.error(e.getLocalizedMessage());
		}

		userDTO.setAlbumsList(albumsList);
		return userDTO;
	}

}
