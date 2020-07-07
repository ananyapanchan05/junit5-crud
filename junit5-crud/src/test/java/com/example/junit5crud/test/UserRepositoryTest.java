package com.example.junit5crud.test;



import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.junit5crud.entity.UserEntity;
import com.example.junit5crud.repository.UserRepository;



@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	UserRepository userRepo;
	
	 @Test
	 public void getAllUsersTest() 
	 {
		
		 assertNotNull(userRepo.findAll());
		 List<UserEntity> user_list=userRepo.findAll();
		 assertEquals(5,user_list.size());
	 }
	 
	 @Test
	 public void getUserByIdTest()
	 {
		 UserEntity user_obj=userRepo.findById(1004);
		 assertEquals("Ananya",user_obj.getName());
	 }
	 @Test
	 public void addUsersTest()
	 {
		    UserEntity u = new UserEntity(1,"Lokesh",30,"USA");
	        userRepo.save(u);
	        UserEntity gotUser=userRepo.findById(1);
	        assertNotNull(u);
	        assertEquals(gotUser.getName(),u.getName());
	 }
	 @Test
	 public void deleteUserTest()
	 {
		 assertThat(userRepo.findAll().size(), is(5));
		 UserEntity deleted=userRepo.findById(1004);
		 userRepo.delete(deleted);
		 assertThat(userRepo.findAll().size(), is(4));
	 }

}
