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
        String expected = "Test hallo!";

        SpotitubeException exception = new SpotitubeException("Test hallo!");

        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void initializeExceptionWithStatusAndMessageTest() {
        String expected = "Custom!";
        Status expectedStatus = Status.UNAUTHORIZED;

        SpotitubeException exception = new SpotitubeException("Custom!", Status.UNAUTHORIZED);

        assertEquals(expected, exception.getMessage());
        assertEquals(expectedStatus, exception.getStatus());
    }
}
