/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class ConnectionFactory implements IConnectionFactory {

    private final Properties properties;

    public ConnectionFactory() {
        properties = new Properties();
        initializeProperties();
        initializeDriver();
    }

    private void initializeProperties() {
        try {
            properties.load(Objects.requireNonNull(ConnectionFactory.class.getClassLoader().getResourceAsStream("database.properties")));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initializeDriver() {
        try {
            Class.forName(properties.getProperty("db.driver"));
        } catch (ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(String.format(
                    properties.getProperty("db.url").concat("&user=%s&password=%s"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")
            ));
        } catch (SQLException exception) {
            throw new IllegalArgumentException("An error occured whilst trying to connect to the database!", exception);
        }
    }

    @Override
    public void setProperties(String url, String username, String password) {
        this.properties.setProperty("db.url", url);
        this.properties.setProperty("db.username", username);
        this.properties.setProperty("db.password", password);
    }
}