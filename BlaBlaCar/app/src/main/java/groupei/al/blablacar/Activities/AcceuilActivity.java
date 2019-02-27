package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import groupei.al.blablacar.Entities.Info;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.JSONSerializer;
import groupei.al.blablacar.Tools.RequestHandler;

public class AcceuilActivity extends AppCompatActivity {
    RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        requestHandler = RequestHandler.getInstance(getApplicationContext());
        Info.getInstance();
        updateScore();

        TextView nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText("" + Info.getUser().getSolde());
        Button chercher = (Button) findViewById(R.id.chercher);
        chercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityChercher(view);
            }
        });

        Button offres = (Button) findViewById(R.id.offres);
        offres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityOffres(view);
            }
        });

        Button contrats = (Button) findViewById(R.id.contrats);
        contrats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityContrats(view);
            }
        });

        Button historique = (Button) findViewById(R.id.historique);
        historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityHistorique(view);
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

    public void changerActivityChercher(View view) {
        Intent intent = new Intent(this, RechercheActivity.class);
        startActivity(intent);
    }

    public void changerActivityOffres(View view) {
        Intent intent = new Intent(this, OffresActivity.class);
        startActivity(intent);
    }

    public void changerActivityContrats(View view) {
        Intent intent = new Intent(this, ContratsActivity.class);
        startActivity(intent);
    }

    public void changerActivityHistorique(View view) {
        Intent intent = new Intent(this, ContratsActivity.class);
        intent.putExtra("titre", "Historique des contrats");
        startActivity(intent);
    }

    private void updateScore(){
        Info.getInstance();
        String url = Info.getUrl();
        JSONObject js=JSONSerializer.updateSoldeJSON(Info.getUser().getEmail());
        JsonObjectRequest jsonObjReq =  new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("recu: " + response.toString());
                        try {
                            JSONObject body = (JSONObject) response.get("body");
                            int solde = (int) body.get("amount");
                            System.out.println("Points user: " + solde);
                            Info.getUser().setSolde(solde);
                            TextView nombre = (TextView) findViewById(R.id.nombre);
                            nombre.setText("" + Info.getUser().getSolde());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("UpdateSolde", "Error: " + error.getMessage());
            }
        });

        requestHandler.addToRequestQueue(jsonObjReq);
    }

}
