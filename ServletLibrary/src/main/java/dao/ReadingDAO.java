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
            String sqlQuery = "SELECT * FROM reading WHERE id_user = ? AND id_book = ?";

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, userId);
            pstmt.setLong(2, bookId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Reading reading = new Reading(rs.getLong("id_user"), rs.getLong("id_book"));
                reads.add(reading);
            }
            connection.close();
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
            PreparedStatement pstmt = connection.prepareStatement(sql_query);
            pstmt.executeUpdate();
            connection.close();
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

            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, userId);
            pstmt.setLong(2, bookId);
            pstmt.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}