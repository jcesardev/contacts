package com.albo.directory.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.albo.directory.models.User;
import com.albo.directory.repos.UserRepository;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/usr")
@Api(value = "/usr", produces = "application/json", tags = {"User"}, description= "User management")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<User>> findAll(@RequestParam(required = false, name = "startLetter") String startsWith) {
	    List<User> users = null;
	    if(startsWith != null) {
	        users = userRepository.findByNameStartingWithIgnoreCase(startsWith);	        
	    }else {
	        users = userRepository.findAll();
	    }	    
	    if(!users.isEmpty()) {
	        return ResponseEntity.ok(users);
	    }
	    return ResponseEntity.notFound().build();
	}
	
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE}, 
	        produces = {MediaType.APPLICATION_JSON_VALUE})
	public User create(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping(path = "/{userId}", 
	        consumes= {MediaType.APPLICATION_JSON_VALUE}, 
	        produces = {MediaType.APPLICATION_JSON_VALUE} )
	public ResponseEntity<User> update(@PathVariable(name="userId")Long userId, @RequestBody User user) {
	    Optional<User> userFromDb = userRepository.findById(userId);
	    if(userFromDb.isPresent()) {
	        user.setId(userFromDb.get().getId());
	        userRepository.saveAndFlush(user);
	        return ResponseEntity.ok(user);
	    }
	    return ResponseEntity.notFound().build();
	}
	
	@GetMapping(path = "/{userId}", 
	        produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<User> getUserDetail(@PathVariable(name="userId") Long userId) {
	    Optional<User> user = userRepository.findById(userId);
	    if(user.isPresent()) {
	        return ResponseEntity.ok(user.get());
	    }
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(path = "/{userId}")
	public ResponseEntity<Object> delete(@PathVariable(name = "userId") Long userId) {	    
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
        
	}
}
