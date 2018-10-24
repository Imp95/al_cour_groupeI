package groupei.al.blablacar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    LoginHandler loginHandler;
    LoginToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Methode appeler a la création du l'app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginHandler = new LoginHandlerMock();




        Button connexion = (Button) findViewById(R.id.connexion);//Récuperer un button
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityConnexion(view);
            }
        });

        SharedPreferences mPrefs = getSharedPreferences("Blablacar", 0);
        String tmp = mPrefs.getString("TokenMail",null);
        if(tmp!=null){
            long tmp2=mPrefs.getLong("TokenTime",0);
            if(System.currentTimeMillis()<tmp2) {
                token = new LoginToken(tmp, tmp2);
                changerActivityConnexion(connexion);
            }
        }

        Button inscription = (Button) findViewById(R.id.inscription);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityInscription(view);
            }
        });
        // this.finish(); Comment fermé l'app
    }

    @Override
    protected void onPause(){ //Methode appeler lorsque qu'on quitte l'app sans la refermer
        super.onPause();

    }
    @Override
    protected void onResume(){ //Methode appeler lorsque qu'on reviens sur l'app, est inclus dans le onCreate
        super.onResume();
    }

    public void changerActivityConnexion(View view) {
        /*Intent intent = new Intent(this, AnotherActivity.class);
        int x =10;
        intent.putExtra("key", x); //faire passer des parametre
        startActivity(intent);*/
        EditText mail = (EditText) findViewById(R.id.email);
        EditText mdp = (EditText) findViewById(R.id.mdp);
        token = loginHandler.login(mail.getText().toString(),mdp.getText().toString());
        if(token==null)
            return;
        SharedPreferences mPrefs = getSharedPreferences("Blablacar", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("TokenMail",token.getEmail());
        mEditor.putLong("TokenTime",token.getExpire());
        Intent intent = new Intent(this, AcceuilActivity.class);
        startActivity(intent);
    }

    public void changerActivityInscription(View view) {
        Intent intent = new Intent(this, InscriptionActivity.class);
        startActivity(intent);
    }
}
