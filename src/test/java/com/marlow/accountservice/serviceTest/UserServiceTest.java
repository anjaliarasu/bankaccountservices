package com.marlow.accountservice.serviceTest;

import com.marlow.accountservice.entity.User;
import com.marlow.accountservice.repositoryTest.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;



    private static final String TEST_NAME = "test";
    private static final String TEST_EMAIL = "tesstst@example.com";
    private User userDto;

    @BeforeEach
    void setUp() {
        // Create a UserDto object with test data before running the tests
        userDto = User.builder().name(TEST_NAME).email(TEST_EMAIL).build();
    }

    @Test
    public void saveUserSuccess() {
        // Mock the userRepository.findByEmail method to return null,
        // simulating that no user exists with the provided email
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(null);

        // Call the saveUser method on the userService with the userDto
        User response = userService.saveUser(userDto);

        // Verify that the findByEmail method was called exactly once with the given email
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());

        // Verify that the save method was called exactly once with any User object
        verify(userRepository, times(1)).save(any(User.class));

        // Assert that the response status code is HttpStatus.CREATED
        //assertEquals(TEST_EMAIL, response.getEmail());
    }

    @Test
    public void saveUserWithAlreadyExistsEmailFail() {
        // Mock the userRepository.findByEmail method to return a new User object,
        // simulating that a user already exists with the provided email
        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(new User());

        // Call the saveUser method on the userService with the userDto
        assertThrows(ResponseStatusException.class,()->userService.saveUser(userDto));


        // Verify that the findByEmail method was called exactly once with the given email
        verify(userRepository, times(1)).findByEmail(userDto.getEmail());

        // Verify that the save method was not called, as the email already exists
        verify(userRepository, times(0)).save(any(User.class));

        // Assert that the response status code is HttpStatus.CONFLICT

    }



}
