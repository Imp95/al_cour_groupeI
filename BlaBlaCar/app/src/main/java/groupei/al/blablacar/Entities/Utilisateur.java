package groupei.al.blablacar.Entities;

import java.io.Serializable;
import java.util.Date;

public class Utilisateur implements Serializable {
    private String Email;
    private String Nom;
    private String Prenom;
    private Date Date_de_naissance;
    private String Telephone;
    private int Solde;

    public Utilisateur(String email, String nom, String prenom, Date date_de_naissance, String telephone, int solde) {
        Email = email;
        Nom = nom;
        Prenom = prenom;
        Date_de_naissance = date_de_naissance;
        Telephone = telephone;
        Solde = solde;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public Date getDate_de_naissance() {
        return Date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        Date_de_naissance = date_de_naissance;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public int getSolde() {
        return Solde;
    }

    public void setSolde(int solde) {
        Solde = solde;
    }
}
