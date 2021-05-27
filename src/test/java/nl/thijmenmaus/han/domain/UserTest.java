/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.domain;

import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserTest {
    private User userMock;

    @BeforeEach
    public void setup() {
        userMock = mock(User.class);
    }

    @Test
    public void emptyInitalizationTest() {
        // Arrange & Act
        User user = new User();
        // Assert
        assertNotNull(user);
    }

    @Test
    public void gettersAndSettersTest() {
        // Arrange
        User fake = DataMocker.mockUser();
        User actual = new User();
        // Act
        actual.setId(fake.getId());
        actual.setUsername(fake.getUsername());
        actual.setPassword(fake.getPassword());
        // Assert
        assertEquals(fake.getId(), actual.getId());
        assertEquals(fake.getUsername(), actual.getUsername());
        assertEquals(fake.getPassword(), actual.getPassword());
    }
}
