/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionFactoryTest {
    @Test
    public void initializeTest() {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        assertNotNull(connectionFactory);
    }

    @Test
    public void setPropertiesTest() {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setProperties("test", "test", "test");
    }
}
