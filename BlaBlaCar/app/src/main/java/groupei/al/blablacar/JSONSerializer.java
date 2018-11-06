package groupei.al.blablacar;
import org.json.JSONException;
import org.json.JSONObject;

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
            json_body.put("mdp", mdp);
            json.put("action", "Connexion");
            json.put("body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    static public JSONObject getInscriptionJSON(String email, String mdp, String nom, String prenom, String date_de_naissance) {
        int passwordHash = mdp.hashCode();
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("email", email);
            json_body.put("mdp", passwordHash);
            json_body.put("nom", nom);
            json_body.put("prenom", prenom);
            json_body.put("date_de_naissance", date_de_naissance);
            json.put("Action", "inscription");
            json.put("Body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
