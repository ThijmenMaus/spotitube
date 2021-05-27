/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.domain;

import nl.thijmenmaus.han.test_util.DataMocker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrackTest {
    @Test
    public void emptyInitalizationTest() {
        // Arrange & Act
        Track track = new Track();
        // Assert
        assertNotNull(track);
    }

    @Test
    public void gettersAndSettersTest() {
        // Arrange
        Track fake = DataMocker.mockTrack();
        Track actual = new Track();
        // Act
        actual.setId(fake.getId());
        actual.setTitle(fake.getTitle());
        actual.setPerformer(fake.getPerformer());
        actual.setUrl(fake.getUrl());
        actual.setDuration(fake.getDuration());
        actual.setAlbum(fake.getAlbum());
        actual.setPublicationDate(fake.getPublicationDate());
        actual.setDescription(fake.getDescription());
        actual.setPlaycount(fake.getPlaycount());
        actual.setAvailableOffline(fake.isAvailableOffline());
        // Assert
        assertEquals(actual.getId(), fake.getId());
        assertEquals(actual.getTitle(), fake.getTitle());
        assertEquals(actual.getPerformer(), fake.getPerformer());
        assertEquals(actual.getUrl(), fake.getUrl());
        assertEquals(actual.getDuration(), fake.getDuration());
        assertEquals(actual.getAlbum(), fake.getAlbum());
        assertEquals(actual.getPublicationDate(), fake.getPublicationDate());
        assertEquals(actual.getDescription(), fake.getDescription());
        assertEquals(actual.getPlaycount(), fake.getPlaycount());
        assertEquals(actual.isAvailableOffline(), fake.isAvailableOffline());
    }
}
