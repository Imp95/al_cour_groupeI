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

import java.util.LinkedList;
import java.util.List;

import groupei.al.blablacar.Activities.AnnonceViewActivity;
import groupei.al.blablacar.Activities.ChoixAnnoncesActivity;
import groupei.al.blablacar.Activities.OffreViewActivity;
import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.R;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.MyViewHolder> {
    private List<Annonce> mDataset;
    private LinkedList<Annonce> panier;
    private ChoixAnnoncesActivity parent;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout mView;
        public TextView departTextField,ariveeTextField,payTextField,bagageTextField,dateTextField;
        public Button addToPanierButton;
        public MyViewHolder(RelativeLayout v) {
            super(v);
            mView = v;
            init();
        }

        private void init() {
            departTextField = (TextView) mView.findViewById(R.id.departTextField);
            ariveeTextField = (TextView) mView.findViewById(R.id.ariveeTextField);
            payTextField = (TextView) mView.findViewById(R.id.payTextField);
            bagageTextField = (TextView) mView.findViewById(R.id.bagageTextField);
            dateTextField = (TextView) mView.findViewById(R.id.dateTextField);
            addToPanierButton = (Button) mView.findViewById(R.id.addToPanierButton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AnnonceAdapter(List<Annonce> myDataset, LinkedList<Annonce> list, ChoixAnnoncesActivity activity) {
        mDataset = myDataset;
        panier=list;
        parent=activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnnonceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.annonce_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Annonce annonce = mDataset.get(position);
        holder.departTextField.setText("Depart : " + annonce.getAdresse_depart());
        holder.ariveeTextField.setText("Arrivee : " + annonce.getAdresse_arrivee());
        holder.payTextField.setText("Paiment : " + annonce.getPaiement());
        holder.bagageTextField.setText("Bagage : " + annonce.getBagage());
        holder.dateTextField.setText("Date : " + annonce.getDate_Debut());
        holder.addToPanierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! panier.contains(mDataset.get(position))) {
                    panier.add(mDataset.get(position));
                    holder.addToPanierButton.setText("-");
                    parent.getCart().setText("" + panier.size());
                }else {
                    panier.remove(mDataset.get(position));
                    holder.addToPanierButton.setText("+");
                    parent.getCart().setText("" + panier.size());
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}