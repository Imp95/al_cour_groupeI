package groupei.al.blablacar.Tools;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import groupei.al.blablacar.Activities.AcceuilActivity;
import groupei.al.blablacar.Activities.OffreViewActivity;
import groupei.al.blablacar.Entities.Contrat;
import groupei.al.blablacar.Entities.Info;
import groupei.al.blablacar.Entities.Utilisateur;
import groupei.al.blablacar.R;

public class ContratAdapter extends RecyclerView.Adapter<ContratAdapter.MyViewHolder> {
    private List<Contrat> mDataset;
    private Activity parent;
    private Boolean historique;
    RequestHandler requestHandler;
    String url;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout mView;
        public TextView departTextField, ariveeTextField, payTextField, dateTextField, statusField;
        public Button addToPanierButton;
        public EditText codeField;
        public MyViewHolder(RelativeLayout v) {
            super(v);
            mView = v;
            init();
        }

        private void init() {
            departTextField = (TextView) mView.findViewById(R.id.departTextField3);
            ariveeTextField = (TextView) mView.findViewById(R.id.ariveeTextField3);
            payTextField = (TextView) mView.findViewById(R.id.payTextField3);
            dateTextField = (TextView) mView.findViewById(R.id.dateTextField3);
            statusField = (TextView) mView.findViewById(R.id.StatusField4);
            addToPanierButton = (Button) mView.findViewById(R.id.addToPanierButton3);
            codeField = (EditText) mView.findViewById(R.id.CodeField);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContratAdapter(List<Contrat> myDataset, Boolean h, Activity activity, RequestHandler r) {
        mDataset = myDataset;
        parent = activity;
        historique = h;
        requestHandler = r;
        Info.getInstance();
        url = Info.getUrl();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContratAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contrat_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Contrat contrat = mDataset.get(position);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm");
        holder.departTextField.setText("Depart : " + contrat.getAdresse_depart());
        holder.ariveeTextField.setText("Arrivee : " + contrat.getAdresse_arrivee());
        holder.payTextField.setText("Paiment : " + contrat.getPaiement()+" points");
        holder.dateTextField.setText("Date : " + format.format(contrat.getDate_proposee()));
        if (!historique) {
            if (contrat.getStatus() == 0) {
                holder.statusField.setText("Status: Colis en attente");
            }
            if (contrat.getStatus() == 1) {
                holder.statusField.setText("Status: En cours d'acheminement");
            }
            holder.addToPanierButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = Integer.parseInt(holder.codeField.getText().toString());
                    if (x >= 100000000 && x <= 999999999) {
                        System.out.println("code : " + x);
                        changerActivityConnexion(position, x, holder);
                        System.out.println("Status du contrat: " + contrat.getStatus());
                    }
                }
            });
        } else {
            holder.statusField.setText("Status: Contrat terminé");
            holder.codeField.setVisibility(View.INVISIBLE);
            holder.addToPanierButton.setVisibility(View.INVISIBLE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void changerActivityConnexion(final int position, int code, final MyViewHolder holder) {
        final Contrat contrat = mDataset.get(position);

        JSONObject js = JSONSerializer.updateContratJSON(contrat.getID(), code);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("update du contrat", response.toString());

                        try {
                            JSONObject body = response.getJSONObject("body");
                            contrat.setStatus(contrat.getStatus() + 1);
                            if (contrat.getStatus() == 1) {
                                holder.statusField.setText("Status: En cours d'acheminement");
                            }
                            if (contrat.getStatus() == 2) {
                                holder.statusField.setText("Status: Contrat terminé");
                                holder.codeField.setVisibility(View.INVISIBLE);
                                holder.addToPanierButton.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("update du contrat", "Error: " + error.getMessage());
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