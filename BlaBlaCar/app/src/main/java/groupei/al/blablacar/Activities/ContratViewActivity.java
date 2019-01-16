package groupei.al.blablacar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import groupei.al.blablacar.Entities.Contrat;
import groupei.al.blablacar.R;
import groupei.al.blablacar.Tools.JSONSerializer;

public class ContratViewActivity extends AppCompatActivity {

    private Contrat contrat;
    private TextView contratId;
    private RelativeLayout attfields;
    private TextView departTextField;
    private TextView ariveeTextField;
    private TextView dateTextField;
    private TextView payTextField;
    private TextView telClTextField;
    private TextView telDestTextField;
    private TextView statusfield;
    private EditText codeField;
    private Button validButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_contrat_view);
        Intent intent = this.getIntent();
        contrat= (Contrat) intent.getSerializableExtra("contrat");
        contratId = (TextView) findViewById(R.id.contratId);
        departTextField = (TextView) findViewById(R.id.departTextField);
        ariveeTextField = (TextView) findViewById(R.id.ariveeTextField);
        dateTextField = (TextView) findViewById(R.id.dateTextField);
        payTextField = (TextView) findViewById(R.id.payTextField);
        telClTextField = (TextView) findViewById(R.id.telClTextField);
        telDestTextField = (TextView) findViewById(R.id.telDestTextField);
        statusfield = (TextView) findViewById(R.id.statusfield);
        codeField = (EditText) findViewById(R.id.codeField);
        validButton = (Button) findViewById(R.id.validButton);
        contratId.setText("Contrat n°"+contrat.getID());
        /*departTextField.setText("Depart : "+contrat.getOffre_acceptee().getAnnonce_repondu().getAdresse_depart());
        ariveeTextField.setText("Arrivee : "+contrat.getOffre_acceptee().getAnnonce_repondu().getAdresse_arrivee());
        dateTextField.setText("Date : "+contrat.getOffre_acceptee().getDate_proposee());
        payTextField.setText("Paiment : "+contrat.getOffre_acceptee().getAnnonce_repondu().getPaiement());
        telClTextField.setText("Telephone client : "+contrat.getOffre_acceptee().getTransporteur().getTelephone());
        telDestTextField.setText("Telephone destinataire : "+contrat.getOffre_acceptee().getTransporteur().getTelephone());*/
        statusfield.setText("Status :\n"+contrat.getStatus());
        if(contrat.getStatus()!="2"){
            codeField.setVisibility(View.VISIBLE);
            validButton.setVisibility(View.VISIBLE);
        }else{
            codeField.setVisibility(View.GONE);
            validButton.setVisibility(View.GONE);
        }
        validButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendValidationCode();
                validButton.setVisibility(View.GONE);
            }
        });
    }

    private void sendValidationCode(){
        JSONObject js=JSONSerializer.getUpdateContratJSON(contrat,codeField.getText().toString());
        String url ="http://192.168.0.42:80/receive_event";
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
