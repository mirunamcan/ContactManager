package mmocanu.fr.contactmanager.contact;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mmocanu.fr.contactmanager.contact.ContactDTO;

public class ShowContactView {

    @FXML
    private Label prenomLabel;

    @FXML
    private Label nomLabel;

    @FXML
    private Label anniversaireLabel;

    @FXML
    private Label numeroLabel;

    @FXML
    private Label adresseLabel;

    @FXML
    private Label noteLabel;

    // ...

    public void updateContact(ContactDTO updatedContact) {
        // Update the labels with the updated contact data
        if (prenomLabel != null) {
            prenomLabel.setText(updatedContact.getPrenom());
        }

        if (nomLabel != null) {
            nomLabel.setText(updatedContact.getNom());
        }
        if (anniversaireLabel != null) {
            anniversaireLabel.setText(updatedContact.getAnniversaire().toString());
        }

        if (numeroLabel != null) {
            numeroLabel.setText(updatedContact.getNumero());
        }
        if (adresseLabel != null) {
            adresseLabel.setText(updatedContact.getAdresse());
        }
        if (noteLabel != null){
            noteLabel.setText(updatedContact.getNote());
        }
    }

    // ...
}