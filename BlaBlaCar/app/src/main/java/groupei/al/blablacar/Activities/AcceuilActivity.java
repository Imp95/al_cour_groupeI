package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import groupei.al.blablacar.R;

public class AcceuilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        Button chercher = (Button) findViewById(R.id.chercher);
        chercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityChercher(view);
            }
        });

        Button offres = (Button) findViewById(R.id.offres);
        offres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityOffres(view);
            }
        });

        Button contrats = (Button) findViewById(R.id.contrats);
        contrats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityContrats(view);
            }
        });

        Button historique = (Button) findViewById(R.id.historique);
        historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityHistorique(view);
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

    public void changerActivityChercher(View view) {
        Intent intent = new Intent(this, RechercheActivity.class);
        startActivity(intent);
    }

    public void changerActivityOffres(View view) {
        Intent intent = new Intent(this, OffresActivity.class);
        startActivity(intent);
    }

    public void changerActivityContrats(View view) {
        Intent intent = new Intent(this, ContratsActivity.class);
        startActivity(intent);
    }

    public void changerActivityHistorique(View view) {
        Intent intent = new Intent(this, ContratsActivity.class);
        intent.putExtra("titre", "Historique des contrats"); //faire passer des parametre
        startActivity(intent);
    }

}
