package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import groupei.al.blablacar.Entities.Adresse;
import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.Entities.Contrat;
import groupei.al.blablacar.Entities.Offre;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.ContratAdapter;

public class ContratsActivity extends AppCompatActivity {

    RecyclerView liste;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrats);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            TextView titre = (TextView) findViewById(R.id.titre_contrats);
            String new_name = (String) bundle.get("titre");
            titre.setText(new_name);
        }
        liste = (RecyclerView) findViewById(R.id.listContrats);
        liste.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        liste.setLayoutManager(mLayoutManager);
        List<Contrat> myDataset = new ArrayList<>();
        //test stuff
        Utilisateur user= new Utilisateur("test","test","test",new Date(),"test",0);
        Annonce annonce = new Annonce(0, new Date(), new Date(),"test",0,0,user
                ,new Adresse(0,"test","test","test","test"),new Adresse(0,"test","test","test","test")
                ,new LinkedList<Offre>());
        Offre offre = new Offre(0,new Date(),"test",annonce,user);
        Contrat contrat = new Contrat(0,"test",0,0,offre);
        myDataset.add(contrat);
        myDataset.add(contrat);
        myDataset.add(contrat);
        myDataset.add(contrat);myDataset.add(contrat);myDataset.add(contrat);myDataset.add(contrat);myDataset.add(contrat);
        //stop test stuff
        mAdapter = new ContratAdapter(myDataset,this);
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
