/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest.dto;

import java.util.List;

public class PlaylistsDTO {
    public List<PlaylistDTO> playlists;
    public int length;


    public PlaylistsDTO() {
        // Empty constructor to prevent this weird Java Dependency Injection from crashing
    }

    public PlaylistsDTO(List<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }
}
