package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/atl";
    private static final String user = "root";
    private static final String pass = "1234";
    private static DBConnection instance;

    public DBConnection() throws SQLException {
        connection = DriverManager.getConnection(url, user, pass);
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
