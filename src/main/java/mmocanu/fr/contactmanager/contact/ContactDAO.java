package mmocanu.fr.contactmanager.contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dbcontact";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public List<ContactDTO> getAllContacts(int userId) throws Exception {
        List<ContactDTO> contacts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("SELECT * FROM contact where user_id = ?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String numero = rs.getString("numero");
                String mail = rs.getString("mail");
                String adresse = rs.getString("adresse");
                Date anniversaire = rs.getDate("anniversaire");
                String note = rs.getString("note");
                contacts.add(new ContactDTO(id, nom, prenom, numero, mail, adresse, anniversaire, note));
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return contacts;
    }

    public void updateContact(ContactDTO contact) {
        String sql = "UPDATE contact SET nom = ?, prenom = ?, anniversaire = ?, numero = ?, adresse = ?, note = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, contact.getNom());
            pstmt.setString(2, contact.getPrenom());
            if (contact.getAnniversaire() != null) {
                pstmt.setDate(3, java.sql.Date.valueOf(contact.getAnniversaire()));
            } else {
                pstmt.setNull(3, Types.DATE);
            }
            pstmt.setString(4, contact.getNumero());
            pstmt.setString(5, contact.getAdresse());
            pstmt.setString(6, contact.getNote());
            pstmt.setInt(7, contact.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertContact(ContactDTO contact, int userId) {
        String sql = "INSERT INTO contact(user_id, nom, prenom, anniversaire, numero, adresse, note) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, contact.getNom());
            pstmt.setString(3, contact.getPrenom());
            if (contact.getAnniversaire() != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(contact.getAnniversaire()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }
            pstmt.setString(5, contact.getNumero());
            pstmt.setString(6, contact.getAdresse());
            pstmt.setString(7, contact.getNote());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteContact(int contactId) {
        String sql = "DELETE FROM contact WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, contactId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




}