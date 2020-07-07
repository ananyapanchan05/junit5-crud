package com.example.junit5crud.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.junit5crud.entity.UserEntity;
import com.example.junit5crud.service.UserService;




@RestController
public class UserApi {
	@Autowired
	private UserService userService;
	
	@GetMapping("/getAllUsers")
	public List<UserEntity> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping(value = "/user/{id}") 
	public ResponseEntity<UserEntity> getUserById (@PathVariable("id") int id)
	{
		UserEntity userEntity=userService.getUserById(id);
	    return ResponseEntity.ok().body(userEntity);
	}
	
	
	@PostMapping(value = "/addUsers")
	public ResponseEntity<UserEntity> addUsers (@Validated @RequestBody UserEntity userDetails)
	{
		UserEntity userEntity=userService.addUsers(userDetails);
	    return new ResponseEntity<UserEntity>(userEntity, HttpStatus.CREATED);
	}
	
	
	@PutMapping(value = "/updateUser/{id}")
	public ResponseEntity<UserEntity> updateUser (@PathVariable("id") int id, @Validated @RequestBody UserEntity userDetails)
	{
		UserEntity usrEntity=userService.updateUser(id, userDetails);
	    
	    return new ResponseEntity<UserEntity>(usrEntity, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/deleteUser/{id}")
	public ResponseEntity<HttpStatus> deleteUser (@PathVariable("id") int id)
	{
	    userService.deleteUser(id);
	    return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
	}
}
