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
        String expectedQuery = "SELECT `id`, `username`, `password` FROM `user` WHERE `username` = ?";
        User expectedUser = DataMocker.mockUser();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);
        when(result.next()).thenReturn(true).thenReturn(false);
        when(userMapperDAO.mapEntityToDomain(result)).thenReturn(expectedUser);

        User actualUser = userDAO.getByUsername(expectedUser.getUsername());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).executeQuery();
        verify(statement).setString(1, expectedUser.getUsername());
        assertEquals(expectedUser.getId(), actualUser.getId());
    }

    @Test
    public void createUserTest() throws SQLException {
        String expectedQuery = "INSERT INTO `user` (`username`, `password`) VALUES (?, ?)";
        User user = DataMocker.mockUser();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);

        userDAO.createUser(user.getUsername(), user.getPassword());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).setString(1, user.getUsername());
        verify(statement).setString(2, user.getPassword());
        verify(statement).execute();
    }

    @Test
    public void doesUserExistTest() throws EntityNotFoundException {
        User expectedUser = DataMocker.mockUser();
        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.getByUsername(expectedUser.getUsername())).thenReturn(expectedUser);
        when(userDAOMock.doesUserExist(expectedUser.getUsername())).thenReturn(true);

        boolean doesUserExist = userDAOMock.doesUserExist(expectedUser.getUsername());

        assertTrue(doesUserExist);
    }
}
