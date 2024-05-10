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
}