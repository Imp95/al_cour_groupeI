package groupei.al.blablacar.Entities;

import java.io.Serializable;
import java.util.Date;

public class Offre implements Serializable {
    private int ID;
    private Date Date_proposee;
    private String Status;
    private int Annonce_repondu;
    private int Transporteur;
    private String Bagage;
    private int Paiement;
    private String Adresse_depart;  // A changer pour Address
    private String Adresse_arrivee; // A changer pour Address

    public Offre(int ID, Date date_proposee, String status, int annonce_repondu, int transporteur, String bagage, int paiement, String adresse_depart, String adresse_arrivee) {
        this.ID = ID;
        Date_proposee = date_proposee;
        Status = status;
        Annonce_repondu = annonce_repondu;
        Transporteur = transporteur;
        Bagage = bagage;
        Paiement = paiement;
        Adresse_depart = adresse_depart;
        Adresse_arrivee = adresse_arrivee;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getDate_proposee() {
        return Date_proposee;
    }

    public void setDate_proposee(Date date_proposee) {
        Date_proposee = date_proposee;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getAnnonce_repondu() {
        return Annonce_repondu;
    }

    public void setAnnonce_repondu(int annonce_repondu) {
        Annonce_repondu = annonce_repondu;
    }

    public int getTransporteur() {
        return Transporteur;
    }

    public void setTransporteur(int transporteur) {
        Transporteur = transporteur;
    }

    public String getBagage() {
        return Bagage;
    }

    public void setBagage(String bagage) {
        Bagage = bagage;
    }

    public int getPaiement() {
        return Paiement;
    }

    public void setPaiement(int paiement) {
        Paiement = paiement;
    }

    public String getAdresse_depart() {
        return Adresse_depart;
    }

    public void setAdresse_depart(String adresse_depart) {
        Adresse_depart = adresse_depart;
    }

    public String getAdresse_arrivee() {
        return Adresse_arrivee;
    }

    public void setAdresse_arrivee(String adresse_arrivee) {
        Adresse_arrivee = adresse_arrivee;
    }


    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Offre : ");
        stringBuilder.append(getID());
        stringBuilder.append(", Status : ");
        stringBuilder.append(getStatus());
        stringBuilder.append(", Date proposée : ");
        stringBuilder.append(getDate_proposee());
        stringBuilder.append(", Annonce repondu : ");
        stringBuilder.append(getAnnonce_repondu());
        return stringBuilder.toString();
    }
}
