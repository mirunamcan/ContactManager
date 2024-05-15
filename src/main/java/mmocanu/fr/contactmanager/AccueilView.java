package mmocanu.fr.contactmanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import mmocanu.fr.contactmanager.contact.*;
import mmocanu.fr.contactmanager.user.UserSession;
import mmocanu.fr.contactmanager.DigitKeyboardDialogController;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AccueilView {

    @FXML
    private TableView<ContactDTO> contactTable;

    @FXML
    public Button settingsButton;

    @FXML
    public Button AddContactBtn;

    @FXML
    private TextField numeroField;




    private ShowContactView showContactView;
    private ObservableList<ContactDTO> contacts;


    public Scene getScene() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mmocanu/fr/contactmanager/views/contact/accueil-view.fxml")));
        return new Scene(root);
    }

    @FXML
    public void initialize() throws Exception {


        AccueilView accueilView = this;


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

        TableColumn<ContactDTO, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setResizable(false);
        actionsColumn.setPrefWidth(200);
        // Set the cell factory to create cells containing the buttons
        actionsColumn.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button seeButton = new Button("See");
                private final Button editButton = new Button("Edit");
                private final Button deleteButton = new Button("Delete");
                private final HBox pane = new HBox(seeButton, editButton, deleteButton);


                {
                    seeButton.setOnAction(event -> {
                        // Get the current contact
                        ContactDTO contact = getTableView().getItems().get(getIndex());

                        try {
                            ShowContactView showContactView = new ShowContactView(accueilView, contact);
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/views/contact/showContact-view.fxml"));
                            fxmlLoader.setController(showContactView);
                            showContactView.setContactToShow(contact); // Pass the ContactDTO object to the ShowContactView controller
                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setTitle("Show Contact");
                            stage.setScene(new Scene(root)); // Create a new scene with the root node
                            stage.show();
                        } catch (IOException e) {
                            // Handle the exception (e.g., show an error message to the user)
                            e.printStackTrace();
                        }
                    });
                    editButton.setOnAction(event -> {
                        // Get the current contact
                        ContactDTO contact = getTableView().getItems().get(getIndex());

                        try {
                            FXMLLoader updateLoader = new FXMLLoader();
                            updateLoader.setLocation(getClass().getResource("/mmocanu/fr/contactmanager/views/contact/update-contactview.fxml"));
                            updateLoader.setControllerFactory(c -> new UpdateContactView(accueilView)); // Use the AccueilView reference here
                            Parent rootUpdateContact = updateLoader.load();

                            UpdateContactView updateContactView = updateLoader.getController();
                            updateContactView.setContactToUpdate(contact);
                            updateContactView.setAccueilView(AccueilView.this);

                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setTitle("Update Contact");
                            stage.setScene(new Scene(rootUpdateContact));

                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });


                    deleteButton.setOnAction(event -> {
                        // Get the current contact
                        ContactDTO contact = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Delete Contact");
                        alert.setContentText("Are you sure you want to delete this contact?");

                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            // User chose OK, delete the contact
                            contactDAO.deleteContact(contact.getId()); // Delete the contact from the database
                            getTableView().getItems().remove(contact); // Remove the contact from the table view
                        } else {
                            // User chose CANCEL or closed the dialog
                        }
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
            };
        });


        contactTable.getColumns().setAll(nomColumn, prenomColumn, numeroColumn, actionsColumn);

        ObservableList<ContactDTO> data = FXCollections.observableArrayList(contacts);
        contactTable.setItems(data);

        settingsButton.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/mmocanu/fr/contactmanager/views/settings-view.fxml")));
                Scene scene = new Scene(root);
                Stage stage = (Stage) settingsButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        AddContactBtn.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/views/contact/create-contactview.fxml"));
                Parent root = fxmlLoader.load();
                CreateContactView controller = fxmlLoader.getController();
                controller.setAccueilView(this);
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

    public void refreshTable() throws Exception {
        // Fetch the updated data
        ContactDAO contactDAO = new ContactDAO();
        int userId = UserSession.getInstance(null).getUser().getId();
        List<ContactDTO> updatedContacts = contactDAO.getAllContacts(userId);
        ObservableList<ContactDTO> updatedData = FXCollections.observableArrayList(updatedContacts);

        // Clear the existing items in the table
        contactTable.getItems().clear();

        // Add the updated data to the table
        contactTable.getItems().addAll(updatedData);
    }


    public void updateContact(ContactDTO contactToUpdate) {
        // Update the contact here...
    }

}

