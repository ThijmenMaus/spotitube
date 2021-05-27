/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.common.filter;

import nl.thijmenmaus.han.service.SessionService;

import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@IsAuthorized
public class IsAuthorizedFilter implements ContainerRequestFilter {
    private SessionService sessionService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        try {
            MultivaluedMap<String, String> params = containerRequestContext.getUriInfo().getQueryParameters();
            String token = params.get("token").get(0);
            try {
                sessionService.verify(token);
            } catch (NotAuthorizedException exception) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception exception) {
            containerRequestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
        }
    }

    @Inject
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }
}
