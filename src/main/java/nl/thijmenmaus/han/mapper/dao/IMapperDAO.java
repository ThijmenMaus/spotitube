/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IMapperDAO<E> {
    E mapEntityToDomain(ResultSet entity) throws SQLException;
}
