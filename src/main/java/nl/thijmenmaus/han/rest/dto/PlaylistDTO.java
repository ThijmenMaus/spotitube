/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest.dto;

import nl.thijmenmaus.han.domain.Track;

import java.util.List;

public class PlaylistDTO {
    public int id;
    public String name;
    public boolean owner;
    public List<Track> tracks;

    public PlaylistDTO() {
        // Empty constructor to prevent this weird Java Dependency Injection from crashing
    }

    public PlaylistDTO(int id, String name, boolean owner, List<Track> tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }
}
