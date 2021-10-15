/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.domain;

import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    @Test
    public void emptyInitalizationTest() {
        Playlist playlist = new Playlist();

        assertNotNull(playlist);
    }

    @Test
    public void gettersAndSettersTest() {
        Playlist fake = DataMocker.mockPlaylist();
        Playlist actual = new Playlist();

        actual.setId(fake.getId());
        actual.setName(fake.getName());
        actual.setOwner(fake.getOwner());
        actual.setTracks(fake.getTracks());
        actual.setLength(fake.getLength());

        assertEquals(fake.getId(), actual.getId());
        assertEquals(fake.getName(), actual.getName());
        assertEquals(fake.getOwner(), actual.getOwner());
        assertEquals(fake.getTracks(), actual.getTracks());
        assertEquals(fake.getLength(), actual.getLength());
    }
}
