package groupei.al.blablacar.Tools;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import groupei.al.blablacar.Activities.OffreViewActivity;
import groupei.al.blablacar.Entities.Contrat;
import groupei.al.blablacar.R;

public class ContratAdapter extends RecyclerView.Adapter<ContratAdapter.MyViewHolder> {
    private List<Contrat> mDataset;
    private Activity parent;
    private Boolean historique;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout mView;
        public TextView departTextField,ariveeTextField,payTextField,dateTextField;
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
            addToPanierButton = (Button) mView.findViewById(R.id.addToPanierButton3);
            codeField = (EditText) mView.findViewById(R.id.CodeField);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContratAdapter(List<Contrat> myDataset, Boolean h, Activity activity) {
        mDataset = myDataset;
        parent = activity;
        historique = h;
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
        Contrat contrat = mDataset.get(position);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm");
        holder.departTextField.setText("Depart : " + contrat.getAdresse_depart());
        holder.ariveeTextField.setText("Arrivee : " + contrat.getAdresse_arrivee());
        holder.payTextField.setText("Paiment : " + contrat.getPaiement()+" points");
        holder.dateTextField.setText("Date : " + format.format(contrat.getDate_proposee()));
        if (!historique) {
            holder.addToPanierButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int x = Integer.parseInt(holder.codeField.getText().toString());
                    if (x >= 100000000 && x <= 999999999) {
                        System.out.println("code : " + x);
                    }
                }
            });
        } else {
            holder.codeField.setVisibility(View.INVISIBLE);
            holder.addToPanierButton.setVisibility(View.INVISIBLE);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}