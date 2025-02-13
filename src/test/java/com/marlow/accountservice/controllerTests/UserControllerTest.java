package com.marlow.accountservice.controllerTests;

import com.marlow.accountservice.controller.UserController;
import com.marlow.accountservice.entity.User;
import com.marlow.accountservice.serviceTest.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void saveUserSuccess() throws Exception {
        // Prepare a valid UserDto request body
        String userDtoJson = "{\"name\": \"Test\", \"email\": \"test@gmail.com\"}";

        // Mock userService.saveUser to return a successful response
        when(userService.saveUser(any())).thenReturn(User.builder().build());

        // Perform POST request to /users with valid UserDto
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                // Verify that the response status code is 201 create.
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{'message':'Success: User details successfully saved!'}"));
    }

    @Test
    public void saveUserWithAlreadyExistsEmailFail() throws Exception {
        // Prepare a valid UserDto request body
        String userDtoJson = "{\"name\": \"Test\", \"email\": \"test@gmail.com\"}";

        // Mock userService.saveUser to return a conflict response
        when(userService.saveUser(any())).thenReturn(null);

        // Perform POST request to /user/new with valid UserDto
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                // Verify that the response status code is conflict
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json("{'message':'Fail: Email already exists!'}"));
    }


}