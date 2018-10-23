package com.nelioalves.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.nelioalves.cursomc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			System.out.println("AUTENTICADO: __"+(UserSS)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return(UserSS)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch (Exception e) {
			return null;
		}
	}
	
}
