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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import groupei.al.blablacar.Entities.Adresse;
import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.Entities.Contrat;
import groupei.al.blablacar.Entities.Info;
import groupei.al.blablacar.Entities.Offre;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.ContratAdapter;
import groupei.al.blablacar.Tools.JSONSerializer;
import groupei.al.blablacar.Tools.RequestHandler;

public class ContratsActivity extends AppCompatActivity {

    RecyclerView liste;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RequestHandler requestHandler;
    String url;
    Utilisateur user;
    Boolean historique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrats);
        Info.getInstance();
        url = Info.getUrl();
        user = Info.getUser();
        historique = false;
        requestHandler = RequestHandler.getInstance(getApplicationContext());
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            TextView titre = (TextView) findViewById(R.id.titre_contrats);
            String new_name = (String) bundle.get("titre");
            titre.setText(new_name);
            historique = true;
        }
        liste = (RecyclerView) findViewById(R.id.listContrats);
        liste.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        liste.setLayoutManager(mLayoutManager);
        final List<Contrat> myDataset = new ArrayList<>();
        //test stuff
        /*Annonce annonce = new Annonce(0, new Date(), new Date(),"test",0,0,user
                ,new Adresse(0,"test","test","test","test"),new Adresse(0,"test","test","test","test")
                ,new LinkedList<Offre>());*/
        /*Annonce annonce = new Annonce(0, new Date(), new Date(),"test",0, "depart", "arrivee");
        Offre offre = new Offre(0,new Date(),"test",annonce,user);
        Contrat contrat = new Contrat(0,"test",0,0,offre);
        myDataset.add(contrat);
        myDataset.add(contrat);
        myDataset.add(contrat);
        myDataset.add(contrat);myDataset.add(contrat);myDataset.add(contrat);myDataset.add(contrat);myDataset.add(contrat);*/
        //stop test stuff
        JSONObject js = new JSONObject();
        if (historique) {
            js = JSONSerializer.seeHistoriqueContratsJSON(user.getEmail());
        } else {
            js = JSONSerializer.seeContratsJSON(user.getEmail());
        }
        JsonObjectRequest jsonObjReq =  new JsonObjectRequest(Request.Method.POST, url, js,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray contracts = response.getJSONArray("body");
                            for(int i=0;i<contracts.length();i++){
                                JSONObject c = contracts.getJSONObject(i);
                                String dateTime = c.getString("proposed_date");
                                SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.FRANCE);
                                Date date = dateParser.parse(dateTime);
                                myDataset.add(new Contrat(c.getInt("id"),c.getString("status"),c.getInt("offer_id"),
                                        date,c.getInt("payment"),c.getString("departure_address"),c.getString("arrival_address")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("seeContrats", "Error: " + error.getMessage());
            }
        });
        requestHandler.addToRequestQueue(jsonObjReq);
        mAdapter = new ContratAdapter(myDataset, historique, this);
        liste.setAdapter(mAdapter);


    }

    @Override
    protected void onPause(){ //Methode appeler lorsque qu'on quitte l'app sans la refermer
        super.onPause();

    }
    @Override
    protected void onResume(){ //Methode appeler lorsque qu'on reviens sur l'app, est inclus dans le onCreate
        super.onResume();
    }

}
