package mmocanu.fr.contactmanager.contact;


import java.sql.Date;

public class ContactDTO {

    private int id;
    private String nom;
    private String prenom;
    private String numero;
    private String mail;
    private String adresse;

    private Date anniversaire;

    private String note;


    public ContactDTO(int id, String nom, String prenom, String numero, String mail, String adresse, Date anniversaire, String note) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.mail = mail;
        this.adresse = adresse;
        this.anniversaire = anniversaire;
        this.note = note;

    }


    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumero() {
        return numero;
    }

    public String getMail() {
        return mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public Date getAnniversaire() {
        return anniversaire;
    }

    public String getNote() {
        return note;
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setAnniversaire(Date anniversaire) {
        this.anniversaire = anniversaire;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(int id) {
        this.id = id;
    }

}