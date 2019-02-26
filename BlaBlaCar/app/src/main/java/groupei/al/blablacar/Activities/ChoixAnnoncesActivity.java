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
import android.widget.TextView;

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

import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.Entities.Offre;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.AnnonceAdapter;

public class ChoixAnnoncesActivity extends AppCompatActivity {

    RecyclerView liste;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Button cart;

    LinkedList<Annonce> panier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_annonces);
        liste = (RecyclerView) findViewById(R.id.listeAnnonces);
        liste.setHasFixedSize(true);
        cart = (Button) findViewById(R.id.cart);
        mLayoutManager = new LinearLayoutManager(this);
        liste.setLayoutManager(mLayoutManager);
        List<Annonce> listAnnonces = new ArrayList<>();
        panier = new LinkedList<>();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        JSONArray jsonAnnonces = new JSONArray();

        try {
            jsonAnnonces = new JSONArray((String) bundle.get("annonces"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonAnnonces.length(); i++) {
            try {
                JSONObject annonce = jsonAnnonces.getJSONObject(i);
                SimpleDateFormat dateParser = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);

                String depart = annonce.getString("departure_date");
                String arrivee = annonce.getString("arrival_date");

                Annonce tmp = new Annonce(annonce.getInt("id"),
                        dateParser.parse(depart), dateParser.parse(arrivee),
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
        mAdapter = new AnnonceAdapter(listAnnonces,panier,this);
        liste.setAdapter(mAdapter);

        final Button cart = (Button) findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkIfTooBig(panier)){
                    return;
                }
                if (panier.size() != 0) {
                    changerActivityCreateOrder(view, panier);
                }
            }
        });
    }

    private boolean checkIfTooBig(LinkedList<Annonce> panier) {
        return false;
    }

    private void changerActivityCreateOrder(View view, LinkedList<Annonce> panier) {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        intent.putExtra("panier", panier);
        startActivity(intent);
    }

    @Override
    protected void onPause(){ //Methode appeler lorsque qu'on quitte l'app sans la refermer
        super.onPause();

    }
    @Override
    protected void onResume(){ //Methode appeler lorsque qu'on reviens sur l'app, est inclus dans le onCreate
        super.onResume();
    }

    public Button getCart(){return cart;}
}
