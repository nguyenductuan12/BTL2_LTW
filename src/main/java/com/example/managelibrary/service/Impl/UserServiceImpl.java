package com.example.managelibrary.service.Impl;


import com.example.managelibrary.dto.UserDto;
import com.example.managelibrary.entity.User;
import com.example.managelibrary.reponsitory.UserReponsitory;
import com.example.managelibrary.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserReponsitory userReponsitory;

	@Override
	public void save(UserDto userDto) {
		LocalDateTime Date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String creationDate = Date.format(formatter);
		User user = new User(userDto.getEmail(), userDto.getUserDisplayName(), ".", userDto.getPassword(),
				creationDate, "ROLE_USER");
		userReponsitory.save(user);

	}

	@Override
	public Boolean checkPasswordUser(UserDto userDto) {
		User user = userReponsitory.findUserByEmail(userDto.getEmail());
		if (user.getPassword().equals(userDto.getPassword())) {
			userDto.setRole(user.getRole());
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkUserbyEmail(String email) {
		User user = userReponsitory.findUserByEmail(email);
		if (user == null)
			return false;
		return true;
	}

	@Override
	public User getUserbyEmail(String email) {
		return userReponsitory.getUserByEmail(email);
	}
}
