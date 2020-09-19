package com.manikarthi25.eureka.user.controller;

import javax.ws.rs.core.MediaType;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manikarthi25.eureka.user.dto.UserDTO;
import com.manikarthi25.eureka.user.request.model.UserRequestModel;
import com.manikarthi25.eureka.user.response.model.UserReponse;
import com.manikarthi25.eureka.user.response.model.UserResponseModel;
import com.manikarthi25.eureka.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private Environment environment;

	@Autowired
	private UserService userService;

	@GetMapping("/status")
	public String getUserAppStatus() {
		return "User App is working on port number" + environment.getProperty("local.server.port");
	}

	@PostMapping(path = "/add", produces = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON }, consumes = {
			MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

	public ResponseEntity<UserResponseModel> addUser(@RequestBody UserRequestModel userRequestModel) {

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO employeeDTO = modelMapper.map(userRequestModel, UserDTO.class);

		UserResponseModel employeeResponseModel = modelMapper.map(userService.addUser(employeeDTO),
				UserResponseModel.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseModel);

	}

	@GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON }, consumes = {
			MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	//@PreAuthorize("principal == #userId")
	@PostAuthorize("principal == returnObject.body.userId")
	public ResponseEntity<UserReponse> getUserDetailsByUserId(@PathVariable String userId) {
		
		UserDTO userDTO = userService.getUserDetailsByUserId(userId);
		UserReponse userReponse = new ModelMapper().map(userDTO, UserReponse.class);
		return ResponseEntity.status(HttpStatus.OK).body(userReponse);

	}
}
