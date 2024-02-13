package com.mdbytes.repository.implementation;


import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Dotenv dotenv = Dotenv.load();
        Class.forName(dotenv.get("MYSQL_DRIVER"));
        return DriverManager.getConnection(dotenv.get("MYSQL_URL"), dotenv.get("MYSQL_USER"), dotenv.get("MYSQL_PASSWORD"));
    }

    // Run to test connection.
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database db = new Database();
        Connection conn = db.getConnection();
        System.out.println(conn.toString());
        conn.close();
    }
}
