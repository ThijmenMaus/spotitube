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
        Properties spy = spy(propertiesMock);

        doNothing().when(spy).load(Objects.requireNonNull(ApplicationConfig.class.getClassLoader().getResourceAsStream("application.properties")));

        assertNotNull(applicationConfig.getProperties());
    }

    @Test
    public void getPropertiesTest() {
        Properties properties = applicationConfig.getProperties();

        assertNotNull(properties);
    }

    @Test
    public void setPropertiesTest() {
        Properties properties = new Properties();

        applicationConfig.setProperties(properties);

        assertEquals(properties, applicationConfig.getProperties());
    }

}
