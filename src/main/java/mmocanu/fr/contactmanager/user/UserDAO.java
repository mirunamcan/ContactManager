// UserDAO.java
package mmocanu.fr.contactmanager.user;

import java.sql.*;

public class UserDAO {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dbcontact";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public void addUser(UserDTO user) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }

    public UserDTO getUserById(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        UserDTO user = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new UserDTO(id, username, password);
            }
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return user;
    }

    public void UpdateUserPwd(String hashedPassword) throws SQLException {

        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?");
            stmt.setString(1, hashedPassword);
            stmt.setInt(2, UserSession.getSavedUserId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted == 1) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}