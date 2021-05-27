/*
 * Copyright (c) 2021
 *  • Thijmen G. Maus
 *  • https://thijmenmaus.nl/
 */

package nl.thijmenmaus.han.datasource;

import java.sql.Connection;

public interface IConnectionFactory {
    Connection getConnection();

    void setProperties(String url, String username, String password);
}
