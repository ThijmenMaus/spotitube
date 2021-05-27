/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dto;

import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.rest.dto.TrackDTO;
import nl.thijmenmaus.han.rest.dto.TracksDTO;

import java.util.ArrayList;
import java.util.List;

public class TrackMapperDTO {
    public TrackDTO mapTrackToDTO(Track track) {
        return new TrackDTO(
                track.getId(),
                track.getTitle(),
                track.getPerformer(),
                track.getDuration(),
                track.getAlbum(),
                track.getPlaycount(),
                track.getPublicationDate(),
                track.getDescription(),
                track.isAvailableOffline()
        );
    }

    public TracksDTO mapTracksToDTO(List<Track> tracks) {
        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = new ArrayList<>();

        for (Track t : tracks) tracksDTO.tracks.add(mapTrackToDTO(t));

        return tracksDTO;
    }
}
