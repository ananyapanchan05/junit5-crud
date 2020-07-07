package com.example.junit5crud.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.junit5crud.entity.UserEntity;
import com.example.junit5crud.repository.UserRepository;
import com.example.junit5crud.service.UserService;

//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
	@Mock
	UserRepository UserRepoMock;
	@InjectMocks
	UserService user_serv;
	@Test
	public void getAllUsersTest()
	{
		
		when(UserRepoMock.findAll()).thenReturn((Arrays.asList(new UserEntity(1,"Name_1",10,"Hyd"),
				new UserEntity(2,"Name_2",20,"Banglore"),
				new UserEntity(3,"Name_3",50,"Pune")
				)));
		
		List<UserEntity> usrs=user_serv.getAllUsers();
		assertEquals(1,usrs.get(0).getId()); // If the first item in the list is equal to getValue.
		assertEquals("Name_2",usrs.get(1).getName()); // If the second item in the list is equal to getValue.
		assertEquals(50,usrs.get(2).getAge());  // If the third item in the list is equal to getValue.
	}
	@Test
	public void getUserByIdTest() throws Exception 
	{ 
		when(UserRepoMock.findById(Mockito.anyInt())).thenReturn
		(new UserEntity(1,"User_1",50,"Hyderabad"));
		
		UserEntity usr=user_serv.getUserById(1);
		
		assertEquals("User_1",usr.getName());
        assertEquals(50,usr.getAge());
     }
	    @Test
	    public void addUsersTest()
	    {
	        UserEntity u = new UserEntity(1,"Lokesh",30,"USA");
	        UserRepoMock.save(u);
	        verify(UserRepoMock, times(1)).save(u);
	    }
	    @Test
	    public void updateUserTest()
	    {
	        UserEntity u = new UserEntity(1,"Lokesh",30,"USA");
	        UserRepoMock.save(u);
	        UserEntity u1=new UserEntity(1,"Anny",30,"USA");
	        UserRepoMock.save(u1);
	        verify(UserRepoMock, times(1)).save(u1);
	    }
	    
	    
	    @Test
	    public void deleteUserTest()
	    {
	    	UserEntity u = new UserEntity(1,"Lokesh",30,"USA");
	        UserRepoMock.delete(u);
	        verify(UserRepoMock, times(1)).delete(u);
	    }


}
