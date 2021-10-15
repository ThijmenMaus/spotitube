/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.track;

import nl.thijmenmaus.han.datasource.IConnectionFactory;
import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.mapper.dao.TrackMapperDAO;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements ITrackDAO {
    private static final String ADD_TO_PLAYLIST_QUERY = "INSERT INTO track_in_playlist (playlist_id, track_id, available_offline) VALUES (?, ?, ?)";
    private static final String GET_IN_PLAYLIST_QUERY = "SELECT t.*, s.album, v.publication_date, v.description, tp.available_offline FROM track t LEFT JOIN song s ON s.track_id = t.id LEFT JOIN video v ON v.track_id = t.id LEFT JOIN track_in_playlist tp ON tp.track_id = t.id WHERE tp.playlist_id = ?";
    private static final String GET_NOT_IN_PLAYLIST_QUERY = "SELECT temp.id, t.title, t.performer, t.duration, t.url, t.playcount, s.album, v.publication_date, v.description, MAX(tip.available_offline) as available_offline FROM (SELECT DISTINCT track.id FROM track LEFT JOIN track_in_playlist tip ON track.id = tip.track_id WHERE track.id NOT IN (SELECT track_id FROM track_in_playlist WHERE track_in_playlist.playlist_id = ?)) AS temp INNER JOIN track t ON t.id = temp.id LEFT JOIN video v ON t.id = v.track_id LEFT JOIN song s ON t.id = s.track_id LEFT JOIN track_in_playlist tip ON t.id = tip.track_id GROUP BY temp.id, t.title, t.performer, t.duration, t.url, t.playcount, album, v.publication_date, v.description";
    private static final String DELETE_FROM_PLAYLIST_QUERY = "DELETE FROM track_in_playlist WHERE track_id = ? AND playlist_id = ?";

    private IConnectionFactory connectionFactory;
    private TrackMapperDAO trackMapperDAO;

    @Override
    public void addToPlaylist(int trackId, int playlistId, boolean availableOffline) throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(ADD_TO_PLAYLIST_QUERY);

            statement.setInt(1, playlistId);
            statement.setInt(2, trackId);
            statement.setBoolean(3, availableOffline);

            statement.execute();
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Override
    public List<Track> getTracksInPlaylist(int playlistId) throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            List<Track> tracks = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(GET_IN_PLAYLIST_QUERY);

            statement.setInt(1, playlistId);

            ResultSet result = statement.executeQuery();

            while (result.next()) tracks.add(trackMapperDAO.mapEntityToDomain(result));

            return tracks;
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Override
    public List<Track> getTracksNotInPlaylist(int playlistId) throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            List<Track> tracks = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(GET_NOT_IN_PLAYLIST_QUERY);

            statement.setInt(1, playlistId);

            ResultSet result = statement.executeQuery();

            while (result.next()) tracks.add(trackMapperDAO.mapEntityToDomain(result));

            return tracks;
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Override
    public void deleteFromPlaylist(int trackId, int playlistId) throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_FROM_PLAYLIST_QUERY);

            statement.setInt(1, trackId);
            statement.setInt(2, playlistId);

            statement.execute();
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Inject
    public void setConnectionFactory(IConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Inject
    public void setTrackMapperDAO(TrackMapperDAO trackMapperDAO) {
        this.trackMapperDAO = trackMapperDAO;
    }
}
