/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest;

import nl.thijmenmaus.han.common.exception.SpotitubeException;
import nl.thijmenmaus.han.common.filter.IsAuthorized;
import nl.thijmenmaus.han.datasource.dao.track.ITrackDAO;
import nl.thijmenmaus.han.datasource.dao.track.TrackDAO;
import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.mapper.dto.TrackMapperDTO;
import nl.thijmenmaus.han.rest.dto.TracksDTO;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tracks")
public class TrackController {
    private ITrackDAO trackDAO;
    private TrackMapperDTO trackMapperDTO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthorized
    public Response getTracks(@QueryParam("forPlaylist") int playlistId) throws SpotitubeException {
        try {
            List<Track> tracks = trackDAO.getTracksNotInPlaylist(playlistId);

            TracksDTO tracksDTO = trackMapperDTO.mapTracksToDTO(tracks);

            return Response.ok().entity(tracksDTO).build();
        } catch (Exception exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het ophalen van de tracks..");
        }
    }

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    @Inject
    public void setTrackMapperDTO(TrackMapperDTO trackMapperDTO) {
        this.trackMapperDTO = trackMapperDTO;
    }
}
