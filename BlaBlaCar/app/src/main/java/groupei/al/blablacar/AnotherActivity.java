package groupei.al.blablacar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Methode appeler a la création du l'app
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        TextView textView = (TextView) findViewById(R.id.text);
        Intent intent = this.getIntent();
        int x = intent.getIntExtra("key",0);
        textView.setText("x = "+x);

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
