package groupei.al.blablacar.Entities;

public class Contrat {
    private int ID;
    private String Status;
    private int Accuse_de_depot;
    private int Accuse_de_reception;
    private Offre Offre_acceptee;

    public Contrat(int ID, String status, int accuse_de_depot, int accuse_de_reception, Offre offre_acceptee) {
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

    public Offre getOffre_acceptee() {
        return Offre_acceptee;
    }

    public void setOffre_acceptee(Offre offre_acceptee) {
        Offre_acceptee = offre_acceptee;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Contrat : ");
        stringBuilder.append(getID());
        stringBuilder.append(", Status : ");
        stringBuilder.append(getStatus());
        stringBuilder.append(", Offre concerené : ");
        stringBuilder.append(getOffre_acceptee().getID());
        return stringBuilder.toString();
    }
}
