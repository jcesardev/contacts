package com.albo.directory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albo.directory.models.User;
import com.albo.directory.repos.UserJpaRepository;

@RestController
@RequestMapping("/usr")
public class UserController {
	
	@Autowired
	private UserJpaRepository userJpaRepository;

	@GetMapping()
	public List<User> findAll() {
		return userJpaRepository.findAll();
	}
	
	@PostMapping()
	public User create(User user) {
		return userJpaRepository.save(user);
	}
	
	@GetMapping("/{userId}")
	public User getUserDetail(@PathVariable(name="userId")Long userId) {
		return userJpaRepository.getOne(userId);
	}	
}
