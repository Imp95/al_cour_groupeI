package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import groupei.al.blablacar.Entities.Contrat;
import groupei.al.blablacar.Tools.JSONSerializer;

public class ContratViewActivity extends AppCompatActivity {

    private Contrat contrat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        contrat= (Contrat) intent.getSerializableExtra("contrat");
    }

    private void sendValidationCode(){
        JSONObject js=JSONSerializer.getUpdateContratJSON(contrat,"");
        String url ="";
        JsonObjectRequest jsonObjReq =  new JsonObjectRequest(Request.Method.POST, url, js,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("UpdateContrats", "Error: " + error.getMessage());
                    }
                });
    }

}
