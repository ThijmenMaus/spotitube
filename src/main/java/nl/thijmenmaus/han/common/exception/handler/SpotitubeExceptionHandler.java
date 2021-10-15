/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.common.exception.handler;

import nl.thijmenmaus.han.common.exception.SpotitubeException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class SpotitubeExceptionHandler implements ExceptionMapper<SpotitubeException> {
    @Override
    public Response toResponse(SpotitubeException exception) {
        return Response.status(exception.getStatus()).entity(exception.getMessage()).build();
    }
}
