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
        Session session = new Session();

        assertNotNull(session);
    }

    @Test
    public void gettersAndSettersTest() {
        Session fake = DataMocker.mockSession();
        Session actual = new Session();

        actual.setUser(fake.getUser());
        actual.setToken(fake.getToken());

        assertEquals(actual.getUser(), fake.getUser());
        assertEquals(actual.getToken(), fake.getToken());
    }
}
