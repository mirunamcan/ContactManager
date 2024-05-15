package mmocanu.fr.contactmanager.contact;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mmocanu.fr.contactmanager.AccueilView;
import mmocanu.fr.contactmanager.contact.ContactDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class ShowContactView {

    @FXML
    private TextField prenom;

    @FXML
    private TextField nom;

    @FXML
    private DatePicker anniversaire;

    @FXML
    private TextField numero;

    @FXML
    private TextField adresse;

    @FXML
    private TextArea note;

    private ContactDTO contactToSee;

    private AccueilView accueilView;

    @FXML
    public void initialize() {
        if (prenom != null) {
            prenom.setText(contactToSee.getPrenom());
        }

        if (nom != null) {
            nom.setText(contactToSee.getNom());
        }
        if (anniversaire != null && contactToSee != null && contactToSee.getAnniversaire() != null) {
            Date date = Date.valueOf(contactToSee.getAnniversaire());
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            anniversaire.setValue(localDate);
        }

        if (numero != null) {
            numero.setText(contactToSee.getNumero());
        }
        if (adresse != null) {
            adresse.setText(contactToSee.getAdresse());
        }
        if (note != null){
            note.setText(contactToSee.getNote());
        }
    }
    public ShowContactView(AccueilView accueilView, ContactDTO contact) {
        this.accueilView = accueilView;
        this.contactToSee = contact;
    }


    public void setContactToShow(ContactDTO contact) {
        System.out.println("contactToSee: " + contact.getNom() + " " + contact.getPrenom());
        this.contactToSee = contact;
        System.out.println("contactToSee: " + contactToSee.getNom() + " " + contactToSee.getPrenom());

    }

    // ...
}