package groupei.al.blablacar.Entities;

import java.util.Date;
import java.util.LinkedList;

public class Annonce {

    private int ID;
    private Date Date_Debut;
    private Date Date_Fin;
    private String Bagages;
    private int Paiement;
    private int Status;
    private Utilisateur Client;
    private Adresse Adresse_depart;
    private Adresse Adresse_arrivee;
    private LinkedList<Offre> Liste_d_offre;

    public Annonce(int ID, Date date_Debut, Date date_Fin, String bagages, int paiement, int status, Utilisateur client, Adresse adresse_depart, Adresse adresse_arrivee, LinkedList<Offre> liste_d_offre) {
        this.ID = ID;
        Date_Debut = date_Debut;
        Date_Fin = date_Fin;
        Bagages = bagages;
        Paiement = paiement;
        Status = status;
        Client = client;
        Adresse_depart = adresse_depart;
        Adresse_arrivee = adresse_arrivee;
        Liste_d_offre = liste_d_offre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getDate_Debut() {
        return Date_Debut;
    }

    public void setDate_Debut(Date date_Debut) {
        Date_Debut = date_Debut;
    }

    public Date getDate_Fin() {
        return Date_Fin;
    }

    public void setDate_Fin(Date date_Fin) {
        Date_Fin = date_Fin;
    }

    public String getBagages() {
        return Bagages;
    }

    public void setBagages(String bagages) {
        Bagages = bagages;
    }

    public int getPaiement() {
        return Paiement;
    }

    public void setPaiement(int paiement) {
        Paiement = paiement;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Utilisateur getClient() {
        return Client;
    }

    public void setClient(Utilisateur client) {
        Client = client;
    }

    public Adresse getAdresse_depart() {
        return Adresse_depart;
    }

    public void setAdresse_depart(Adresse adresse_depart) {
        Adresse_depart = adresse_depart;
    }

    public Adresse getAdresse_arrivee() {
        return Adresse_arrivee;
    }

    public void setAdresse_arrivee(Adresse adresse_arrivee) {
        Adresse_arrivee = adresse_arrivee;
    }

    public LinkedList<Offre> getListe_d_offre() {
        return Liste_d_offre;
    }

    public void setListe_d_offre(LinkedList<Offre> liste_d_offre) {
        Liste_d_offre = liste_d_offre;
    }
}
