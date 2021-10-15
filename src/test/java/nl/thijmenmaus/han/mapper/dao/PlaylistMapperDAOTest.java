/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dao;

import nl.thijmenmaus.han.domain.Playlist;
import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistMapperDAOTest {
    private PlaylistMapperDAO playlistMapperDAO;

    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        playlistMapperDAO = new PlaylistMapperDAO();
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void mapDTOtoDomainTest() throws SQLException {
        Playlist expectedPlaylist = DataMocker.mockPlaylist();

        when(resultSet.getInt("id")).thenReturn(expectedPlaylist.getId());
        when(resultSet.getString("name")).thenReturn(expectedPlaylist.getName());
        when(resultSet.getString("owner")).thenReturn(expectedPlaylist.getOwner());
        when(resultSet.getInt("length")).thenReturn(expectedPlaylist.getLength());
        Playlist actualPlaylist = playlistMapperDAO.mapEntityToDomain(resultSet);

        assertEquals(actualPlaylist.getId(), expectedPlaylist.getId());
        assertEquals(actualPlaylist.getName(), expectedPlaylist.getName());
        assertEquals(actualPlaylist.getOwner(), expectedPlaylist.getOwner());
        assertEquals(actualPlaylist.getTracks().size(), expectedPlaylist.getTracks().size());
        assertEquals(actualPlaylist.getLength(), expectedPlaylist.getLength());
    }
}
