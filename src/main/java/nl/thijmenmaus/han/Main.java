/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class Main {

    @GET
    public String getIndex() {
        return "Congratulations Hackerman, you found it! Welcome to the root of the Spotitube API! We have some free cookies and lunch if you'd like?";
    }
}
