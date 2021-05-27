/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MainTest {
    private static final Main main = new Main();

    @Test
    public void getIndexWelcomeTest() {
        String expected = "Congratulations Hackerman, you found it! Welcome to the root of the Spotitube API! We have some free cookies and lunch if you'd like?";

        String actual = main.getIndex();

        assertEquals(expected, actual);
    }

    @Test
    public void getIndexNotWelcomeTest() {
        String expected = "Go away, this is my code! >:(";

        String actual = main.getIndex();

        assertNotEquals(expected, actual);
    }
}
