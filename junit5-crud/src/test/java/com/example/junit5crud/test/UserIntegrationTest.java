package com.example.junit5crud.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;

import com.example.junit5crud.Junit5CrudApplication;
import com.example.junit5crud.entity.UserEntity;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,
classes=Junit5CrudApplication.class)
public class UserIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort       
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void getAllUsersTest() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/getAllUsers",
				HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
	}

	@Test
	public void getUserByIdTest() {
		UserEntity userEntity = restTemplate.getForObject(getRootUrl() + "/user/1001", UserEntity.class);
		System.out.println(userEntity.getName());
		assertNotNull(userEntity); 
	}

	@Test
	public void addUsersTest() {
		UserEntity user = new UserEntity(1008,"User_8",45,"USA");

		ResponseEntity<UserEntity> postResponse = restTemplate.postForEntity(getRootUrl() + "/addUsers", user, UserEntity.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void updateUserTest() {
		int id = 1003;
		UserEntity user = restTemplate.getForObject(getRootUrl() + "/getAllUsers/" + id, UserEntity.class);
		user.setName("admin1");
		

		restTemplate.put(getRootUrl() + "/updateUser/" + id, user);

		UserEntity updatedUser = restTemplate.getForObject(getRootUrl() + "/getAllUsers/" + id, UserEntity.class);
		assertNotNull(updatedUser);
	}

	@Test
	public void deleteUser() {
		int id = 1002;
		UserEntity user = restTemplate.getForObject(getRootUrl() + "/getAllUsers/" + id, UserEntity.class);
		assertNotNull(user);

		restTemplate.delete(getRootUrl() + "/deleteUser/" + id);

		try {
			user = restTemplate.getForObject(getRootUrl() + "/getAllUsers/" + id, UserEntity.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
