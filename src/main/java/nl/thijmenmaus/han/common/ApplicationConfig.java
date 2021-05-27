/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.common;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ApplicationConfig {
    private Properties properties = new Properties();

    public ApplicationConfig() {
        try {
            properties.load(Objects.requireNonNull(ApplicationConfig.class.getClassLoader().getResourceAsStream("application.properties")));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getSecret() {
        return properties.getProperty("app.secret");
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }
}
