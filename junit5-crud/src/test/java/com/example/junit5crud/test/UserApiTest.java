package com.example.junit5crud.test;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import com.example.junit5crud.api.UserApi;
import com.example.junit5crud.entity.UserEntity;
import com.example.junit5crud.service.UserService;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserApi.class)
public class UserApiTest {
	@MockBean
	private UserService UserServ;
	@Autowired
	private MockMvc mockMVC;
	
	String exampleUserJson = "{\"id\":2,\"name\":\"User_2\",\"age\":20,\"address\":\"Pune\"}";

	// Test Get All Users.
	@Test
	public void getAllUsersTest() throws Exception
	{
		when(UserServ.getAllUsers()).thenReturn
		(Arrays.asList(new UserEntity(1,"User_1",50,"Hyderabad"),
				new UserEntity(2,"User_2",20,"Hyderabad"),
				new UserEntity(3,"User_3",30,"Chennai")
				));
		
		mockMVC.perform( MockMvcRequestBuilders
			      .get("/getAllUsers")
			      .accept(MediaType.APPLICATION_JSON))
			      .andDo(print())
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
			      .andExpect(MockMvcResultMatchers.jsonPath("$..id").isNotEmpty());
			      
	}
	// Test Get Users by ID.
	@Test
	public void getUserByIdTest() throws Exception 
	{ 
		when(UserServ.getUserById(Mockito.anyInt())).thenReturn
		(new UserEntity(1,"User_1",50,"Hyderabad"));
		
	  mockMVC.perform(MockMvcRequestBuilders
	      .get("/user/{id}", 1)
	      .accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User_1"));
	 }
	
	//Test Post Users
	@Test
	public void addUsersTest() throws Exception 
	{
		UserEntity mockUser = new UserEntity(1, "User_1", 50,"Hyderabad");

		
		when(
			UserServ.addUsers(Mockito.any(UserEntity.class))).thenReturn(mockUser);

		   mockMVC.perform( MockMvcRequestBuilders
	      .post("/addUsers")
	      .content(exampleUserJson)
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isCreated())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User_1"));
	}
	@Test
	public void updateUserTest() throws Exception 
	{
		
		UserEntity updatedMock=new UserEntity(2, "User_2", 20,"Pune");
		when(UserServ.updateUser(Mockito.anyInt(),Mockito.any(UserEntity.class))).thenReturn(updatedMock);
		//Mockito.when(UserServ.updateUser(2,updatedMock)).thenReturn(updatedMock);
	      mockMVC.perform(MockMvcRequestBuilders
	      .put("/updateUser/{id}",2)
	      .content(exampleUserJson)
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("User_2"))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Pune"));
	}
	@Test
	public void deleteUserTest() throws Exception 
	{
		UserServ.deleteUser(1);
		verify(UserServ,times(1)).deleteUser(Mockito.anyInt());
	}

	

}
