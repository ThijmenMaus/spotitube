/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest;

import nl.thijmenmaus.han.common.exception.SpotitubeException;
import nl.thijmenmaus.han.datasource.dao.user.UserDAO;
import nl.thijmenmaus.han.domain.Session;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.rest.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RegisterControllerTest {
    private RegisterController registerController;
    private UserDTO userDTO;

    private User userMock;
    private UserDAO userDAOMock;

    @BeforeEach
    public void setup() {
        registerController = new RegisterController();;
        userDTO = new UserDTO();

        userMock = mock(User.class);
        userDAOMock = mock(UserDAO.class);

        registerController.setUserDAO(userDAOMock);
        registerController.setUser(userMock);
    }

    @Test
    public void registerSuccessTest() throws SpotitubeException {
        // Arrange
        int expectedStatusCode = 201;
        User expectedUser = new User(1, "Thijmen", "pretendThatThisIsHashed");

        UserDTO submittedUser = new UserDTO();
        userDTO.user = "Thijmen";
        userDTO.password = "helloIAmCoding!";

        // Act
        when(userDAOMock.doesUserExist(submittedUser.user)).thenReturn(false);
        UserDAO spy = Mockito.spy(userDAOMock);
        doNothing().when(spy).createUser(submittedUser.user, expectedUser.getPassword());

        Response response = registerController.register(submittedUser);

        // Assert
        assertEquals(expectedStatusCode, response.getStatus());
    }

    @Test
    public void regiserUserExistsTest() throws SpotitubeException {
        // Arrange
        int expectedStatusCode = 409;

        UserDTO submittedUser = new UserDTO();
        userDTO.user = "Thijmen";
        userDTO.password = "helloIAmCoding!";

        // Act
        when(userDAOMock.doesUserExist(submittedUser.user)).thenReturn(true);

        // Assert
        assertThrows(SpotitubeException.class, () -> {
            Response response = registerController.register(submittedUser);
            assertEquals(expectedStatusCode, response.getStatus());
        });
    }
}
