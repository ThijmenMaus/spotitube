/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConnectionFactoryTest {
    @Test
    public void initializeTest() {
        // Arrange & Act
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // Assert
        assertNotNull(connectionFactory);
    }

    @Test
    public void setPropertiesTest() {
        // Arrange & Act
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // Act & Assert
        connectionFactory.setProperties("test", "test", "test");
    }
}
