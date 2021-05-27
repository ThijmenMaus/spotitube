/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest;

import nl.thijmenmaus.han.common.exception.EntityNotFoundException;
import nl.thijmenmaus.han.common.exception.SpotitubeException;
import nl.thijmenmaus.han.datasource.dao.user.IUserDAO;
import nl.thijmenmaus.han.datasource.dao.user.UserDAO;
import nl.thijmenmaus.han.domain.Session;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.rest.dto.UserDTO;
import nl.thijmenmaus.han.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginControllerTest {
    private LoginController loginController;
    private UserDTO userDTO;

    private SessionService sessionServiceMock;
    private User userMock;
    private IUserDAO userDAOMock;


    @BeforeEach
    public void setup() {
        loginController = new LoginController();
        userDTO = new UserDTO();

        sessionServiceMock = mock(SessionService.class);
        userMock = mock(User.class);
        userDAOMock = mock(IUserDAO.class);


        loginController.setSessionService(sessionServiceMock);
        loginController.setUserDAO(userDAOMock);
        loginController.setUser(userMock);
    }

    @Test
    public void loginSuccessTest() throws EntityNotFoundException, SpotitubeException {
        // Arrange
        int expectedStatusCode = 200;
        User expectedUser = new User(1, "Thijmen", "pretendThatThisIsHashed");

        UserDTO submittedUser = new UserDTO();
        userDTO.user = "Thijmen";
        userDTO.password = "helloIAmCoding!";

        // Act
        when(userDAOMock.getByUsername(submittedUser.user)).thenReturn(expectedUser);
        when(userMock.doesPasswordMatch(submittedUser.password, expectedUser.getPassword())).thenReturn(true);
        when(sessionServiceMock.buildSession(submittedUser.user)).thenReturn(new Session("thijmen", "pretentThatThisIsAToken"));

        Response response = loginController.login(submittedUser);

        // Assert
        assertEquals(expectedStatusCode, response.getStatus());
    }

    @Test
    public void loginWrongPasswordTest() throws EntityNotFoundException {
        // Arrange
        int expectedStatusCode = 401;
        User expectedUser = new User(1, "Thijmen", "pretendThatThisIsHashed");

        UserDTO submittedUser = new UserDTO();
        userDTO.user = "Thijmen";
        userDTO.password = "helloIAmCoding!";

        // Act
        when(userDAOMock.getByUsername(submittedUser.user)).thenReturn(expectedUser);
        when(userMock.doesPasswordMatch(submittedUser.password, expectedUser.getPassword())).thenReturn(false);


        // Assert
        assertThrows(SpotitubeException.class, () -> {
            Response response = loginController.login(submittedUser);
            assertEquals(expectedStatusCode, response.getStatus());
        });
    }

    @Test
    public void userNotFoundTest() throws EntityNotFoundException {
        // Arrange
        int expectedStatusCode = 404;

        UserDTO submittedUser = new UserDTO();
        userDTO.user = "Thijmen";
        userDTO.password = "helloIAmCoding!";

        // Act
        when(userDAOMock.getByUsername(submittedUser.user)).thenThrow(new EntityNotFoundException(User.class));

        // Assert
        assertThrows(SpotitubeException.class, () -> {
            Response response = loginController.login(submittedUser);
            assertEquals(expectedStatusCode, response.getStatus());
        });
    }

}
