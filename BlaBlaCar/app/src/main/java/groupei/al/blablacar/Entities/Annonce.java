package groupei.al.blablacar.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

public class Annonce implements Serializable {

    private int ID;
    private Date Date_Debut;
    private Date Date_Fin;
    private String Bagage;
    private int Paiement;
    private String Adresse_depart;  // A changer pour Address
    private String Adresse_arrivee; // A changer pour Address

    public Annonce(int ID, Date date_Debut, Date date_Fin, String bagage, int paiement, String adresse_depart, String adresse_arrivee) {
        this.ID = ID;
        Date_Debut = date_Debut;
        Date_Fin = date_Fin;
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
        stringBuilder.append("Départ : ");stringBuilder.append(getAdresse_depart());stringBuilder.append('\n');
        stringBuilder.append("Arrivé : ");stringBuilder.append(getAdresse_arrivee());stringBuilder.append('\n');
        stringBuilder.append("Dimension bagage : ");stringBuilder.append(getBagage());stringBuilder.append('\n');
        stringBuilder.append("Paiment : ");stringBuilder.append(getPaiement());stringBuilder.append('\n');
        stringBuilder.append("Date : Entre le ");stringBuilder.append(getDate_Debut());stringBuilder.append(" et le ");;stringBuilder.append(getDate_Fin());
        return stringBuilder.toString();
    }
}
