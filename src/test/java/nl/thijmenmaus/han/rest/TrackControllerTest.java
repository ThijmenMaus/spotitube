/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest;

import nl.thijmenmaus.han.common.exception.SpotitubeException;
import nl.thijmenmaus.han.datasource.track.ITrackDAO;
import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.mapper.dto.TrackMapperDTO;
import nl.thijmenmaus.han.rest.dto.TracksDTO;
import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackControllerTest {
    private TrackController trackController;
    private TrackMapperDTO trackMapperDTO;

    private ITrackDAO trackDAOMock;

    @BeforeEach
    public void setup() {
        trackController = new TrackController();
        trackMapperDTO = new TrackMapperDTO();

        trackDAOMock = mock(ITrackDAO.class);

        trackController.setTrackDAO(trackDAOMock);
        trackController.setTrackMapperDTO(trackMapperDTO);
    }

    @Test
    public void getTracksTest() throws SpotitubeException {
        int expectedStatusCode = 200;
        List<Track> tracks = new ArrayList<>(){{
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
        }};
        int playlistId = 2;

        when(trackDAOMock.getTracksNotInPlaylist(playlistId)).thenReturn(tracks);
        TracksDTO expectedTracks = trackMapperDTO.mapTracksToDTO(tracks);

        Response response = trackController.getTracks(playlistId);

        assertEquals(expectedStatusCode, response.getStatus());
        assertEquals(tracks.get(0).getTitle(), expectedTracks.tracks.get(0).title);
        assertEquals(tracks.get(1).getTitle(), expectedTracks.tracks.get(1).title);
        assertEquals(tracks.get(2).getTitle(), expectedTracks.tracks.get(2).title);
    }
}
