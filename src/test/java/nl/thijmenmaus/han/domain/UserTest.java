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
    @Test
    public void emptyInitalizationTest() {
        User user = new User();

        assertNotNull(user);
    }

    @Test
    public void gettersAndSettersTest() {
        User fake = DataMocker.mockUser();
        User actual = new User();

        actual.setId(fake.getId());
        actual.setUsername(fake.getUsername());
        actual.setPassword(fake.getPassword());

        assertEquals(fake.getId(), actual.getId());
        assertEquals(fake.getUsername(), actual.getUsername());
        assertEquals(fake.getPassword(), actual.getPassword());
    }
}
