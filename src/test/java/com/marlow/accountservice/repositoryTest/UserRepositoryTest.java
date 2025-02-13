
package com.marlow.accountservice.repositoryTest;
import com.marlow.accountservice.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

@Autowired
    private UserRepository userRepository;

@Test
public void testSaveUser() {
    // Define test data
    String name = "test1";
    String email = "test1@example.com";

    // Create a User object with the test data
    User user = User.builder()
            .name(name).email(email)
            .build();

    // Save the user to the database
    User savedUser = userRepository.save(user);

    // Assert that the retrieved user is not null
    assertNotNull(savedUser);

    // Assert that the retrieved user id is not null
    assertNotNull(savedUser.getId());

    // Assert that the retrieved user's name matches the expected name
    assertEquals(name, savedUser.getName());

}

    @Test
    public void testFindByEmailUserNotFound() {
        // Find an non existent user
        User foundUser = userRepository.findByEmail("test1");

        // Assert that the retrieved user is null
        assertNull(foundUser);
    }
}
