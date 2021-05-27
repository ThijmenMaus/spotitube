/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.dao;

import nl.thijmenmaus.han.common.exception.EntityNotFoundException;
import nl.thijmenmaus.han.datasource.IConnectionFactory;
import nl.thijmenmaus.han.datasource.dao.user.UserDAO;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.mapper.dao.UserMapperDAO;
import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserDAOTest {
    private IConnectionFactory connectionFactory;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    //    private PlaylistDAO playlistDAO;
//    private PlaylistMapperDAO playlistMapperDAO;
    private UserDAO userDAO;
    private UserMapperDAO userMapperDAO;

    @BeforeEach
    public void setup() {
        connectionFactory = mock(IConnectionFactory.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        result = mock(ResultSet.class);
        userMapperDAO = mock(UserMapperDAO.class);

        userDAO = new UserDAO();
        userDAO.setConnectionFactory(connectionFactory);
        userDAO.setUserMapperDAO(userMapperDAO);
    }

    @Test
    public void getUserByUsernameTest() throws SQLException, EntityNotFoundException {
        // Arrange
        String expectedQuery = "SELECT `id`, `username`, `password` FROM `user` WHERE `username` = ?";
        User expectedUser = DataMocker.mockUser();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);
        when(result.next()).thenReturn(true).thenReturn(false);
        when(userMapperDAO.mapEntityToDomain(result)).thenReturn(expectedUser);

        // Act
        User actualUser = userDAO.getByUsername(expectedUser.getUsername());

        // Assert
        verify(connection).prepareStatement(expectedQuery);
        verify(statement).executeQuery();
        verify(statement).setString(1, expectedUser.getUsername());
        assertEquals(expectedUser.getId(), actualUser.getId());
    }

    @Test
    public void createUserTest() throws SQLException {
        // Arrange
        String expectedQuery = "INSERT INTO `user` (`username`, `password`) VALUES (?, ?)";
        User user = DataMocker.mockUser();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);

        // Act
        userDAO.createUser(user.getUsername(), user.getPassword());

        // Assert
        verify(connection).prepareStatement(expectedQuery);
        verify(statement).setString(1, user.getUsername());
        verify(statement).setString(2, user.getPassword());
        verify(statement).execute();
    }

    @Test
    public void doesUserExistTest() throws EntityNotFoundException {
        // Arrange
        User expectedUser = DataMocker.mockUser();
        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.getByUsername(expectedUser.getUsername())).thenReturn(expectedUser);
        when(userDAOMock.doesUserExist(expectedUser.getUsername())).thenReturn(true);

        // Act
        boolean doesUserExist = userDAOMock.doesUserExist(expectedUser.getUsername());

        // Assert
        assertTrue(doesUserExist);
    }
}
