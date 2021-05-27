/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.domain;

public class Track {
    private int id;
    private String title;
    private String performer;
    private String url;
    private int duration;
    private String album;
    private String publicationDate;
    private String description;
    private int playcount;
    private boolean availableOffline;

    public Track() {
        // Empty constructor to prevent this weird Java Dependency Injection from crashing
    }

    public Track(int id, String title, String performer, String url, int duration, String album, String publicationDate, String description, int playcount, boolean availableOffline) {
        this.id = id;
        this.title = title;
        this.performer = performer;
        this.url = url;
        this.duration = duration;
        this.album = album;
        this.publicationDate = publicationDate;
        this.description = description;
        this.playcount = playcount;
        this.availableOffline = availableOffline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public boolean isAvailableOffline() {
        return availableOffline;
    }

    public void setAvailableOffline(boolean availableOffline) {
        this.availableOffline = availableOffline;
    }
}
