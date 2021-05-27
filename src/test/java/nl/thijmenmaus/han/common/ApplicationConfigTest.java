/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ApplicationConfigTest {
    private ApplicationConfig applicationConfig;

    private Properties propertiesMock;

    @BeforeEach
    public void setup() {
        applicationConfig = new ApplicationConfig();
        propertiesMock = mock(Properties.class);
        applicationConfig.setProperties(propertiesMock);
    }

    @Test
    public void initializeTest() throws IOException {
        // Arrange
        Properties spy = spy(propertiesMock);
        // Act
        doNothing().when(spy).load(Objects.requireNonNull(ApplicationConfig.class.getClassLoader().getResourceAsStream("application.properties")));
        // Assert
        assertNotNull(applicationConfig.getProperties());
    }

    @Test
    public void getPropertiesTest() {
        // Arrange & Act
        Properties properties = applicationConfig.getProperties();
        // Assert
        assertNotNull(properties);
    }

    @Test
    public void setPropertiesTest() {
        // Arrange
        Properties properties = new Properties();
        // Act
        applicationConfig.setProperties(properties);
        // Assert
        assertEquals(properties, applicationConfig.getProperties());

    }

}
