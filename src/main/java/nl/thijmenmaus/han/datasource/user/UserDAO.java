/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.user;

import nl.thijmenmaus.han.common.exception.EntityNotFoundException;
import nl.thijmenmaus.han.datasource.IConnectionFactory;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.mapper.dao.UserMapperDAO;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements IUserDAO {

    private static final String GET_BY_USERNAME_QUERY = "SELECT `id`, `username`, `password` FROM `user` WHERE `username` = ?";
    private static final String CREATE_QUERY = "INSERT INTO `user` (`username`, `password`) VALUES (?, ?)";

    private IConnectionFactory connectionFactory;
    private UserMapperDAO userMapperDAO;

    @Override
    public User getByUsername(String username) throws InternalServerErrorException, EntityNotFoundException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_BY_USERNAME_QUERY);

            statement.setString(1, username);

            ResultSet result = statement.executeQuery();

            if (result != null && result.next())
                return userMapperDAO.mapEntityToDomain(result);

            throw new EntityNotFoundException(UserDAO.class);
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Override
    public void createUser(String username, String password) throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY);

            statement.setString(1, username);
            statement.setString(2, password);

            statement.execute();
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Override
    public boolean doesUserExist(String username) throws InternalServerErrorException {
        try {
            User user = getByUsername(username);
            return user != null;
        } catch (EntityNotFoundException exception) {
            return false;
        }
    }

    @Inject
    public void setConnectionFactory(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Inject
    public void setUserMapperDAO(UserMapperDAO userMapperDAO) {
        this.userMapperDAO = userMapperDAO;
    }
}
