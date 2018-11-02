package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.Checker;

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Methode appeler a la création du l'app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        Button confirmer = (Button) findViewById(R.id.confirmer);
        confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityAcceuil(view);
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

    public void changerActivityAcceuil(View view) {
        if (mock_inscription()) {
            Intent intent = new Intent(this, AcceuilActivity.class);
            startActivity(intent);
        }
    }

    private boolean mock_inscription() {
        Checker.getInstance();
        EditText mail = (EditText) findViewById(R.id.email);
        EditText mdp = (EditText) findViewById(R.id.mdp);
        EditText mdp2 = (EditText) findViewById(R.id.mdp2);
        EditText nom = (EditText) findViewById(R.id.nom);
        EditText prenom = (EditText) findViewById(R.id.prenom);
        EditText naissance = (EditText) findViewById(R.id.naissance);
        EditText tel = (EditText) findViewById(R.id.tel);
        try {
            return Checker.inscriptionIsValid(mail.getText().toString(), mdp.getText().toString(), mdp2.getText().toString(), naissance.getText().toString(), tel.getText().toString(), nom.getText().toString(), prenom.getText().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
