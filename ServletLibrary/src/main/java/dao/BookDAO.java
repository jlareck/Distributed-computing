package dao;

import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import db.ConnectionPool;

public class BookDAO {
    static public ArrayList<Book> getBooksFromDB(long id, String name)  {
        ArrayList<Book> books = new ArrayList<>();
        try {
            Connection connection = ConnectionPool.getConnectionPool().getConnection();

            String sqlQuery = "SELECT * FROM books WHERE title = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong(1));
                book.setTitle(rs.getString(2));
                book.setStock(rs.getInt(3));
                books.add(book);
            }
            connection.close();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return books;
    }

    static public boolean takeBook(long bookId) {
        boolean book_taken = false;
        Connection connection;
        try {
            connection = ConnectionPool.getConnectionPool().getConnection();
            final String sqlSelect = "SELECT * FROM books WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlSelect);
            pstmt.setLong(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int amount = rs.getInt("amount");
                if (amount != 0) {
                    amount--;
                    final String sqlUpdate = "UPDATE books SET amount = ? WHERE id = ?";
                    PreparedStatement st = connection.prepareStatement(sqlUpdate);
                    st.setInt(1, amount);
                    st.setLong(2, bookId);
                    st.executeUpdate();
                    st.close();
                    book_taken = true;
                }
            }
            connection.close();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return book_taken;
    }

    static public boolean returnBook(long bookId){
        boolean bookReturned = false;
        Connection connection;
        try {
            connection = ConnectionPool.getConnectionPool().getConnection();
            String sqlSelect = "SELECT amount FROM books WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlSelect);
            pstmt.setLong(1, bookId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int amount = rs.getInt("amount");
                amount++;
                final String sqlUpdate = "UPDATE books SET amount = ? WHERE id = ?";
                PreparedStatement st = connection.prepareStatement(sqlUpdate);
                st.setInt(1, amount);
                st.setLong(2, bookId);
                st.executeUpdate();
                st.close();
                bookReturned = true;
            }
            connection.close();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return bookReturned;
    }

}