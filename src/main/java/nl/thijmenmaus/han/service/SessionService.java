/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.thijmenmaus.han.common.ApplicationConfig;
import nl.thijmenmaus.han.common.exception.EntityNotFoundException;
import nl.thijmenmaus.han.datasource.dao.user.UserDAO;
import nl.thijmenmaus.han.domain.Session;
import nl.thijmenmaus.han.domain.User;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Map;

public class SessionService {
    private static final String issuer = "spotitube-back";

    private ApplicationConfig config;
    private UserDAO userDAO;

    public Session buildSession(String username) throws NotAuthorizedException {
        try {
            User user = userDAO.getByUsername(username);
            if (user == null) throw new EntityNotFoundException(UserDAO.class);
            String token = generateToken(user.getId(), user.getUsername());
            return new Session(user.getUsername(), token);
        } catch (EntityNotFoundException exception) {
            throw new NotAuthorizedException(exception);
        }
    }

    public DecodedJWT verify(String token) throws NotAuthorizedException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(config.getSecret());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            throw new NotAuthorizedException(exception);
        }
    }

    public User getUser(String token) throws InternalServerErrorException, EntityNotFoundException {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claims = jwt.getClaims();
            try {
                return new User(claims.get("id").asInt(), claims.get("username").asString());
            } catch (Exception exception) {
                throw new EntityNotFoundException(User.class);
            }
        } catch (JWTDecodeException exception) {
            throw new InternalServerErrorException(exception);
        }
    }


    public String generateToken(int id, String username) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(config.getSecret());
            return JWT.create().withIssuer(issuer).withClaim("id", id).withClaim("username", username).sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Inject
    public void setConfig(ApplicationConfig config) {
        this.config = config;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
