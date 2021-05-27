/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.test_util;

import com.github.javafaker.Faker;
import nl.thijmenmaus.han.domain.Playlist;
import nl.thijmenmaus.han.domain.Session;
import nl.thijmenmaus.han.domain.Track;
import nl.thijmenmaus.han.domain.User;
import nl.thijmenmaus.han.rest.dto.UserDTO;

import java.util.ArrayList;
import java.util.Locale;

public class DataMocker {
    private static final Faker faker = new Faker(new Locale("nl"));

    public static Playlist mockPlaylist() {
        return new Playlist(
                faker.number().numberBetween(10, 100),
                faker.color().name(),
                faker.name().firstName(),
                new ArrayList<>(),
                faker.number().numberBetween(200, 900)
        );
    }

    public static User mockUser() {
        return new User(
                faker.number().numberBetween(10, 100),
                faker.name().username(),
                faker.lorem().word()
        );
    }

    public static UserDTO mockUserDTO() {
        UserDTO user = new UserDTO();
        user.user = faker.name().username();
        user.password = faker.lorem().word();
        return user;
    }

    public static Session mockSession() {
        return new Session(
                faker.name().username(),
                faker.phoneNumber().cellPhone()
        );
    }

    public static Track mockTrack() {
        return new Track(
                faker.number().numberBetween(10, 100),
                faker.pokemon().name(),
                faker.artist().name(),
                "https://google.com/",
                faker.number().numberBetween(200, 400),
                faker.starTrek().character(),
                faker.date().toString(),
                faker.harryPotter().spell(),
                faker.number().numberBetween(100, 10000),
                faker.bool().bool()
        );
    }
}
