package groupei.al.blablacar.Tools;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.Entities.Contrat;

public class JSONSerializer {
    static private JSONSerializer instance;

    private JSONSerializer() {}

    static public JSONSerializer getInstance() {
        if (instance == null) {
            instance = new JSONSerializer();
        }
        return instance;
    }

    static public JSONObject getConnexionJSON(String email, String mdp) {
        //int passwordHash = mdp.hashCode();
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("email", email);
            json_body.put("password", mdp);
            json.put("action", "Connexion");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    static public JSONObject getInscriptionJSON(String email, String mdp, String nom, String prenom, String date_de_naissance, String tel) {
        //int passwordHash = mdp.hashCode();
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("email", email);
            json_body.put("password", mdp);
            json_body.put("name", nom);
            json_body.put("firstname", prenom);
            json_body.put("birthday", date_de_naissance);
            json_body.put("phone_number", tel);
            json.put("action", "Inscription");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject getRechercheJSON(String depart, String arrivee, String bagage) {
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("departure_town", depart);
            json_body.put("arrival_town", arrivee);
            json_body.put("bagage", bagage);
            json.put("action", "RechercheAnnonce");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    public static JSONObject getUpdateContratJSON(Contrat c,String code) {
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("id_contract",c.getID());
            json_body.put("preuve",code);
            json.put("action", "UpdateContrat");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject getCreateOfferJSON(int id_annonce, int id, String dispo) {
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("ad_id", id_annonce);
            json_body.put("user_id", id);
            json_body.put("proposed_date", dispo);
            json.put("action", "CreationOffre");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject seeOffersJSON(int id) {
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("user_id", id);
            json.put("action", "VoirOffres");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject seeContratsJSON(String email) {
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("user_email", email);
            json.put("action", "VoirContrats");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    public static JSONObject seeHistoriqueContratsJSON(String email) {
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("user_email", email);
            json.put("action", "HistoriqueContrats");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject updateContratJSON(int id_contract, int preuve) {
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("id_contract", id_contract);
            json_body.put("preuve", preuve);
            json.put("action", "UpdateContrat");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject updateSoldeJSON(String user_email) {
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("user_email", user_email);
            json.put("action", "UpdateAmount");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
