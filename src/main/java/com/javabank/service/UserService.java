package com.javabank.service;

import com.javabank.dto.RegisterRequest;
import com.javabank.entity.User;

public interface UserService {
	
	User registerUser(RegisterRequest request);

}
