package mmocanu.fr.contactmanager.contact;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mmocanu.fr.contactmanager.AccueilView;
import mmocanu.fr.contactmanager.contact.ContactDAO;
import mmocanu.fr.contactmanager.contact.ContactDTO;
import mmocanu.fr.contactmanager.contact.ShowContactView;

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
    private ContactDAO contactDAO = new ContactDAO();

    public UpdateContactView(AccueilView accueilView) {
        this.accueilView = accueilView;
    }

    public void setContactToUpdate(ContactDTO contactToUpdate) {
        this.contactToUpdate = contactToUpdate;
        prenomField.setText(contactToUpdate.getPrenom());
        nomField.setText(contactToUpdate.getNom());
        anniversaireField.setValue(contactToUpdate.getAnniversaire());
        numeroField.setText(contactToUpdate.getNumero());
        adresseField.setText(contactToUpdate.getAdresse());
        noteField.setText(contactToUpdate.getNote());
    }


    @FXML
    public void initialize() {
        enregistrerBtn.setOnAction(event -> {
            if (contactToUpdate != null) {
                contactToUpdate.setPrenom(prenomField.getText());
                contactToUpdate.setNom(nomField.getText());
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

                contactDAO.updateContact(contactToUpdate);

                accueilView.updateContact(contactToUpdate);

                try {
                    accueilView.refreshTable();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Stage stage = (Stage) enregistrerBtn.getScene().getWindow();
                stage.close();
            }
        });
    }

    public void setAccueilView(AccueilView accueilView) {
        this.accueilView = accueilView;
    }
}