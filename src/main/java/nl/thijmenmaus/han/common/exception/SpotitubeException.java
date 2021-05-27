/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.common.exception;

import javax.ws.rs.core.Response.Status;
import java.io.Serializable;

public class SpotitubeException extends Exception implements Serializable {
    private final Status status;

    public SpotitubeException(String message) {
        super(message);
        this.status = Status.BAD_REQUEST;
    }

    public SpotitubeException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
