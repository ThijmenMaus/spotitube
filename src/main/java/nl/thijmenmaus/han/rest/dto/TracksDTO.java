/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest.dto;

import java.util.List;

public class TracksDTO {
    public List<TrackDTO> tracks;

    public TracksDTO() {
        // Empty constructor to prevent this weird Java Dependency Injection from crashing
    }

    public TracksDTO(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
