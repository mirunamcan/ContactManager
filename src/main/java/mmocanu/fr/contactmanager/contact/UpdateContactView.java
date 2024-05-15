package mmocanu.fr.contactmanager.contact;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import mmocanu.fr.contactmanager.AccueilView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mmocanu.fr.contactmanager.DigitKeyboardDialogController;

import java.io.IOException;
import java.sql.Date;

public class UpdateContactView {

    @FXML
    private TextField prenomField;

    @FXML
    private TextField nomField;

    @FXML
    private DatePicker anniversaireField;

    @FXML
    private TextField numeroField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextArea noteField;

    @FXML
    private Button enregistrerBtn;


    private ContactDTO contactToUpdate;
    private AccueilView accueilView;
    private ShowContactView showContactView;
    private ContactDAO contactDAO = new ContactDAO();

    public UpdateContactView(AccueilView accueilView, ShowContactView showContactView) {
        this.accueilView = accueilView;
        this.showContactView = showContactView;}


    public void setContactToUpdate(ContactDTO contactToUpdate) {
        this.contactToUpdate = contactToUpdate;
        // Populate the form fields with the current data of the contact
        prenomField.setText(contactToUpdate.getPrenom());
        nomField.setText(contactToUpdate.getNom());
        anniversaireField.setValue(contactToUpdate.getAnniversaire());
        numeroField.setText(contactToUpdate.getNumero());
        adresseField.setText(contactToUpdate.getAdresse());
        noteField.setText(contactToUpdate.getNote());

        // Update the contact data with the form inputs
        contactToUpdate.setPrenom(prenomField.getText());
        contactToUpdate.setNom(nomField.getText());
        if (anniversaireField.getValue() != null) {
            contactToUpdate.setAnniversaire(Date.valueOf(anniversaireField.getValue()));
        }

        String phoneNumber = numeroField.getText();
        if (!phoneNumber.matches("0\\d{9}")) {
            System.out.println("Invalid phone number format");
            return;}

        contactToUpdate.setNumero(numeroField.getText());
        contactToUpdate.setAdresse(adresseField.getText());
        contactToUpdate.setNote(noteField.getText());

        System.out.println("Contact to update after setting fields: " + contactToUpdate);

        // Update the contact data in the AccueilView and ShowContactView
        accueilView.updateContact(contactToUpdate);
        showContactView.updateContact(contactToUpdate);
    }



    public void setShowContactView(ShowContactView showContactView) {
        this.showContactView = showContactView;
    }
    @FXML
    public void initialize() {
        enregistrerBtn.setOnAction(event -> {
            System.out.println("Button clicked");
            if (contactToUpdate != null) {
                System.out.println("Contact to update is not null");

                // Update the contact data with the form inputs
                contactToUpdate.setPrenom(prenomField.getText());
                contactToUpdate.setNom(nomField.getText());

                // Only set the anniversaire if the field is not null
                if (anniversaireField.getValue() != null) {
                    contactToUpdate.setAnniversaire(Date.valueOf(anniversaireField.getValue()));
                } else {
                    contactToUpdate.setAnniversaire(null);
                }

                String phoneNumber = numeroField.getText();
                if (!phoneNumber.matches("0\\d{9}")) {
                    System.out.println("Invalid phone number format");
                    return;
                }

                contactToUpdate.setNumero(numeroField.getText());
                contactToUpdate.setAdresse(adresseField.getText());
                contactToUpdate.setNote(noteField.getText());

                System.out.println("Contact to update after setting fields: " + contactToUpdate);

                // Update the contact data in the AccueilView and ShowContactView
                accueilView.updateContact(contactToUpdate);
                try {
                    System.out.println("Initialize accueil view");
                    accueilView.initialize();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                showContactView.updateContact(contactToUpdate);

                contactDAO.updateContact(contactToUpdate);

                // Close the window
                Stage stage = (Stage) enregistrerBtn.getScene().getWindow();
                stage.close();
            } else {
                System.out.println("Contact to update is null");
            }
        });
        numeroField.setOnMouseClicked(event -> {
            try {
                numeroField.clear();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mmocanu/fr/contactmanager/views/DigitKeyboard.fxml"));
                Pane root = fxmlLoader.load();

                DigitKeyboardDialogController controller = fxmlLoader.getController();
                controller.setNumeroField(numeroField);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Digit Keyboard");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    public void setAccueilView(AccueilView accueilView) {
        this.accueilView = accueilView;
    }

}