package mmocanu.fr.contactmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mmocanu.fr.contactmanager.user.UserDAO;
import mmocanu.fr.contactmanager.user.UserDTO;
import mmocanu.fr.contactmanager.user.UserSession;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    double x, y;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader;

        UserDAO userDAO = new UserDAO();
        int savedUserId = UserSession.getSavedUserId();

        if (savedUserId != -1) {
            // If a user ID is saved in preferences, retrieve the user data and create a new UserSession
            UserDTO user = userDAO.getUserById(savedUserId);
            if (user != null) {
                UserSession.getInstance(user);
                fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/views/contact/accueil-view.fxml"));
            } else {
                fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/connexion-view.fxml"));
            }
        } else {
            fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/connexion-view.fxml"));
        }
        Scene scene = new Scene(fxmlLoader.load());
        scene.setOnMousePressed(evt -> {
            x = evt.getSceneX();
            y = evt.getSceneY();
        });
        scene.setOnMouseDragged(evt -> {
            stage.setX(evt.getScreenX() - x);
            stage.setY(evt.getScreenY() - y);
        });

        stage.setTitle("Connexion");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}