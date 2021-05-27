/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.rest.dto;

public class TrackDTO {
    public int id;
    public String title;
    public String performer;
    public int duration;
    public String album;
    public int playcount;
    public String publicationDate;
    public String description;
    public boolean offlineAvailable;

    public TrackDTO() {
        // Empty constructor to prevent this weird Java Dependency Injection from crashing
    }

    public TrackDTO(int id, String title, String performer, int duration, String album, int playcount, String publicationDate, String description, boolean offlineAvailable) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.duration = duration;
        this.album = album;
        this.playcount = playcount;
        this.publicationDate = publicationDate;
        this.description = description;
        this.offlineAvailable = offlineAvailable;
    }
}
