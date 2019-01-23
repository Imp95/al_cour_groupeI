package groupei.al.blablacar.Entities;

import java.io.Serializable;
import java.util.Date;

public class Contrat implements Serializable {
    private int ID;
    private String Status;
    private int Accuse_de_depot;
    private int Accuse_de_reception;
    private int Offre_acceptee;
    private Date Date_proposee;
    private int Paiement;
    private String Adresse_depart;  // A changer pour Address
    private String Adresse_arrivee; // A changer pour Address

    public Contrat(int ID, String status, int accuse_de_depot, int accuse_de_reception, int offre_acceptee) {
        this.ID = ID;
        Status = status;
        Accuse_de_depot = accuse_de_depot;
        Accuse_de_reception = accuse_de_reception;
        Offre_acceptee = offre_acceptee;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getAccuse_de_depot() {
        return Accuse_de_depot;
    }

    public void setAccuse_de_depot(int accuse_de_depot) {
        Accuse_de_depot = accuse_de_depot;
    }

    public int getAccuse_de_reception() {
        return Accuse_de_reception;
    }

    public void setAccuse_de_reception(int accuse_de_reception) {
        Accuse_de_reception = accuse_de_reception;
    }

    public int getOffre_acceptee() {
        return Offre_acceptee;
    }

    public void setOffre_acceptee(int offre_acceptee) {
        Offre_acceptee = offre_acceptee;
    }

    public Date getDate_proposee() {
        return Date_proposee;
    }

    public void setDate_proposee(Date date_proposee) {
        Date_proposee = date_proposee;
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
        stringBuilder.append("Contrat : ");
        stringBuilder.append(getID());
        stringBuilder.append(", Status : ");
        stringBuilder.append(getStatus());
        stringBuilder.append(", Offre concerené : ");
        stringBuilder.append(getOffre_acceptee());
        return stringBuilder.toString();
    }
}
