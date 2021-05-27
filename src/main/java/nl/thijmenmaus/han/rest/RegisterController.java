/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest;

import nl.thijmenmaus.han.common.exception.SpotitubeException;
import nl.thijmenmaus.han.datasource.dao.user.IUserDAO;
import nl.thijmenmaus.han.datasource.dao.user.UserDAO;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.rest.dto.UserDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class RegisterController {
    private IUserDAO userDAO;
    private User user;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserDTO dto) throws SpotitubeException {
        try {
            if (userDAO.doesUserExist(dto.user))
                throw new SpotitubeException("Deze gebruiker is al geregistreerd..", Response.Status.CONFLICT);

            userDAO.createUser(dto.user, user.buildPassword(dto.password));

            return Response.status(Response.Status.CREATED).build();
        } catch (InternalServerErrorException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het registreren van de gebruiker..");
        }
    }

    @Inject
    public void setUserDAO(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setUser(User user) {
        this.user = user;
    }
}
