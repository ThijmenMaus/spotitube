/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dao;

import nl.thijmenmaus.han.domain.Track;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackMapperDAO implements IMapperDAO<Track> {
    @Override
    public Track mapEntityToDomain(ResultSet entity) throws SQLException {
        return new Track(
                entity.getInt("id"),
                entity.getString("title"),
                entity.getString("performer"),
                entity.getString("url"),
                entity.getInt("duration"),
                entity.getString("album"),
                entity.getString("publication_date"),
                entity.getString("description"),
                entity.getInt("playcount"),
                entity.getBoolean("available_offline")
        );
    }
}
