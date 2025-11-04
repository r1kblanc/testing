package com.example.tictactoe;

import java.nio.file.*;
import java.sql.*;

public class DatabaseUtil {

    private static final String DB_PATH = "src/main/resources/db/Database1.accdb"; // full absolute path!

    public static Connection getConnection() {
        try {
            // Always use absolute path to avoid duplicate databases
            Path db = Paths.get(DB_PATH);
            String url = "jdbc:ucanaccess://" + db.toString().replace("\\", "/");
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connected to: " + db.toAbsolutePath());
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
