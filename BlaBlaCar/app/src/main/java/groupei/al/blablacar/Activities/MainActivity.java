package groupei.al.blablacar.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import groupei.al.blablacar.Entities.Info;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.Tools.JSONSerializer;
import groupei.al.blablacar.Tools.LoginHandler;
import groupei.al.blablacar.Tools.LoginHandlerMock;
import groupei.al.blablacar.Tools.LoginToken;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.RequestHandler;

public class MainActivity extends AppCompatActivity {

    LoginHandler loginHandler;
    LoginToken token;
    TextView error;
    RequestHandler requestHandler;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Methode appeler a la création du l'app
        super.onCreate(savedInstanceState);
        requestHandler = RequestHandler.getInstance(getApplicationContext());
        setContentView(R.layout.activity_main);
        loginHandler = new LoginHandlerMock();
        error = (TextView) findViewById(R.id.errorText);
        error.setTextColor(Color.rgb(255,0,0));
        url = "http://10.0.2.2:5555/receive_event";
        Info.getInstance();
        Info.setUrl(url);

        Button connexion = (Button) findViewById(R.id.connexion);//Récuperer un button
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityConnexion(view);
            }
        });

        SharedPreferences mPrefs = getSharedPreferences("Blablacar", 0);
        String tmp = mPrefs.getString("TokenMail",null);
        if(tmp!=null){
            long tmp2=mPrefs.getLong("TokenTime",0);
            if(System.currentTimeMillis()<tmp2) {
                token = new LoginToken(tmp, tmp2);
                changerActivityConnexion(connexion);
            }
        }

        Button inscription = (Button) findViewById(R.id.inscription);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityInscription(view);
            }
        });
        // this.finish(); Comment fermé l'app
    }

    @Override
    protected void onPause(){ //Methode appeler lorsque qu'on quitte l'app sans la refermer
        super.onPause();
    }

    @Override
    protected void onResume(){ //Methode appeler lorsque qu'on reviens sur l'app, est inclus dans le onCreate
        super.onResume();
    }

    // CHANGE ACTIVITY

    public void changerActivityConnexion(View view) {

        final EditText mail = (EditText) findViewById(R.id.email);
        EditText mdp = (EditText) findViewById(R.id.mdp);

        JSONObject js = JSONSerializer.getConnexionJSON(mail.getText().toString(), mdp.getText().toString());
        final Activity act = this;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("connexion", response.toString());
                        try {
                            JSONObject body = response.getJSONObject("body");

                            String dateTime = ((String) body.get("birthday"));
                            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
                            Date date = dateParser.parse(dateTime);
                            int id = body.getInt("id");
                            String email = body.getString("email");
                            int amount = body.getInt("amount");

                            Utilisateur user = new Utilisateur(id, email, body.getString("name"), body.getString("firstname"),
                                    date, body.getString("phone_number"), amount);
                            Info.setUser(user);

                            Intent intent = new Intent(act, AcceuilActivity.class);
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
                Log.d("connexion", "Error: " + error.getMessage());
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

    public void changerActivityInscription(View view) {
        Intent intent = new Intent(this, InscriptionActivity.class);
        startActivity(intent);
    }
}
