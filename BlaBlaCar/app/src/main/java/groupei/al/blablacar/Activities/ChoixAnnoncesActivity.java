package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

public class ChoixAnnoncesActivity extends AppCompatActivity {

    RecyclerView liste;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_annonces);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Utilisateur user = (Utilisateur) bundle.get("user");
        JSONArray jsonAnnonces = new JSONArray();
        try {
            jsonAnnonces = new JSONArray((String) bundle.get("annonces"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<Annonce> listAnnonces = new ArrayList<>();

        for (int i = 0; i < jsonAnnonces.length(); i++) {
            try {
                JSONObject annonce = jsonAnnonces.getJSONObject(i);
                SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.FRANCE);

                String dateTimeDepart = ((String) annonce.get("departure_date"));
                String dateTimeArrivee = ((String) annonce.get("arrival_date"));
                Date depart = dateParser.parse(dateTimeDepart);
                Date arrivee = dateParser.parse(dateTimeArrivee);

                listAnnonces.add(new Annonce(annonce.getInt("id"),
                        depart, arrivee,
                        annonce.getString("bagage"), annonce.getInt("payment"),
                        annonce.getString("departure_address"), annonce.getString("arrival_address")
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        liste = (RecyclerView) findViewById(R.id.listeAnnonces);
        liste.setHasFixedSize(true);
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
