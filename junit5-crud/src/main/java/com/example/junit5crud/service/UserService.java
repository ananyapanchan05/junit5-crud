package com.example.junit5crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.junit5crud.entity.UserEntity;
import com.example.junit5crud.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	

	public UserEntity addUsers(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	public List<UserEntity> getAllUsers() {
		List<UserEntity> userEntityList = userRepository.findAll();
		//System.out.println("Getting data from DB : " + userEntityList);
		return userEntityList;
	}
	public UserEntity updateUser(int id,UserEntity userDetails)
	{
		UserEntity userEntity=userRepository.findById(id);
		userEntity.setName(userDetails.getName());
		userEntity.setAge(userDetails.getAge());
		userEntity.setAddress(userDetails.getAddress());
		UserEntity updatedUser =userRepository.save(userEntity);
		return updatedUser;
	}

	public UserEntity getUserById(int id)
	{
		return userRepository.findById(id);
	}

	public void deleteUser(int id) {
		UserEntity userEntity =userRepository.findById(id);
		userRepository.delete(userEntity);
	}


}
