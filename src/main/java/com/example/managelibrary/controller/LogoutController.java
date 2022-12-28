package com.example.managelibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import com.example.managelibrary.dto.UserDto;

@Controller

@SessionAttributes("userdto")
public class LogoutController {
    @ModelAttribute("userdto")
    public UserDto userDto(){
        return new UserDto();
    }
    @GetMapping("/logout")
    public String Logout(@ModelAttribute("userdto") UserDto userDto, WebRequest request, SessionStatus status){
//        Xóa session user ra khỏi vị trí
        status.setComplete();// đã hoàn thành
        request.removeAttribute("userdto",WebRequest.SCOPE_SESSION);//thực hiện xóa userdto ra khỏi tầm của session
        return "redirect:/login";
    }
}
