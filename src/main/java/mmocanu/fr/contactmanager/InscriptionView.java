package mmocanu.fr.contactmanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

public class InscriptionView {
    @FXML
    private Hyperlink goToConnexion;
    @FXML
    private TextField identifiantField;
    @FXML
    private PasswordField mdpField;
    @FXML
    private PasswordField validmdpField;
    @FXML
    private Button enregistrerBtn;

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/dbcontact";
    static final String USER = "root";
    static final String PASS = "";

    @FXML
    public void initialize() {
        goToConnexion.setOnAction(event -> goToConnexion());

        enregistrerBtn.setOnAction(event -> {
            String username = identifiantField.getText();
            String password = mdpField.getText();
            String confirmPassword = validmdpField.getText();

            if (validatePasswordMatch(password, confirmPassword)) {
                if (validatePassword(password)) {
                    try {
                        String hashedPassword = hashPassword(password);
                        registerUser(username, hashedPassword);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (!password.matches(regex)) {
            showAlert("Invalid input", "The password must contain at least 8 characters, a digit, a lower case letter, an upper case letter, and a special character.");
            return false;
        }
        return true;
    }




    private void registerUser(String username, String password) throws SQLException {
        if (username.isEmpty() || password.isEmpty() || validmdpField.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            stmt.setString(1, username);
            stmt.setString(2, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted == 1) {
                showAlert("Success", "You are now registered");
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

public Scene getScene() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/mmocanu/fr/contactmanager/views/inscription-view.fxml"));
        return new Scene(root);
        }

public void goToConnexion() {
        try {
        ConnexionView connexionView = new ConnexionView();
        Scene connexionScene = connexionView.getScene();
        Stage currentStage = (Stage) goToConnexion.getScene().getWindow();
        currentStage.setScene(connexionScene);
        } catch (Exception e) {
        e.printStackTrace();
        }
        }

private boolean validatePasswordMatch(String password, String confirmPassword) {
        if (password == null || confirmPassword == null) {
        showAlert("Invalid input", "Please enter a password");
        return false;
        }
        if (!password.equals(confirmPassword)) {
        showAlert("Invalid input", "Passwords do not match");
        return false;
        }
        return true;
        }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return PasswordVerifier.bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        }
        }