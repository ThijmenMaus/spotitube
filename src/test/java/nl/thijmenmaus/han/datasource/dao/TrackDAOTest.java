/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.dao;

import nl.thijmenmaus.han.datasource.IConnectionFactory;
import nl.thijmenmaus.han.datasource.dao.track.TrackDAO;
import nl.thijmenmaus.han.domain.Playlist;
import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.mapper.dao.TrackMapperDAO;
import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackDAOTest {
    private IConnectionFactory connectionFactory;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private TrackDAO trackDAO;
    private TrackMapperDAO trackMapperDAO;

    @BeforeEach
    public void setup() {
        connectionFactory = mock(IConnectionFactory.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        result = mock(ResultSet.class);
        trackMapperDAO = mock(TrackMapperDAO.class);

        trackDAO = new TrackDAO();
        trackDAO.setConnectionFactory(connectionFactory);
        trackDAO.setTrackMapperDAO(trackMapperDAO);
    }

    @Test
    public void addTrackToPlaylistTest() throws SQLException {
        String expectedQuery = "INSERT INTO track_in_playlist (playlist_id, track_id, available_offline) VALUES (?, ?, ?)";
        Playlist playlist = DataMocker.mockPlaylist();
        Track track = DataMocker.mockTrack();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);

        trackDAO.addToPlaylist(track.getId(), playlist.getId(), track.isAvailableOffline());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).setInt(1, playlist.getId());
        verify(statement).setInt(2, track.getId());
        verify(statement).setBoolean(3, track.isAvailableOffline());
        verify(statement).execute();
    }

    @Test
    public void getTracksInPlaylistTest() throws SQLException {
        String expectedQuery = "SELECT t.*, s.album, v.publication_date, v.description, tp.available_offline FROM track t LEFT JOIN song s ON s.track_id = t.id LEFT JOIN video v ON v.track_id = t.id LEFT JOIN track_in_playlist tp ON tp.track_id = t.id WHERE tp.playlist_id = ?";
        Playlist playlist = DataMocker.mockPlaylist();
        List<Track> expectedTracks = new ArrayList<>() {{
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
        }};

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);
        when(result.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(trackMapperDAO.mapEntityToDomain(result)).thenReturn(expectedTracks.get(0)).thenReturn(expectedTracks.get(1)).thenReturn(expectedTracks.get(2));

        List<Track> actualTracks = trackDAO.getTracksInPlaylist(playlist.getId());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).executeQuery();
        assertEquals(expectedTracks.get(0).getId(), actualTracks.get(0).getId());
        assertEquals(expectedTracks.get(1).getId(), actualTracks.get(1).getId());
        assertEquals(expectedTracks.get(2).getId(), actualTracks.get(2).getId());
    }

    @Test
    public void getTracksNotInPlaylistTest() throws SQLException {
        String expectedQuery = "SELECT temp.id, t.title, t.performer, t.duration, t.url, t.playcount, s.album, v.publication_date, v.description, MAX(tip.available_offline) as available_offline FROM (SELECT DISTINCT track.id FROM track LEFT JOIN track_in_playlist tip ON track.id = tip.track_id WHERE track.id NOT IN (SELECT track_id FROM track_in_playlist WHERE track_in_playlist.playlist_id = ?)) AS temp INNER JOIN track t ON t.id = temp.id LEFT JOIN video v ON t.id = v.track_id LEFT JOIN song s ON t.id = s.track_id LEFT JOIN track_in_playlist tip ON t.id = tip.track_id GROUP BY temp.id, t.title, t.performer, t.duration, t.url, t.playcount, album, v.publication_date, v.description";
        Playlist playlist = DataMocker.mockPlaylist();
        List<Track> expectedTracks = new ArrayList<>() {{
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
            add(DataMocker.mockTrack());
        }};

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);
        when(result.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(trackMapperDAO.mapEntityToDomain(result)).thenReturn(expectedTracks.get(0)).thenReturn(expectedTracks.get(1)).thenReturn(expectedTracks.get(2));

        List<Track> actualTracks = trackDAO.getTracksNotInPlaylist(playlist.getId());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).executeQuery();
        assertEquals(expectedTracks.get(0).getId(), actualTracks.get(0).getId());
        assertEquals(expectedTracks.get(1).getId(), actualTracks.get(1).getId());
        assertEquals(expectedTracks.get(2).getId(), actualTracks.get(2).getId());
    }

    @Test
    public void deleteTrackFromPlaylistTest() throws SQLException {
        String expectedQuery = "DELETE FROM track_in_playlist WHERE track_id = ? AND playlist_id = ?";
        Track track = DataMocker.mockTrack();
        Playlist playlist = DataMocker.mockPlaylist();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);

        trackDAO.deleteFromPlaylist(track.getId(),playlist.getId());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).setInt(1, track.getId());
        verify(statement).setInt(2, playlist.getId());
        verify(statement).execute();
    }
}
