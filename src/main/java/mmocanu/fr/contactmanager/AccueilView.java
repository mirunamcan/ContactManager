package mmocanu.fr.contactmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mmocanu.fr.contactmanager.contact.ContactDAO;
import mmocanu.fr.contactmanager.contact.ContactDTO;
import mmocanu.fr.contactmanager.user.UserSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AccueilView {
    @FXML
    public TableView<ContactDTO> contactTable;

    @FXML
    public Button settingsBtn;

    @FXML
    public Button AddContactBtn;


    public Scene getScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mmocanu/fr/contactmanager/views/contact/accueil-view.fxml")));
        return new Scene(root);
    }

    @FXML
    public void initialize() throws Exception {
        ContactDAO contactDAO = new ContactDAO();
        int userId = UserSession.getInstance(null).getUser().getId();
        List<ContactDTO> contacts = contactDAO.getAllContacts(userId);

        TableColumn<ContactDTO, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nomColumn.setResizable(false);
        nomColumn.setPrefWidth(100);

        TableColumn<ContactDTO, String> prenomColumn = new TableColumn<>("Prenom");
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        prenomColumn.setResizable(false);
        prenomColumn.setPrefWidth(100);

        TableColumn<ContactDTO, String> numeroColumn = new TableColumn<>("Numero");
        numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        numeroColumn.setResizable(false);
        numeroColumn.setPrefWidth(100);
//        TableColumn<ContactDTO, String> mailColumn = new TableColumn<>("Mail");
//        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
//        mailColumn.setResizable(false);
//        mailColumn.setPrefWidth(100);

       /* TableColumn<ContactDTO, String> adresseColumn = new TableColumn<>("Adresse");
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        adresseColumn.setResizable(false);
        adresseColumn.setPrefWidth(100);

        TableColumn<ContactDTO, Date> anniversaireColumn = new TableColumn<>("Anniversaire");
        anniversaireColumn.setCellValueFactory(new PropertyValueFactory<>("anniversaire"));
        anniversaireColumn.setResizable(false);
        anniversaireColumn.setPrefWidth(100);

        TableColumn<ContactDTO, String> noteColumn = new TableColumn<>("Note");
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        noteColumn.setResizable(false);
        noteColumn.setPrefWidth(100);*/

        TableColumn<ContactDTO, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setResizable(false);
        actionsColumn.setPrefWidth(200);
        // Set the cell factory to create cells containing the buttons
        actionsColumn.setCellFactory(param -> new TableCell<>() {

            private final Button seeButton = new Button("See");
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox pane = new HBox(seeButton, editButton, deleteButton);

            {

                seeButton.setOnAction(event -> {
                    // Get the current contact
                    ContactDTO contact = getTableView().getItems().get(getIndex());
                });
                // Set the actions for the buttons
                editButton.setOnAction(event -> {
                    // Get the current contact
                    ContactDTO contact = getTableView().getItems().get(getIndex());
                    // TODO: Implement your edit action here
                });

                deleteButton.setOnAction(event -> {
                    // Get the current contact
                    ContactDTO contact = getTableView().getItems().get(getIndex());
                    // TODO: Implement your delete action here
                });

                // Set the style for the buttons
                seeButton.getStyleClass().add("see-button");
                editButton.getStyleClass().add("edit-button");
                deleteButton.getStyleClass().add("delete-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });


        contactTable.getColumns().setAll(nomColumn, prenomColumn, numeroColumn,/* mailColumn, adresseColumn, anniversaireColumn, noteColumn, */actionsColumn);

        ObservableList<ContactDTO> data = FXCollections.observableArrayList(contacts);
        contactTable.setItems(data);

        settingsBtn.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mmocanu/fr/contactmanager/views/settings-view.fxml")));
                Scene scene = new Scene(root);
                Stage stage = (Stage) settingsBtn.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        AddContactBtn.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/views/contact/create-contactview.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL); // Optional: makes the new window modal
                stage.setTitle("Create Contact");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}