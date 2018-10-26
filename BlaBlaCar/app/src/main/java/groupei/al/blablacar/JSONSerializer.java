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
        int passwordHash = mdp.hashCode();
        JSONObject json_body = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            json_body.put("email", email);
            json_body.put("mdp", passwordHash);
            json.put("Action", "connexion");
            json.put("Body", json_body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //json.put("mdp", passwordHash);
        System.out.println(json.toString());
        return json;
    }
}
