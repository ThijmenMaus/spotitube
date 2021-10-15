/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest;

import nl.thijmenmaus.han.common.exception.EntityNotFoundException;
import nl.thijmenmaus.han.common.exception.SpotitubeException;
import nl.thijmenmaus.han.datasource.playlist.IPlaylistDAO;
import nl.thijmenmaus.han.datasource.track.ITrackDAO;
import nl.thijmenmaus.han.domain.Playlist;
import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.mapper.dto.PlaylistMapperDTO;
import nl.thijmenmaus.han.mapper.dto.TrackMapperDTO;
import nl.thijmenmaus.han.rest.dto.PlaylistDTO;
import nl.thijmenmaus.han.rest.dto.PlaylistsDTO;
import nl.thijmenmaus.han.rest.dto.TrackDTO;
import nl.thijmenmaus.han.rest.dto.TracksDTO;
import nl.thijmenmaus.han.service.SessionService;
import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistControllerTest {
    private static final String token = "HELLOIAMATOKENHOWABOUTYOU";

    private PlaylistController playlistController;
    private User user;
    private PlaylistMapperDTO playlistMapperDTO;
    private TrackMapperDTO trackMapperDTO;

    private SessionService sessionServiceMock;
    private IPlaylistDAO playlistDAOMock;
    private ITrackDAO trackDAOMock;

    @BeforeEach
    public void setup() {
        playlistController = new PlaylistController();
        playlistMapperDTO = new PlaylistMapperDTO();
        trackMapperDTO = new TrackMapperDTO();
        user = new User(1, "thijmen", "dadada");

        sessionServiceMock = mock(SessionService.class);
        playlistDAOMock = mock(IPlaylistDAO.class);
        trackDAOMock = mock(ITrackDAO.class);

        playlistController.setSessionService(sessionServiceMock);
        playlistController.setPlaylistMapperDTO(playlistMapperDTO);
        playlistController.setPlaylistDAO(playlistDAOMock);
        playlistController.setTrackDAO(trackDAOMock);
        playlistController.setTrackMapperDTO(trackMapperDTO);
    }

    @Test
    public void createPlaylist() throws SpotitubeException, EntityNotFoundException {
        int expectedStatusCode = 201;
        List<Playlist> playlists = new ArrayList<>() {{
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
        }};
        Playlist newPlaylist = new Playlist(1, "test", user.getUsername(), new ArrayList<>(), 100);
        PlaylistDTO submittedPlaylist = new PlaylistDTO(1, "test", true, new ArrayList<>());

        when(sessionServiceMock.getUser(token)).thenReturn(user);
        IPlaylistDAO spy = spy(playlistDAOMock);
        doNothing().when(spy).create(submittedPlaylist.name, user.getId());
        playlists.add(newPlaylist);
        PlaylistsDTO expectedPlaylists = playlistMapperDTO.mapPlaylistsToDTO(playlists, user.getUsername());

        Response response = playlistController.createPlaylist(token, submittedPlaylist);

        assertEquals(expectedStatusCode, response.getStatus());
        assertTrue(expectedPlaylists.playlists.get(3).owner);
    }

    @Test
    public void createTrackInPlaylistTest() throws SpotitubeException {
        int expectedStatusCode = 201;
        int playlistId = 1;
        List<Track> tracks = new ArrayList<>() {{
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
        }};
        Track newTrack = new Track(1, "test", "test", "test", 1, "test", "test", "test", 1, false);
        TrackDTO submittedTrack = new TrackDTO(1, "test", "test", 1, "test", 1, "test", "test", false);

        ITrackDAO spy = spy(trackDAOMock);
        doNothing().when(spy).addToPlaylist(submittedTrack.id, playlistId, submittedTrack.offlineAvailable);
        tracks.add(newTrack);
        when(trackDAOMock.getTracksInPlaylist(playlistId)).thenReturn(tracks);
        TracksDTO expectedTracks = trackMapperDTO.mapTracksToDTO(tracks);

        Response response = playlistController.createTrackInPlaylist(playlistId, submittedTrack);

        assertEquals(expectedStatusCode, response.getStatus());
        assertEquals(expectedTracks.tracks.get(3).title, submittedTrack.title);
    }

    @Test
    public void getAllPlaylistsTest() throws EntityNotFoundException, SpotitubeException {
        int expectedStatusCode = 200;
        List<Playlist> playlists = new ArrayList<>() {{
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
        }};
        PlaylistsDTO expectedPlaylists = playlistMapperDTO.mapPlaylistsToDTO(playlists, user.getUsername());

        when(playlistDAOMock.getAll()).thenReturn(playlists);
        when(sessionServiceMock.getUser(token)).thenReturn(user);

        Response response = playlistController.getAllPlaylists(token);
        assertEquals(expectedStatusCode, response.getStatus());
        assertEquals(expectedPlaylists.playlists.get(2).name, playlists.get(2).getName());
    }

    @Test
    public void getPlaylistTracksTest() throws SpotitubeException {
        int expectedStatusCode = 200;
        int playlistId = 2;
        List<Track> tracks = new ArrayList<>() {{
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
        }};
        TracksDTO expectedTracks = trackMapperDTO.mapTracksToDTO(tracks);

        when(trackDAOMock.getTracksInPlaylist(playlistId)).thenReturn(tracks);
        Response response = playlistController.getPlaylistTracks(playlistId);

        assertEquals(expectedStatusCode, response.getStatus());
        assertEquals(expectedTracks.tracks.get(2).title, tracks.get(2).getTitle());
    }

    @Test
    public void updatePlaylistTest() throws EntityNotFoundException, SpotitubeException {
        int expectedStatusCode = 200;
        int playlistId = 2;
        List<Playlist> playlists = new ArrayList<>() {{
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
        }};
        PlaylistDTO submittedPlaylist = new PlaylistDTO(1, "test", true, new ArrayList<>());
        Playlist expectedPlaylist = new Playlist(
                submittedPlaylist.id,
                submittedPlaylist.name,
                user.getUsername(),
                new ArrayList<>(),
                1
        );

        when(sessionServiceMock.getUser(token)).thenReturn(user);
        IPlaylistDAO spy = spy(playlistDAOMock);
        doNothing().when(spy).update(submittedPlaylist.name, playlistId, user.getId());
        playlists.set(playlistId - 1, expectedPlaylist);
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        PlaylistsDTO expectedPlaylists = playlistMapperDTO.mapPlaylistsToDTO(playlists, user.getUsername());
        Response response = playlistController.updatePlaylist(token, playlistId, submittedPlaylist);

        assertEquals(expectedStatusCode, response.getStatus());
        assertEquals(expectedPlaylists.playlists.get(1).name, expectedPlaylist.getName());
    }

    @Test
    public void deletePlaylistTest() throws EntityNotFoundException, SpotitubeException {
        int expectedStatusCode = 200;
        int playlistId = 2;
        List<Playlist> playlists = new ArrayList<>() {{
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
        }};

        when(sessionServiceMock.getUser(token)).thenReturn(user);
        IPlaylistDAO spy = spy(playlistDAOMock);
        doNothing().when(spy).delete(playlistId, user.getId());
        playlists.remove(playlistId - 1);
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        PlaylistsDTO expectedPlaylists = playlistMapperDTO.mapPlaylistsToDTO(playlists, user.getUsername());
        Response response = playlistController.deletePlaylist(token, playlistId);

        assertEquals(expectedStatusCode, response.getStatus());
        assertEquals(2, expectedPlaylists.playlists.size());
    }

    @Test
    public void deleteTrackFromPlaylistTest() throws SpotitubeException {
        int expectedStatusCode = 200;
        int playlistId = 2;
        int trackId = 2;
        List<Track> tracks = new ArrayList<>() {{
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
        }};

        ITrackDAO spy = spy(trackDAOMock);
        doNothing().when(spy).deleteFromPlaylist(trackId, playlistId);
        tracks.remove(trackId - 1);
        when(trackDAOMock.getTracksInPlaylist(playlistId)).thenReturn(tracks);
        TracksDTO expectedTracks = trackMapperDTO.mapTracksToDTO(tracks);
        Response response = playlistController.deleteTrackFromPlaylist(playlistId, trackId);

        assertEquals(expectedStatusCode, response.getStatus());
        assertEquals(2, expectedTracks.tracks.size());
    }
}
