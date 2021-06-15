package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static ConnectionPool connectionPool = new ConnectionPool();

    private final String url = "jdbc:postgresql://localhost:5432/";
    private final String user = "newuser";
    private final String password = "";
    private final int MAX_CONNECTIONS = 8;

    private BlockingQueue<Connection> connections;

    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found");
            e.printStackTrace();
        }
        connections = new LinkedBlockingQueue<Connection>(MAX_CONNECTIONS);
        try {
            for(int i = 0; i < MAX_CONNECTIONS; ++i) {
                connections.put(DriverManager.getConnection(url,user,password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public Connection getConnection() throws InterruptedException, SQLException {
        Connection c = connections.take();
        if (c.isClosed()) {
            c = DriverManager.getConnection(url,user,password);
        }
        return c;
    }

    public void releaseConnection(Connection c) throws InterruptedException {
        connections.put(c);
    }
}