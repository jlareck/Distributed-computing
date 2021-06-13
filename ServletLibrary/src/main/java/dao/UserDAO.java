package dao;
import db.ConnectionPool;
import model.*;

import java.sql.*;
import java.util.ArrayList;
public class UserDAO {
    static public ArrayList<User> getUserFromDB(String login, String password)  {
        ArrayList<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getConnectionPool();
        try(Connection connection = pool.getConnection()) {
            String sql =
                    "SELECT * FROM users WHERE login = ? AND password = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, login);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            User user = new User();
            if(rs.next()) {
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setLastname(rs.getString(3));
                user.setLogin(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setAdmin(rs.getBoolean(6));
            }
            st.close();
            users.add(user);
            pool.releaseConnection(connection);

            return users;
        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
