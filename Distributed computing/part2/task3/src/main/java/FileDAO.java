import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {
    public static File findById(long id) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM file "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            File file = null;
            if(rs.next()) {
                file = new File();
                file.setId(rs.getLong(1));
                file.setName(rs.getString(2));
                file.setDirectoryId(rs.getLong(3));
                file.setDirectoryId(rs.getLong(4));
            }
            st.close();
            return file;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static File findByName(String name) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM file "
                            + "WHERE name = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            File file = null;
            if(rs.next()) {
                file = new File();
                file.setId(rs.getLong(1));
                file.setName(rs.getString(2));
                file.setDirectoryId(rs.getLong(3));
                file.setSize(rs.getLong(4));
            }
            st.close();
            return file;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static boolean update(File file) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "UPDATE file "
                            + "SET name = ?, directoryid = ?, size = ? "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, file.getName());
            st.setLong(2, file.getDirectoryId());
            st.setLong(3, file.getSize());
            st.setLong(4, file.getId());
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

    public static boolean insert(File file) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "INSERT INTO file (name,directoryid,size) "
                            + "VALUES (?,?,?) "
                            + "RETURNING id";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, file.getName());
            st.setLong(2, file.getDirectoryId());
            st.setLong(3, file.getSize());
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                file.setId(rs.getLong(1));
            } else
                return false;
            st.close();
            return true;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public static boolean delete(File file) {
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "DELETE FROM file "
                            + "WHERE id = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, file.getId());
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

    public static List<File> findAll(){
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM file";
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            List<File> list = new ArrayList<>();
            while(rs.next()) {
                var file = new File();
                file.setId(rs.getLong(1));
                file.setName(rs.getString(2));
                file.setDirectoryId(rs.getLong(3));
                file.setSize(rs.getLong(4));
                list.add(file);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static List<File> findByDirectoryId(Long id){
        try(Connection connection = DBConnection.getConnection();) {
            String sql =
                    "SELECT * "
                            + "FROM file "
                            + "WHERE directoryid = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            List<File> list = new ArrayList<>();
            while(rs.next()) {
                var file = new File();
                file.setId(rs.getLong(1));
                file.setName(rs.getString(2));
                file.setDirectoryId(rs.getLong(3));
                file.setSize(rs.getLong(4));
                list.add(file);
            }
            st.close();
            return list;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}