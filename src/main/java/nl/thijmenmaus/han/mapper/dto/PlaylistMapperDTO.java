/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.mapper.dto;

import nl.thijmenmaus.han.domain.Playlist;
import nl.thijmenmaus.han.rest.dto.PlaylistDTO;
import nl.thijmenmaus.han.rest.dto.PlaylistsDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistMapperDTO {
    public PlaylistDTO mapPlaylistToDTO(Playlist playlist, String username) {
        return new PlaylistDTO(
                playlist.getId(),
                playlist.getName(),
                playlist.getOwner().equals(username),
                new ArrayList<>()
        );
    }

    public PlaylistsDTO mapPlaylistsToDTO(List<Playlist> playlists, String username) {
        PlaylistsDTO playlistsDTO = new PlaylistsDTO();

        playlistsDTO.playlists = new ArrayList<>();
        playlistsDTO.length = 0;

        for (Playlist p : playlists) {
            playlistsDTO.playlists.add(mapPlaylistToDTO(p, username));
            playlistsDTO.length += p.getLength();
        }

        return playlistsDTO;
    }
}
