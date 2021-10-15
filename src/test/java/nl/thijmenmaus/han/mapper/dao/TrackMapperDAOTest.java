/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dao;

import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackMapperDAOTest {
    private TrackMapperDAO trackMapperDAO;

    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        trackMapperDAO = new TrackMapperDAO();
        resultSet = mock(ResultSet.class);
    }

    @Test
    public void mapDTOtoDomainTest() throws SQLException {
        Track expectedTrack = DataMocker.mockTrack();

        when(resultSet.getInt("id")).thenReturn(expectedTrack.getId());
        when(resultSet.getString("title")).thenReturn(expectedTrack.getTitle());
        when(resultSet.getString("performer")).thenReturn(expectedTrack.getPerformer());
        when(resultSet.getString("url")).thenReturn(expectedTrack.getUrl());
        when(resultSet.getInt("duration")).thenReturn(expectedTrack.getDuration());
        when(resultSet.getString("album")).thenReturn(expectedTrack.getAlbum());
        when(resultSet.getString("publication_date")).thenReturn(expectedTrack.getPublicationDate());
        when(resultSet.getString("description")).thenReturn(expectedTrack.getDescription());
        when(resultSet.getInt("playcount")).thenReturn(expectedTrack.getPlaycount());
        when(resultSet.getBoolean("available_offline")).thenReturn(expectedTrack.isAvailableOffline());

        Track actualTrack = trackMapperDAO.mapEntityToDomain(resultSet);

        assertEquals(actualTrack.getId(), expectedTrack.getId());
        assertEquals(actualTrack.getTitle(), expectedTrack.getTitle());
        assertEquals(actualTrack.getPerformer(), expectedTrack.getPerformer());
        assertEquals(actualTrack.getUrl(), expectedTrack.getUrl());
        assertEquals(actualTrack.getDuration(), expectedTrack.getDuration());
        assertEquals(actualTrack.getAlbum(), expectedTrack.getAlbum());
        assertEquals(actualTrack.getPublicationDate(), expectedTrack.getPublicationDate());
        assertEquals(actualTrack.getDescription(), expectedTrack.getDescription());
        assertEquals(actualTrack.getPlaycount(), expectedTrack.getPlaycount());
        assertEquals(actualTrack.isAvailableOffline(), expectedTrack.isAvailableOffline());
    }
}
