package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import groupei.al.blablacar.Tools.Checker;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Methode appeler a la création du l'app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginHandler = new LoginHandlerMock();
        //requestHandler = RequestHandler.getInstance(getApplicationContext());
        JSONSerializer.getInstance();
        error = (TextView) findViewById(R.id.errorText);
        error.setTextColor(Color.rgb(255,0,0));

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

    public void changerActivityConnexion(View view) {
        /*Intent intent = new Intent(this, AnotherActivity.class);
        int x =10;
        intent.putExtra("key", x); //faire passer des parametre
        startActivity(intent);*/
        EditText mail = (EditText) findViewById(R.id.email);
        EditText mdp = (EditText) findViewById(R.id.mdp);
        Checker.getInstance();
        /*JSONObject js = JSONSerializer.getConnexionJSON(mail.toString(), mdp.toString());
        String url ="http://192.168.0.1:4000/receive_event";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,url,js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("connexion", response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("connexion", "Error: " + error.getMessage());
            }}) {

             // Passing some request headers

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "/application/json; charset=utf-8");
                return headers;
            }
        };

        requestHandler.addToRequestQueue(jsonObjReq);
        */


        /*
        token = loginHandler.login(mail.getText().toString(),mdp.getText().toString());
        if(token==null){
            error.setText("Invalid login or password");
            return;
        }

        SharedPreferences mPrefs = getSharedPreferences("Blablacar", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("TokenMail",token.getEmail());
        mEditor.putLong("TokenTime",token.getExpire());
        */
        try {
            if (Checker.connexionIsValid(mail.getText().toString(), mdp.getText().toString())) {
                Intent intent = new Intent(this, AcceuilActivity.class);
                startActivity(intent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changerActivityInscription(View view) {
        Intent intent = new Intent(this, InscriptionActivity.class);
        startActivity(intent);
    }
}
