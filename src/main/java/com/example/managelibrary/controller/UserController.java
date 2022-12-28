package com.example.managelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.managelibrary.dto.UserDto;
import com.example.managelibrary.service.BookService;

@Controller
@SessionAttributes("userdto")
public class UserController {
	@Autowired
	private BookService bookService;
	
	@ModelAttribute("userdto")
	public UserDto userDto() {
		return new UserDto();
	}
	
	
}
