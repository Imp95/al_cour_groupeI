package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.AnnonceAdapterValid;
import groupei.al.blablacar.Tools.JSONSerializer;
import groupei.al.blablacar.Tools.RequestHandler;

public class CreateOrderActivity extends AppCompatActivity {
    RequestHandler requestHandler;
    Button valider;
    Button refuser;
    TextView id;
    TextView date;
    TextView bagage;
    TextView paiement;
    TextView depart;
    TextView arrivee;
    EditText dispo;
    List<Annonce> panier;
    Annonce annonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        requestHandler = RequestHandler.getInstance(getApplicationContext());
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final Utilisateur user = (Utilisateur) bundle.get("user");
        panier = (List<Annonce>) getIntent().getSerializableExtra("panier");
        Log.d("size-panier", String.valueOf(panier.size()));
        annonce = panier.get(0);
        panier.remove(0);

        valider = (Button) findViewById(R.id.valider);
        refuser = (Button) findViewById(R.id.refuser);
        id = (TextView) findViewById(R.id.id);
        date = (TextView) findViewById(R.id.date);
        bagage = (TextView) findViewById(R.id.bagage);
        paiement = (TextView) findViewById(R.id.paiement);
        depart = (TextView) findViewById(R.id.depart);
        arrivee = (TextView) findViewById(R.id.arrivee);

        id.setText(id.getText().toString() + annonce.getID());
        date.setText(date.getText().toString() + annonce.getDate_Debut() + "\net le " + annonce.getDate_Fin());
        bagage.setText(bagage.getText().toString() + "\n" + annonce.getBagage());
        paiement.setText(paiement.getText().toString() + "\n" + annonce.getPaiement());
        depart.setText(depart.getText().toString() + annonce.getAdresse_depart());
        arrivee.setText(arrivee.getText().toString() + annonce.getAdresse_arrivee());

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityValider(view, panier, user, annonce);
            }
        });

        refuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityRefuser(view, panier, user);
            }
        });
    }

    private void changerActivityValider(View view, List<Annonce> panier, Utilisateur user, Annonce annonce) {
        // A implementer quand la route sera disponible
        EditText dispo = (EditText) findViewById(R.id.dispo);
        JSONObject js = JSONSerializer.getCreateOfferJSON(annonce.getID(), user.getEmail(), dispo.getText().toString());
        String url = "http://192.168.0.42:80/receive_event";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("createOffer", response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("createOffer", "Error: " + error.getMessage());
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
        changerActivityRefuser(view, panier, user);
    }

    private void changerActivityRefuser(View view, List<Annonce> panier, Utilisateur user) {
        if (panier.size() == 0) {
            Intent intent = new Intent(this, AcceuilActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, CreateOrderActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("panier", (Serializable) panier);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

    }
    @Override
    protected void onResume(){ //Methode appeler lorsque qu'on reviens sur l'app, est inclus dans le onCreate
        super.onResume();
    }


}