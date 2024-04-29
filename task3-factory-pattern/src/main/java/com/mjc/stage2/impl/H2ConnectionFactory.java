package com.mjc.stage2.impl;

import com.mjc.stage2.ConnectionFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2ConnectionFactory implements ConnectionFactory {
    private static final String PROP_FILE = "h2database.properties";
    private Connection connection = null;

    @Override
    public Connection createConnection() {
        if (connection == null) {
            Properties properties = getProps();
            String driver = properties.getProperty("jdbc_driver");
            String url = properties.getProperty("db_url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
                return connection;
            }catch (ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }
        }

        return connection;
    }

    private Properties getProps() {
        Properties properties = new Properties();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("Properties file couldn't be loaded: "  + PROP_FILE);
            }

            properties.load(inputStream);
        }catch (IOException e){
            throw new RuntimeException("Error loading file: " + PROP_FILE, e);
        }

        return properties;
    }
}

