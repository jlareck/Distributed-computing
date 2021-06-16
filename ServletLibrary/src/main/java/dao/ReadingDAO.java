package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.*;
import db.ConnectionPool;

public class ReadingDAO {
    static public ArrayList<Reading> getReadings(long userId, long bookId) {
        ArrayList<Reading> reads = new ArrayList<>();
        try {
            Connection connection = ConnectionPool.getConnectionPool().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            String sqlQuery = "SELECT * FROM reading WHERE id_user = ? AND id_book = ?";

            PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery);
            prepareStatement.setLong(1, userId);
            prepareStatement.setLong(2, bookId);
            ResultSet rs = prepareStatement.executeQuery();
            while (rs.next()) {
                Reading reading = new Reading(rs.getLong("id_user"), rs.getLong("id_book"));
                reads.add(reading);
            }
            ConnectionPool.getConnectionPool().releaseConnection(connection);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return reads;
    }

    static public boolean addReading(long userId, long bookId) {
        try {
            if (getReadings(userId, bookId).size() != 0) {
                return false;
            }
            Connection connection = ConnectionPool.getConnectionPool().getConnection();
            String sql_query = String.format("INSERT INTO reading (id_user, id_book) VALUES(%s, %s)",
                    userId, bookId, false);
            PreparedStatement prepareStatement = connection.prepareStatement(sql_query);
            prepareStatement.executeUpdate();
            ConnectionPool.getConnectionPool().releaseConnection(connection);
            return true;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public boolean deleteReading(long userId, long bookId) {
        try {
            if (getReadings(userId, bookId).size() == 0) {
                return false;
            }
            Connection connection = ConnectionPool.getConnectionPool().getConnection();
            String sqlQuery = "DELETE FROM reading WHERE id_user = ? AND id_book = ?";

            PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery);
            prepareStatement.setLong(1, userId);
            prepareStatement.setLong(2, bookId);
            prepareStatement.executeUpdate();
            ConnectionPool.getConnectionPool().releaseConnection(connection);
            return true;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}