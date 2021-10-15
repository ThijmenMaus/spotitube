/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest;

import nl.thijmenmaus.han.common.exception.EntityNotFoundException;
import nl.thijmenmaus.han.common.exception.SpotitubeException;
import nl.thijmenmaus.han.common.filter.IsAuthorized;
import nl.thijmenmaus.han.datasource.playlist.IPlaylistDAO;
import nl.thijmenmaus.han.datasource.track.ITrackDAO;
import nl.thijmenmaus.han.domain.Playlist;
import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.mapper.dto.PlaylistMapperDTO;
import nl.thijmenmaus.han.mapper.dto.TrackMapperDTO;
import nl.thijmenmaus.han.rest.dto.PlaylistDTO;
import nl.thijmenmaus.han.rest.dto.PlaylistsDTO;
import nl.thijmenmaus.han.rest.dto.TrackDTO;
import nl.thijmenmaus.han.rest.dto.TracksDTO;
import nl.thijmenmaus.han.service.SessionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/playlists")
public class PlaylistController {
    private SessionService sessionService;
    private PlaylistMapperDTO playlistMapperDTO;
    private TrackMapperDTO trackMapperDTO;
    private IPlaylistDAO playlistDAO;
    private ITrackDAO trackDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthorized
    public Response createPlaylist(@QueryParam("token") String token, PlaylistDTO dto) throws SpotitubeException {
        try {
            User user = sessionService.getUser(token);
            playlistDAO.create(dto.name, user.getId());
            List<Playlist> playlists = playlistDAO.getAll();
            PlaylistsDTO playlistsDTO = playlistMapperDTO.mapPlaylistsToDTO(playlists, user.getUsername());

            return Response.status(Response.Status.CREATED).entity(playlistsDTO).build();
        } catch (EntityNotFoundException | InternalServerErrorException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het aanmaken van de playlist..");
        }
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthorized
    public Response createTrackInPlaylist(@PathParam("id") int playlistId, TrackDTO dto) throws SpotitubeException {
        try {
            trackDAO.addToPlaylist(dto.id, playlistId, dto.offlineAvailable);
            List<Track> tracks = trackDAO.getTracksInPlaylist(playlistId);
            TracksDTO tracksDTO = trackMapperDTO.mapTracksToDTO(tracks);

            return Response.status(Response.Status.CREATED).entity(tracksDTO).build();
        } catch (InternalServerErrorException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het toevoegen van dit nummer..");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthorized
    public Response getAllPlaylists(@QueryParam("token") String token) throws SpotitubeException {
        try {
            List<Playlist> playlists = playlistDAO.getAll();
            User user = sessionService.getUser(token);
            PlaylistsDTO playlistsDTO = playlistMapperDTO.mapPlaylistsToDTO(playlists, user.getUsername());

            return Response.ok().entity(playlistsDTO).build();
        } catch (InternalServerErrorException | EntityNotFoundException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het ophalen van de playlists..");
        }
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthorized
    public Response getPlaylistTracks(@PathParam("id") int id) throws SpotitubeException {
        try {
            List<Track> tracks = trackDAO.getTracksInPlaylist(id);
            TracksDTO tracksDTO = trackMapperDTO.mapTracksToDTO(tracks);

            return Response.ok().entity(tracksDTO).build();
        } catch (InternalServerErrorException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het ophalen van de playlists..");
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthorized
    public Response updatePlaylist(@QueryParam("token") String token, @PathParam("id") int id, PlaylistDTO dto) throws SpotitubeException {
        try {
            User user = sessionService.getUser(token);
            playlistDAO.update(dto.name, id, user.getId());
            List<Playlist> playlists = playlistDAO.getAll();
            PlaylistsDTO playlistsDTO = playlistMapperDTO.mapPlaylistsToDTO(playlists, user.getUsername());

            return Response.ok().entity(playlistsDTO).build();
        } catch (InternalServerErrorException | EntityNotFoundException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het wijzigen van de playlist..");
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthorized
    public Response deletePlaylist(@QueryParam("token") String token, @PathParam("id") int id) throws SpotitubeException {
        try {
            User user = sessionService.getUser(token);
            playlistDAO.delete(id, user.getId());
            List<Playlist> playlists = playlistDAO.getAll();
            PlaylistsDTO playlistsDTO = playlistMapperDTO.mapPlaylistsToDTO(playlists, user.getUsername());

            return Response.ok().entity(playlistsDTO).build();
        } catch (InternalServerErrorException | EntityNotFoundException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het verwijderen van de playlist..");
        }
    }

    @DELETE
    @Path("/{pid}/tracks/{tid}")
    @Produces(MediaType.APPLICATION_JSON)
    @IsAuthorized
    public Response deleteTrackFromPlaylist(@PathParam("pid") int playlistId, @PathParam("tid") int trackId) throws SpotitubeException {
        try {
            trackDAO.deleteFromPlaylist(trackId, playlistId);
            List<Track> tracks = trackDAO.getTracksInPlaylist(playlistId);
            TracksDTO tracksDTO = trackMapperDTO.mapTracksToDTO(tracks);

            return Response.ok().entity(tracksDTO).build();
        } catch (InternalServerErrorException exception) {
            throw new SpotitubeException("Er is iets misgegaan tijdens het verwijderen van dit nummer uit de playlist..");
        }
    }

    @Inject
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Inject
    public void setPlaylistMapperDTO(PlaylistMapperDTO playlistMapperDTO) {
        this.playlistMapperDTO = playlistMapperDTO;
    }

    @Inject
    public void setTrackMapperDTO(TrackMapperDTO trackMapperDTO) {
        this.trackMapperDTO = trackMapperDTO;
    }

    @Inject
    public void setPlaylistDAO(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }
}
