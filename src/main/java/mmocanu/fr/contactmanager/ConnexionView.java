package mmocanu.fr.contactmanager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import mmocanu.fr.contactmanager.user.UserDTO;
import mmocanu.fr.contactmanager.user.UserSession;

import java.sql.*;

import static mmocanu.fr.contactmanager.PasswordVerifier.verifyPassword;

public class ConnexionView {
    @FXML
    private Hyperlink goToInscription;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dbcontact";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";
    @FXML
    public void initialize() {
        goToInscription.setOnAction(event -> {
            goToInscription();
        });

        submitButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (validateUsername(username) && validatePassword(password)) {
                try {
                    verifData(username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Scene getScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/mmocanu/fr/contactmanager/connexion-view.fxml"));
        return new Scene(root);
    }

    public void goToInscription() {
        try {
            InscriptionView inscriptionView = new InscriptionView();
            Scene inscriptionScene = inscriptionView.getScene();
            Stage currentStage = (Stage) goToInscription.getScene().getWindow();
            currentStage.setScene(inscriptionScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            showAlert("Invalid input", "Please enter a username");
            return false;
        }
        // Add more validation rules as needed
        return true;
    }

    private boolean validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            showAlert("Invalid input", "Please enter a password");
            return false;
        }
        // Add more validation rules as needed
        return true;
    }

    private void verifData(String username, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("SELECT id, username, password FROM users WHERE username = ?");
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPasswordHash = rs.getString("password");

                if (PasswordVerifier.verifyPassword(password, storedPasswordHash)) {
                    UserDTO user = new UserDTO(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
                    UserSession.getInstance(user);
                    AccueilView accueilView = new AccueilView();
                    Scene accueilScene = accueilView.getScene();
                    Stage currentStage = (Stage) submitButton.getScene().getWindow();
                    currentStage.setScene(accueilScene);
                } else {
                    showAlert("Invalid credentials", "The username or password is incorrect");
                }
            } else {
                showAlert("Invalid credentials", "The username or password is incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}