/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dao;

import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperDAOTest {
    private UserMapperDAO userMapperDAO;

    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        userMapperDAO = new UserMapperDAO();
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void mapDTOtoDomainTest() throws SQLException {
        User expectedUser = DataMocker.mockUser();

        when(resultSet.getInt("id")).thenReturn(expectedUser.getId());
        when(resultSet.getString("username")).thenReturn(expectedUser.getUsername());
        when(resultSet.getString("password")).thenReturn(expectedUser.getPassword());
        User actualUser = userMapperDAO.mapEntityToDomain(resultSet);

        assertEquals(actualUser.getId(), expectedUser.getId());
        assertEquals(actualUser.getUsername(), expectedUser.getUsername());
        assertEquals(actualUser.getPassword(), expectedUser.getPassword());
    }
}
