/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.playlist;

import nl.thijmenmaus.han.domain.Playlist;

import javax.ws.rs.InternalServerErrorException;
import java.util.List;

public interface IPlaylistDAO {
    void create(String name, int userId) throws InternalServerErrorException;

    List<Playlist> getAll() throws InternalServerErrorException;

    void update(String name, int id, int userId) throws InternalServerErrorException;

    void delete(int id, int userId) throws InternalServerErrorException;
}
