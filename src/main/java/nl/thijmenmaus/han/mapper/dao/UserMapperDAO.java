/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dao;

import nl.thijmenmaus.han.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapperDAO implements IMapperDAO<User> {

    @Override
    public User mapEntityToDomain(ResultSet entity) throws SQLException {
        return new User(
                entity.getInt("id"),
                entity.getString("username"),
                entity.getString("password")
        );
    }
}
