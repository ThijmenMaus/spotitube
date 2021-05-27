/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.dao.user;

import nl.thijmenmaus.han.common.exception.EntityNotFoundException;
import nl.thijmenmaus.han.domain.User;

import javax.ws.rs.InternalServerErrorException;

public interface IUserDAO {
    User getByUsername(String username) throws InternalServerErrorException, EntityNotFoundException;

    void createUser(String username, String password) throws InternalServerErrorException;

    boolean doesUserExist(String username) throws InternalServerErrorException;
}
