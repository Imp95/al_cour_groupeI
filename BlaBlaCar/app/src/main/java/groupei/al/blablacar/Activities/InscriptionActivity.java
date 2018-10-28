package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import groupei.al.blablacar.R;

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
        Intent intent = new Intent(this, AcceuilActivity.class);
        startActivity(intent);
    }
}
