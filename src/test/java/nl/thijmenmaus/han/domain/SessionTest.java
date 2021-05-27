/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.domain;

import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {
    @Test
    public void emptyInitalizationTest() {
        // Arrange & Act
        Session session = new Session();
        // Assert
        assertNotNull(session);
    }

    @Test
    public void gettersAndSettersTest() {
        // Arrange
        Session fake = DataMocker.mockSession();
        Session actual = new Session();
        // Act
        actual.setUser(fake.getUser());
        actual.setToken(fake.getToken());
        // Assert
        assertEquals(actual.getUser(), fake.getUser());
        assertEquals(actual.getToken(), fake.getToken());
    }
}
