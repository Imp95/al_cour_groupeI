package groupei.al.blablacar.Entities;

import java.io.Serializable;
import java.util.Date;

public class Offre implements Serializable {
    private int ID;
    private Date Date_proposee;
    private String Status;
    private Annonce Annonce_repondu;
    private Utilisateur Transporteur;

    public Offre(int ID, Date date_proposee, String status, Annonce annonce_repondu, Utilisateur transporteur) {
        this.ID = ID;
        Date_proposee = date_proposee;
        Status = status;
        Annonce_repondu = annonce_repondu;
        Transporteur = transporteur;
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

    public Annonce getAnnonce_repondu() {
        return Annonce_repondu;
    }

    public void setAnnonce_repondu(Annonce annonce_repondu) {
        Annonce_repondu = annonce_repondu;
    }

    public Utilisateur getTransporteur() {
        return Transporteur;
    }

    public void setTransporteur(Utilisateur transporteur) {
        Transporteur = transporteur;
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
        stringBuilder.append(getAnnonce_repondu().getID());
        return stringBuilder.toString();
    }
}
