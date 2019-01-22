package groupei.al.blablacar.Tools;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import groupei.al.blablacar.Activities.OffreViewActivity;
import groupei.al.blablacar.Entities.Contrat;
import groupei.al.blablacar.R;

public class ContratAdapter extends RecyclerView.Adapter<ContratAdapter.MyViewHolder> {
    private List<Contrat> mDataset;
    private Activity parent;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout mView;
        public TextView departTextField,ariveeTextField,payTextField,dateTextField;
        public Button addToPanierButton;
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
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContratAdapter(List<Contrat> myDataset, Activity activity) {
        mDataset = myDataset;
        parent=activity;
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
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Contrat contrat = mDataset.get(position);
        /*holder.departTextField.setText("Depart : " + contrat.getAdresse_depart());
        holder.ariveeTextField.setText("Arrivee : " + contrat.getAdresse_arrivee());
        holder.payTextField.setText("Paiment : " + contrat.getPaiement()+" points");
        holder.dateTextField.setText("Date : " + contrat.getDate_proposee());*/
        holder.addToPanierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent,OffreViewActivity.class);
                intent.putExtra("contrat",mDataset.get(position));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}