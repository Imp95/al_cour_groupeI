package groupei.al.blablacar.Tools;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
            json_body.put("contrat",c.getID());
            json_body.put("code",code);
            json.put("action", "UpdateContrats");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
