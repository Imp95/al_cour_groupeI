package groupei.al.blablacar.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import groupei.al.blablacar.Entities.Adresse;
import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.Entities.Offre;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.OffreAdapter;

public class OffresActivity extends AppCompatActivity {

    RecyclerView liste;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offres);
        liste = (RecyclerView) findViewById(R.id.listOffre);
        liste.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        liste.setLayoutManager(mLayoutManager);
        List<Offre> myDataset = new ArrayList<>();
        //test stuff
        Utilisateur user= new Utilisateur("test","test","test",new Date(),"test",0);
        Annonce annonce = new Annonce(0, new Date(), new Date(),"test",0,0,user
                ,new Adresse(0,"test","test","test","test"),new Adresse(0,"test","test","test","test")
        ,new LinkedList<Offre>());
        Offre offre = new Offre(0,new Date(),"test",annonce,user);
        myDataset.add(offre);
        myDataset.add(offre);
        myDataset.add(offre);myDataset.add(offre);
        myDataset.add(offre);
        myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);myDataset.add(offre);

        //stop test stuff
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
