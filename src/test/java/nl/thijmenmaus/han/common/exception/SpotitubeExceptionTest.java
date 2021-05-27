/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.common.exception;

import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response.Status;

import static org.junit.jupiter.api.Assertions.*;

public class SpotitubeExceptionTest {
    @Test
    public void initializeExceptionMessageTest() {
        // Arrange
        String expected = "Test hallo!";

        // Act
        SpotitubeException exception = new SpotitubeException("Test hallo!");

        // Assert
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void initializeExceptionWithStatusAndMessageTest() {
        // Arrange
        String expected = "Custom!";
        Status expectedStatus = Status.UNAUTHORIZED;

        // Act
        SpotitubeException exception = new SpotitubeException("Custom!", Status.UNAUTHORIZED);

        // Assert
        assertEquals(expected, exception.getMessage());
        assertEquals(expectedStatus, exception.getStatus());
    }
}
