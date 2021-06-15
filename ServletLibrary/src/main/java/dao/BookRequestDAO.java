package dao;

import model.BookRequest;
import db.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRequestDAO {
    static public ArrayList<BookRequest> getBookRequests(int userId, int bookId) {
        ArrayList<BookRequest> reqs = new ArrayList<>();
        try {
            Connection connection = ConnectionPool.getConnectionPool().getConnection();

            final String sqlQuery = "SELECT * FROM request_handler WHERE id_user = ? AND id_book = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1, userId);
            pstmt.setLong(2, bookId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                BookRequest book_request = new BookRequest(rs.getLong("id_user"), rs.getLong("id_book"),
                        rs.getBoolean("accepted"));
                reqs.add(book_request);
            }
            connection.close();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return reqs;
    }

    static public boolean addRequest(int userId, int bookId) {
        try {
            if (getBookRequests(userId, bookId).size() != 0) {
                return false;
            }
            Connection connection = ConnectionPool.getConnectionPool().getConnection();
            //final String sqlQuery = "INSERT INTO request_handler (?, ?, ?) VALUES()";
            String sqlQuery = String.format("INSERT INTO request_handler (id_user, id_book, accepted) VALUES(%s, %s, %s)",
                    userId, bookId, false);
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public boolean deleteRequest(int userId, int bookId) {
        try {
            if (getBookRequests(userId, bookId).size() == 0) {
                return false;
            }
            Connection connection = ConnectionPool.getConnectionPool().getConnection();
            String sqlQuery = "DELETE FROM request_handler WHERE id_user = ? AND id_book = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setLong(1,userId);
            pstmt.setLong(2, bookId);
            pstmt.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    static public boolean acceptRequest(int userId, int bookId) {
        try {
            if (getBookRequests(userId, bookId).size() == 0) {
                return false;
            }
            Connection connection = ConnectionPool.getConnectionPool().getConnection();
            String sqlQuery = "UPDATE request_handler SET accepted = 'true' WHERE id_user = ? AND id_book = ?";
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