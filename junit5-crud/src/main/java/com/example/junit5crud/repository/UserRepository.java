package com.example.junit5crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.junit5crud.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity,Integer>{
	UserEntity findById(int id);

}
