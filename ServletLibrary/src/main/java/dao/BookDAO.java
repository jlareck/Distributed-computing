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
            PreparedStatement prepareStatement = connection.prepareStatement(sqlQuery);
            prepareStatement.setString(1, name);
            ResultSet rs = prepareStatement.executeQuery();

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
            PreparedStatement prepareStatement = connection.prepareStatement(sqlSelect);
            prepareStatement.setLong(1, bookId);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt("stock");
                if (stock != 0) {
                    stock--;
                    final String sqlUpdate = "UPDATE books SET stock = ? WHERE id = ?";
                    PreparedStatement st = connection.prepareStatement(sqlUpdate);
                    st.setInt(1, stock);
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
            String sqlSelect = "SELECT stock FROM books WHERE id = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sqlSelect);
            prepareStatement.setLong(1, bookId);
            ResultSet rs = prepareStatement.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt("stock");
                stock++;
                final String sqlUpdate = "UPDATE books SET stock = ? WHERE id = ?";
                PreparedStatement st = connection.prepareStatement(sqlUpdate);
                st.setInt(1, stock);
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