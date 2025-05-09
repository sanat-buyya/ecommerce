package com.jsp.ecommerce.service;

import com.jsp.ecommerce.dto.UserDto;

import jakarta.servlet.http.HttpSession;

public interface GeneralService {

	String login(String email, String password, HttpSession session);

	String logout(HttpSession session);

}