/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dao;

import nl.thijmenmaus.han.domain.Playlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistMapperDAO implements IMapperDAO<Playlist> {
    @Override
    public Playlist mapEntityToDomain(ResultSet entity) throws SQLException {
        return new Playlist(
                entity.getInt("id"),
                entity.getString("name"),
                entity.getString("owner"),
                new ArrayList<>(),
                entity.getInt("length")
        );
    }
}
