/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.dao.playlist;

import nl.thijmenmaus.han.datasource.IConnectionFactory;
import nl.thijmenmaus.han.domain.Playlist;
import nl.thijmenmaus.han.mapper.dao.PlaylistMapperDAO;

import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO implements IPlaylistDAO {
    private static final String CREATE_QUERY = "INSERT INTO playlist (name, user_id) VALUES (?, ?)";
    private static final String GET_ALL_QUERY = "SELECT DISTINCT p.id AS id, p.name AS name, u.username AS owner, (SELECT SUM(duration) FROM track t INNER JOIN track_in_playlist AS tp ON t.id = tp.track_id WHERE p.id = tp.playlist_id) AS length FROM playlist AS p LEFT OUTER JOIN track_in_playlist AS tp ON p.id = tp.playlist_id LEFT JOIN user u ON p.user_id = u.id";
    private static final String UPDATE_QUERY = "UPDATE playlist SET name = ? WHERE id = ? AND user_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM playlist WHERE id = ? AND user_id = ?";

    private IConnectionFactory connectionFactory;
    private PlaylistMapperDAO playlistMapperDAO;

    @Override
    public void create(String name, int userId) throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_QUERY);

            statement.setString(1, name);
            statement.setInt(2, userId);

            statement.execute();
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Override
    public List<Playlist> getAll() throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            List<Playlist> playlists = new ArrayList<>();

            PreparedStatement statement = connection.prepareStatement(GET_ALL_QUERY);
            ResultSet result = statement.executeQuery();

            while (result.next()) playlists.add(playlistMapperDAO.mapEntityToDomain(result));

            return playlists;
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Override
    public void update(String name, int id, int userId) throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);

            statement.setString(1, name);
            statement.setInt(2, id);
            statement.setInt(3, userId);

            statement.execute();
        } catch (SQLException exception) {
            throw new InternalServerErrorException(exception);
        }
    }

    @Override
    public void delete(int id, int userId) throws InternalServerErrorException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);

            statement.setInt(1, id);
            statement.setInt(2, userId);

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
    public void setPlaylistMapperDAO(PlaylistMapperDAO playlistMapperDAO) {
        this.playlistMapperDAO = playlistMapperDAO;
    }
}
