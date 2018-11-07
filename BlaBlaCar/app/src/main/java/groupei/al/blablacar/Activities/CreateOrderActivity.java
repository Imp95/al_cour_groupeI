package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.LinkedList;

import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;

public class CreateOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final Utilisateur user = (Utilisateur) bundle.get("user");
        //LinkedList<Annonce> panier = (LinkedList<Annonce>) bundle.get("panier");
    }


}
