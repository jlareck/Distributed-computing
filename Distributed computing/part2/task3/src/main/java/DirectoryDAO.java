import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDAO {
    public static Directory findById(long id) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM directory "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            Directory directory = null;
            if(rs.next()) {
                directory = new Directory();
                directory.setId(rs.getLong(1));
                directory.setName(rs.getString(2));
            }
            st.close();
            return directory;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static Directory findByName(String name) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM directory "
                            + "WHERE name = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            Directory directory = null;
            if(rs.next()) {
                directory = new Directory();
                directory.setId(rs.getLong(1));
                directory.setName(rs.getString(2));
            }
            st.close();
            return directory;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static boolean update(Directory directory) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "UPDATE directory "
                            + "SET name = ? "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, directory.getName());
            st.setLong(2, directory.getId());
            var result = st.executeUpdate();
            st.close();
            if(result>0)
                return true;
            else
                return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean insert(Directory directory) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "INSERT INTO directory (name) "
                            + "VALUES (?) "
                            + "RETURNING id";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, directory.getName());
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                directory.setId(rs.getLong(1));
            } else
                return false;
            st.close();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean delete(Directory directory) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM directory "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, directory.getId());
            var result = st.executeUpdate();
            st.close();
            if(result>0)
                return true;
            else
                return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static List<Directory> findAll(){
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM directory";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<Directory> list = new ArrayList<>();
            while(rs.next()) {
                var directory = new Directory();
                directory.setId(rs.getLong(1));
                directory.setName(rs.getString(2));
                list.add(directory);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
