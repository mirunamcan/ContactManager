package mmocanu.fr.contactmanager.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mmocanu.fr.contactmanager.AccueilView;
import mmocanu.fr.contactmanager.PasswordVerifier;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static mmocanu.fr.contactmanager.user.UserDAO.*;

public class SettingsView {
    @FXML
    public Button ValiderBtn;
    @FXML
    public TextField MdpField;
    @FXML
    public TextField MdpConfirmField;
    @FXML
    public Button backButton;
    @FXML
    private Button logoutButton;


    @FXML
    public void initialize() {

        backButton.setOnAction(event -> {
            try {
                AccueilView accueilView = new AccueilView();
                Scene accueilScene = accueilView.getScene();
                Stage currentStage = (Stage) backButton.getScene().getWindow();
                currentStage.setScene(accueilScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        logoutButton.setOnAction(event -> logout());
        ValiderBtn.setOnAction(event -> {
                    String password = MdpField.getText();
                    String confirmPassword = MdpConfirmField.getText();

                    if (validatePasswordMatch(password, confirmPassword)) {
                        if (validatePassword(password)) {
                            try {
                                String hashedPassword = hashPassword(password);
                                UserDAO userDAO = new UserDAO();
                                userDAO.UpdateUserPwd(hashedPassword);
                                AccueilView accueilView = new AccueilView();
                                Scene accueilScene = accueilView.getScene();
                                Stage currentStage = (Stage) ValiderBtn.getScene().getWindow();
                                currentStage.setScene(accueilScene);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        }

        /*changeUsernameButton.setOnAction(event -> {
            String password = promptForPassword();
            if (isPasswordCorrect(password)) {
                // Allow user to change username
            } else {
                showError("Incorrect password");
            }
        });

        changePasswordButton.setOnAction(event -> {
            String username = promptForUsername();
            String newPassword = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            if (isUsernameCorrect(username) && newPassword.equals(confirmPassword)) {
                // Change user's password
            } else {
                showError("Incorrect username or passwords do not match");
            }
        });*/


    private void openSettings() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/views/settings-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout() {
        try {
            UserSession.getInstance(null).cleanUserSession();
            // Load the login page
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/connexion-view.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage for the login page
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Login");
            stage.setScene(new Scene(root));

            // Show the login page and close the current window
            stage.show();
            ((Stage) logoutButton.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        if (!password.matches(regex)) {
            showAlert("Invalid input", "The password must contain at least 8 characters, a digit, a lower case letter, an upper case letter, and a special character.");
            return false;
        }
        return true;
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