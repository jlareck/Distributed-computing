package dao;
import db.ConnectionPool;
import model.*;

import java.sql.*;
import java.util.ArrayList;
public class UserDAO {
    static public ArrayList<User> getUserFromDB(String login, String password, int isAdmin) throws InterruptedException, SQLException {
        ArrayList<User> users = new ArrayList<>();
        ConnectionPool pool = ConnectionPool.getConnectionPool();
        try(Connection connection = pool.getConnection()) {
            String sql =
                    "SELECT * FROM employees WHERE login = ? AND password = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, login);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            User user = new User();
            if(rs.next()) {
                user.setId(rs.getInt(1));
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

    public static void main(String[] args) {
//        try {
//            ArrayList<User> users = getUserFromDB(Constants.SELECT_ALL_INT, Constants.SELECT_ALL_STR, Constants.SELECT_ALL_STR, Constants.SELECT_ALL_INT);
//            for (User user: users) {
//                System.out.println(user.getName());
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }
}
