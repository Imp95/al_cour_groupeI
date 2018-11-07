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
        final Utilisateur user = (Utilisateur) bundle.get("user");

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
