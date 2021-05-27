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

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController {
    private SessionService sessionService;
    private IUserDAO userDAO;
    private User user;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO dto) throws SpotitubeException {
        try {
            try {
                User remoteUser = userDAO.getByUsername(dto.user);

                if (!user.doesPasswordMatch(dto.password, remoteUser.getPassword()))
                    throw new SpotitubeException("Deze gegevens zijn niet bekend..", Response.Status.UNAUTHORIZED);

                Session session = sessionService.buildSession(dto.user);

                return Response.ok().entity(session).build();
            } catch (EntityNotFoundException e) {
                throw new SpotitubeException("Deze gebruiker bestaat niet in het systeem..", Response.Status.NOT_FOUND);
            }
        } catch (NotAuthorizedException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het inloggen..");
        }
    }

    @Inject
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
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
