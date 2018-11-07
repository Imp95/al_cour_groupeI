package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;

public class AcceuilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        TextView nombre = (TextView) findViewById(R.id.nombre);
        final Utilisateur user = (Utilisateur) bundle.get("user");
        nombre.setText("" + user.getSolde());

        Button chercher = (Button) findViewById(R.id.chercher);
        chercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityChercher(view, user);
            }
        });

        Button offres = (Button) findViewById(R.id.offres);
        offres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityOffres(view, user);
            }
        });

        Button contrats = (Button) findViewById(R.id.contrats);
        contrats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityContrats(view, user);
            }
        });

        Button historique = (Button) findViewById(R.id.historique);
        historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityHistorique(view, user);
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

    public void changerActivityChercher(View view, Utilisateur user) {
        Intent intent = new Intent(this, RechercheActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void changerActivityOffres(View view, Utilisateur user) {
        Intent intent = new Intent(this, OffresActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void changerActivityContrats(View view, Utilisateur user) {
        Intent intent = new Intent(this, ContratsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void changerActivityHistorique(View view, Utilisateur user) {
        Intent intent = new Intent(this, ContratsActivity.class);
        intent.putExtra("titre", "Historique des contrats");
        intent.putExtra("user", user);
        startActivity(intent);
    }

}
