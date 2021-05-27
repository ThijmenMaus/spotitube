/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import nl.thijmenmaus.han.common.ApplicationConfig;
import nl.thijmenmaus.han.common.exception.EntityNotFoundException;
import nl.thijmenmaus.han.datasource.dao.user.UserDAO;
import nl.thijmenmaus.han.domain.Session;
import nl.thijmenmaus.han.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SessionServiceTest {
    @Mock
    private UserDAO userDAOMock;

    @Mock
    private ApplicationConfig applicationConfigMock;

    @Mock
    private SessionService sessionService;

    @Mock
    private JWT jwtMock;

    @BeforeEach
    public void setup() {
        userDAOMock = mock(UserDAO.class);
        jwtMock = mock(JWT.class);
        applicationConfigMock = mock(ApplicationConfig.class);
        when(applicationConfigMock.getSecret()).thenReturn("Test123Secret");

        sessionService = new SessionService();
        sessionService.setUserDAO(userDAOMock);
        sessionService.setConfig(applicationConfigMock);
    }


    @Test
    public void buildSessionTest() throws EntityNotFoundException {
        // Arrange
        User expectedUser = new User(1, "Thijmen");
        when(userDAOMock.getByUsername(expectedUser.getUsername())).thenReturn(expectedUser);

        Session expected = new Session("Thijmen", sessionService.generateToken(expectedUser.getId(), expectedUser.getUsername()));

        // Act
        Session actual = sessionService.buildSession("Thijmen");

        // Assert
        assertEquals(expected.getUser(), actual.getUser());
        assertEquals(expected.getToken(), actual.getToken());
    }

    @Test
    public void buildSessionNoUserTest() {
        User user = new User(2, "IkBestaNiet");
        assertThrows(NotAuthorizedException.class, () -> {
            sessionService.buildSession(user.getUsername());
        });
    }

    @Test
    public void verifyTest() {
        // Arrange
        String token = sessionService.generateToken(1, "Thijmen");
        // Act & Assert
        sessionService.verify(token);
    }

    @Test
    public void verifyUnauthorizedTest() {
        // Arrange
        String token = sessionService.generateToken(1, "Thijmen");

        // Act & Assert
        assertThrows(NotAuthorizedException.class, () -> {
            when(applicationConfigMock.getSecret()).thenReturn("WrongSecret");
            sessionService.verify(token);
        });
    }

    @Test
    public void getUserFromTokenTest() throws EntityNotFoundException {
        // Arrange
        User expectedUser = new User(1, "Thijmen");
        String token = sessionService.generateToken(expectedUser.getId(), expectedUser.getUsername());

        // Act
        User actualUser = sessionService.getUser(token);

        // Assert
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test
    public void jwtDecodeFailureTest() {
        // Arrange
        String token = "DADADADATOKEN";
        // Assert
        assertThrows(InternalServerErrorException.class, () -> {
            sessionService.getUser(token);
        });
    }
}
