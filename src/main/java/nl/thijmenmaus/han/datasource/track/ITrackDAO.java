/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource.track;

import nl.thijmenmaus.han.domain.Track;

import javax.ws.rs.InternalServerErrorException;
import java.util.List;

public interface ITrackDAO {
    void addToPlaylist(int trackId, int playlistId, boolean availableOffline) throws InternalServerErrorException;

    List<Track> getTracksInPlaylist(int playlistId) throws InternalServerErrorException;

    List<Track> getTracksNotInPlaylist(int playlistId) throws InternalServerErrorException;

    void deleteFromPlaylist(int trackId, int playlistId) throws InternalServerErrorException;

}
