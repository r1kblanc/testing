package com.example.tictactoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        try {
            // Load the UCanAccess driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("UCanAccess driver not found", e);
        }

        String url = "jdbc:ucanaccess://src/main/resources/db/Database1.accdb";

        // Return connection (reuse if already open)
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(url);
        }

        return conn;
    }
}
