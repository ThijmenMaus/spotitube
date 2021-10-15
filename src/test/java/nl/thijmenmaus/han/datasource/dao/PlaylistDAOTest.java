/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.dao;

import nl.thijmenmaus.han.datasource.IConnectionFactory;
import nl.thijmenmaus.han.datasource.playlist.PlaylistDAO;
import nl.thijmenmaus.han.domain.Playlist;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.mapper.dao.PlaylistMapperDAO;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

public class PlaylistDAOTest {
    private IConnectionFactory connectionFactory;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;

    private PlaylistDAO playlistDAO;
    private PlaylistMapperDAO playlistMapperDAO;

    @BeforeEach
    public void setup() {
        connectionFactory = mock(IConnectionFactory.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        result = mock(ResultSet.class);
        playlistMapperDAO = mock(PlaylistMapperDAO.class);

        playlistDAO = new PlaylistDAO();
        playlistDAO.setConnectionFactory(connectionFactory);
        playlistDAO.setPlaylistMapperDAO(playlistMapperDAO);
    }

    @Test
    public void getAllPlaylistsTest() throws SQLException {
        String expectedQuery = "SELECT DISTINCT p.id AS id, p.name AS name, u.username AS owner, (SELECT SUM(duration) FROM track t INNER JOIN track_in_playlist AS tp ON t.id = tp.track_id WHERE p.id = tp.playlist_id) AS length FROM playlist AS p LEFT OUTER JOIN track_in_playlist AS tp ON p.id = tp.playlist_id LEFT JOIN user u ON p.user_id = u.id";
        List<Playlist> expectedPlaylists = new ArrayList<>() {{
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
        }};

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);
        when(result.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        when(playlistMapperDAO.mapEntityToDomain(result)).thenReturn(expectedPlaylists.get(0)).thenReturn(expectedPlaylists.get(1)).thenReturn(expectedPlaylists.get(2));

        List<Playlist> actualPlaylists = playlistDAO.getAll();

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).executeQuery();
        assertEquals(expectedPlaylists.get(0).getId(), actualPlaylists.get(0).getId());
        assertEquals(expectedPlaylists.get(1).getId(), actualPlaylists.get(1).getId());
        assertEquals(expectedPlaylists.get(2).getId(), actualPlaylists.get(2).getId());
    }

    @Test
    public void getAllPlaylistsFailureTest() throws SQLException {
        String expectedQuery = "SELECT DISTINCT p.id AS id, p.name AS name, u.username AS owner, (SELECT SUM(duration) FROM track t INNER JOIN track_in_playlist AS tp ON t.id = tp.track_id WHERE p.id = tp.playlist_id) AS length FROM playlist AS p LEFT OUTER JOIN track_in_playlist AS tp ON p.id = tp.playlist_id LEFT JOIN user u ON p.user_id = u.id";
        List<Playlist> expectedPlaylists = new ArrayList<>() {{
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
            add(DataMocker.mockPlaylist());
        }};

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);
        when(result.next()).thenReturn(false);

        List<Playlist> actualPlaylists = playlistDAO.getAll();

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).executeQuery();
        assertNotEquals(expectedPlaylists.size(), actualPlaylists.size());
    }

    @Test
    public void createPlaylistTest() throws SQLException {
        String expectedQuery = "INSERT INTO playlist (name, user_id) VALUES (?, ?)";
        Playlist playlist = DataMocker.mockPlaylist();
        User owner = DataMocker.mockUser();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);

        playlistDAO.create(playlist.getName(), owner.getId());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).setString(1, playlist.getName());
        verify(statement).setInt(2, owner.getId());
        verify(statement).execute();
    }

    @Test
    public void deletePlaylistTest() throws SQLException {
        String expectedQuery = "DELETE FROM playlist WHERE id = ? AND user_id = ?";
        Playlist playlist = DataMocker.mockPlaylist();
        User owner = DataMocker.mockUser();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);

        playlistDAO.delete(playlist.getId(), owner.getId());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).setInt(1, playlist.getId());
        verify(statement).setInt(2, owner.getId());
        verify(statement).execute();
    }

    @Test
    public void updatePlaylistTest() throws SQLException {
        String expectedQuery = "UPDATE playlist SET name = ? WHERE id = ? AND user_id = ?";
        Playlist playlist = DataMocker.mockPlaylist();
        User owner = DataMocker.mockUser();
        String newPlaylistName = DataMocker.mockPlaylist().getName();

        when(connectionFactory.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(expectedQuery)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(result);

        playlistDAO.update(newPlaylistName, playlist.getId(), owner.getId());

        verify(connection).prepareStatement(expectedQuery);
        verify(statement).setString(1, newPlaylistName);
        verify(statement).setInt(2, playlist.getId());
        verify(statement).setInt(3, owner.getId());
        verify(statement).execute();
    }
}
