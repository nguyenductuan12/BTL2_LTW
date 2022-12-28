package com.example.managelibrary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.managelibrary.dto.UserDto;
import com.example.managelibrary.entity.User;
import com.example.managelibrary.service.UserService;

@Controller	
@SessionAttributes("userdto")
public class UserLoginController {
	
	@Autowired
    private UserService userService;
	
    @ModelAttribute("userdto")
    public UserDto userDto(){
        return new UserDto();
    }
    @GetMapping("/login")
    public String showLoginForm(){
        return "/login";
    }
    @PostMapping("/login")
    public String Login(@ModelAttribute("userdto") UserDto userDto, Model model){
    	
        if(userService.checkUserbyEmail(userDto.getEmail())==false){
            return "redirect:/login?emailwrong";
        }
        
        if(userService.checkPasswordUser(userDto)){
        	if(userDto.getRole().equals("ROLE_ADMIN")) {
        		return "redirect:/books?success";
        	}else if(userDto.getRole().equals("ROLE_USER")){
        		return "redirect:/books?success";
        	}
        	
        }
        
        return "redirect:/login?passwordwrong";
    }
}
