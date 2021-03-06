package groupei.al.blablacar.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import groupei.al.blablacar.Entities.Info;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.JSONSerializer;
import groupei.al.blablacar.Tools.RequestHandler;

public class RechercheActivity extends AppCompatActivity {
    RequestHandler requestHandler;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        requestHandler = RequestHandler.getInstance(getApplicationContext());
        Info.getInstance();

        url = Info.getUrl();

        Button rechercher = (Button) findViewById(R.id.rechercher);
        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changerActivityConnexion(view);
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

    public void changerActivityConnexion(View view) {
        EditText depart = (EditText) findViewById(R.id.depart);
        EditText arrivee = (EditText) findViewById(R.id.arrivee);
        EditText bagage = (EditText) findViewById(R.id.dimension);

        JSONObject js = JSONSerializer.getRechercheJSON(depart.getText().toString(), arrivee.getText().toString(), bagage.getText().toString());
        final Activity act = this;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("recherche", response.toString());
                        try {
                            if(!response.get("body").toString().equals("[]")) {
                                Intent intent = new Intent(act, ChoixAnnoncesActivity.class);
                                intent.putExtra("annonces", response.get("body").toString()); // Passage du JSONArray en String
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("recherche", "Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "/application/json; charset=utf-8");
                return headers;
            }
        };

        System.out.println(jsonObjReq.toString());
        requestHandler.addToRequestQueue(jsonObjReq);
    }
}
