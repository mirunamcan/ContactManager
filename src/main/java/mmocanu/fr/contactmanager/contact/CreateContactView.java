package mmocanu.fr.contactmanager.contact;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mmocanu.fr.contactmanager.AccueilView;
import mmocanu.fr.contactmanager.user.UserDTO;
import mmocanu.fr.contactmanager.user.UserSession;

import java.sql.Date;
import java.time.LocalDate;

public class CreateContactView {

    @FXML
    private TextField prenomField;

    @FXML
    private TextField nomField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField numeroField;

    @FXML
    private TextField adresseField;

    @FXML
    private TextArea noteArea;

    @FXML
    private Button enregistrerButton;

    private ContactDAO contactDAO;

    private UserDTO userDTO;

    private AccueilView accueilView;

    public CreateContactView() {
        this.contactDAO = new ContactDAO();
    }



    @FXML
    public void initialize() {
        enregistrerButton.setOnAction(e -> {
            ContactDTO contact = getContactFromFields();
            UserDTO user = UserSession.getInstance(userDTO).getUser(); // Modify this line
            contactDAO.insertContact(contact, user.getId());
            try {
                accueilView.initialize();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            ((Stage) enregistrerButton.getScene().getWindow()).close();
        });
    }

    public void setAccueilView(AccueilView accueilView) {
        this.accueilView = accueilView;
    }

    private ContactDTO getContactFromFields() {
        String prenom = prenomField.getText();
        String nom = nomField.getText();
        LocalDate date = datePicker.getValue();
        Date anniversaire = null;
        if (date != null) {
            anniversaire = java.sql.Date.valueOf(date);
        }
        String numero = numeroField.getText();
        String adresse = adresseField.getText();
        String note = noteArea.getText();

        return new ContactDTO(0, prenom, nom, numero, null, adresse, anniversaire, note);
    }
}