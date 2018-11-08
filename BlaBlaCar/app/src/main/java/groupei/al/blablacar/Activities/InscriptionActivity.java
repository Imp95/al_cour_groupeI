package groupei.al.blablacar.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.JSONSerializer;
import groupei.al.blablacar.Tools.RequestHandler;

public class InscriptionActivity extends AppCompatActivity {
    RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Methode appeler a la création du l'app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        requestHandler = RequestHandler.getInstance(getApplicationContext());

        Button confirmer = (Button) findViewById(R.id.confirmer);
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityAcceuil(view);
            }
        });
    }

    @Override
    protected void onPause(){ //Methode appeler lorsque qu'on quitte l'app sans la refermer
        super.onPause();

    }
    @Override
    protected void onResume(){ //Methode appeler lorsque qu'on reviens sur l'app, est inclus dans le onCreate
        super.onResume();
    }

    public void changerActivityAcceuil(View view) {
        EditText mail = (EditText) findViewById(R.id.email);
        EditText mdp = (EditText) findViewById(R.id.mdp);
        EditText mdp2 = (EditText) findViewById(R.id.mdp2);
        EditText nom = (EditText) findViewById(R.id.nom);
        EditText prenom = (EditText) findViewById(R.id.prenom);
        EditText tel = (EditText) findViewById(R.id.tel);
        EditText naissance = (EditText) findViewById(R.id.naissance);

        if (mdp.getText().toString().equals(mdp2.getText().toString())) {
            JSONObject js = JSONSerializer.getInscriptionJSON(mail.getText().toString(), mdp.getText().toString(), nom.getText().toString(), prenom.getText().toString(),
                    naissance.getText().toString(), tel.getText().toString());
            String url = "http://192.168.1.82:80/service-persistance/public/index.php/receive_event";
            final Activity act = this;
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                    Request.Method.POST, url, js,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("inscription", response.toString());
                            try {
                                System.out.println(response.get("body").getClass().getName());
                                JSONObject body = response.getJSONObject("body");
                                System.out.println(body.get("birthday").getClass().getName());
                                String dateTime = ((String) body.get("birthday"));
                                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
                                Date date = dateParser.parse(dateTime);
                                Utilisateur user = new Utilisateur(body.getString("email"), body.getString("name"), body.getString("firstname"),
                                        date, body.getString("phone_number"), body.getInt("amount"));
                                Intent intent = new Intent(act, AcceuilActivity.class);
                                intent.putExtra("user", user); //faire passer des parametres
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("inscription", "Error: " + error.getMessage());
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "/application/json; charset=utf-8");
                    return headers;
                }
            };

            System.out.println(jsonObjReq.toString());
            requestHandler.addToRequestQueue(jsonObjReq);
        }
    }
}
