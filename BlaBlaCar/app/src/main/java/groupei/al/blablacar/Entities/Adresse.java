package groupei.al.blablacar.Entities;

public class Adresse {
    private int Numero_de_Rue;
    private String Nom_De_Rue;
    private String Complement;
    private String Ville;
    private String Code_Postal;

    public Adresse(int numero_de_Rue, String nom_De_Rue, String complement, String ville, String code_Postal) {
        Numero_de_Rue = numero_de_Rue;
        Nom_De_Rue = nom_De_Rue;
        Complement = complement;
        Ville = ville;
        Code_Postal = code_Postal;
    }

    public int getNumero_de_Rue() {
        return Numero_de_Rue;
    }

    public void setNumero_de_Rue(int numero_de_Rue) {
        Numero_de_Rue = numero_de_Rue;
    }

    public String getNom_De_Rue() {
        return Nom_De_Rue;
    }

    public void setNom_De_Rue(String nom_De_Rue) {
        Nom_De_Rue = nom_De_Rue;
    }

    public String getComplement() {
        return Complement;
    }

    public void setComplement(String complement) {
        Complement = complement;
    }

    public String getVille() {
        return Ville;
    }

    public void setVille(String ville) {
        Ville = ville;
    }

    public String getCode_Postal() {
        return Code_Postal;
    }

    public void setCode_Postal(String code_Postal) {
        Code_Postal = code_Postal;
    }
}
