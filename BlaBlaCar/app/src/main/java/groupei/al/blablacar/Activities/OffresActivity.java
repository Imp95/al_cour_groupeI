package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
import groupei.al.blablacar.Tools.JSONSerializer;
import groupei.al.blablacar.Tools.OffreAdapter;
import groupei.al.blablacar.Tools.RequestHandler;

public class OffresActivity extends AppCompatActivity {

    RecyclerView liste;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RequestHandler requestHandler;
    String url;
    Utilisateur user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Info.getInstance();
        setContentView(R.layout.activity_offres);
        requestHandler = RequestHandler.getInstance(getApplicationContext());
        liste = (RecyclerView) findViewById(R.id.listOffre);
        liste.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        liste.setLayoutManager(mLayoutManager);
        final List<Offre> myDataset = new ArrayList<>();
        //test stuff
        user = Info.getUser();
        url = Info.getUrl();
        /*Annonce annonce = new Annonce(0, new Date(), new Date(),"test",0,0,user
                ,new Adresse(0,"test","test","test","test"),new Adresse(0,"test","test","test","test")
        ,new LinkedList<Offre>());*/ // A modifier

        /*Annonce annonce = new Annonce(0, new Date(), new Date(), "0x0x0" ,0 , "depart", "arrivee");

        Offre offre = new Offre(0,new Date(),"test",annonce,user);
        myDataset.add(offre);
        myDataset.add(offre);
        myDataset.add(offre);myDataset.add(offre);
        myDataset.add(offre);
        myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);
        */
        //stop test stuff
        JSONObject js=JSONSerializer.seeOffersJSON(user.getId());
        JsonObjectRequest jsonObjReq =  new JsonObjectRequest(Request.Method.POST, url, js,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray offers = response.getJSONArray("body");
                            for(int i=0;i<offers.length();i++){
                                JSONObject o = offers.getJSONObject(i);
                                Log.d("dateOffresBug", o.getString("proposed_date"));
                                String dateTime = o.getString("proposed_date");
                                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.FRANCE);
                                Date date = dateParser.parse(dateTime);
                                myDataset.add(new Offre(o.getInt("id"), date, o.getString("status"), o.getInt("ad_id"), o.getInt("carrier_id"),
                                        o.getString("bagage"),o.getInt("payment"),o.getString("departure_address"),o.getString("arrival_address")));
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
                Log.d("seeOffers", "Error: " + error.getMessage());
            }
        });
        requestHandler.addToRequestQueue(jsonObjReq);
        mAdapter = new OffreAdapter(myDataset,this);
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
