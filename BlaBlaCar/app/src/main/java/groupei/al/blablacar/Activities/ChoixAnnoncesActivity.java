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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.Entities.Offre;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.AnnonceAdapter;

public class ChoixAnnoncesActivity extends AppCompatActivity {

    RecyclerView liste;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_annonces);
        liste = (RecyclerView) findViewById(R.id.listeAnnonces);
        liste.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        liste.setLayoutManager(mLayoutManager);
        List<Annonce> listAnnonces = new ArrayList<>();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Utilisateur user = (Utilisateur) bundle.get("user");
        JSONArray jsonAnnonces = new JSONArray();

        try {
            jsonAnnonces = new JSONArray((String) bundle.get("annonces"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonAnnonces.length(); i++) {
            try {
                JSONObject annonce = jsonAnnonces.getJSONObject(i);
                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.FRANCE);

                JSONObject jsonDate = annonce.getJSONObject("departure_date");
                String dateTime = jsonDate.get("date").toString();
                Date depart = dateParser.parse(dateTime);
                jsonDate = annonce.getJSONObject("arrival_date");
                dateTime = jsonDate.get("date").toString();
                Date arrivee = dateParser.parse(dateTime);

                Annonce tmp = new Annonce(annonce.getInt("id"),
                        depart, arrivee,
                        annonce.getString("bagage"), annonce.getInt("payment"),
                        annonce.getString("departure_address"), annonce.getString("arrival_address")
                );
                listAnnonces.add(tmp);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //stop test stuff
        Log.d("test_annonces", String.valueOf(listAnnonces.size()));
        mAdapter = new AnnonceAdapter(listAnnonces,this);
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
