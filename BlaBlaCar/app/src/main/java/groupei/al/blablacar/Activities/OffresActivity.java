package groupei.al.blablacar.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.MyAdapter;

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

        /*mAdapter = new MyAdapter(myDataset);
        liste.setAdapter(mAdapter);*/




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
