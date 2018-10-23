package groupei.al.blablacar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Methode appeler a la création du l'app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);//Récuperer un buttong
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivity(view);
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

    public void changerActivity(View view) {
        Intent intent = new Intent(this, AnotherActivity.class);
        int x =10;
        intent.putExtra("key", x); //faire passer des parametre
        startActivity(intent);
    }


}
