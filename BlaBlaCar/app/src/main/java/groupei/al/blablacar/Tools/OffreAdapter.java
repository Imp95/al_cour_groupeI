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
import groupei.al.blablacar.Activities.OffresActivity;
import groupei.al.blablacar.Entities.Offre;
import groupei.al.blablacar.R;

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.MyViewHolder> {
    private List<Offre> mDataset;
    private Activity parent;

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
            departTextField = (TextView) mView.findViewById(R.id.departTextField2);
            ariveeTextField = (TextView) mView.findViewById(R.id.ariveeTextField2);
            payTextField = (TextView) mView.findViewById(R.id.payTextField2);
            bagageTextField = (TextView) mView.findViewById(R.id.bagageTextField2);
            dateTextField = (TextView) mView.findViewById(R.id.dateTextField2);
            addToPanierButton = (Button) mView.findViewById(R.id.addToPanierButton2);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public OffreAdapter(List<Offre> myDataset, OffresActivity offresActivity) {
        mDataset = myDataset;
        parent = offresActivity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public OffreAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offre_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Offre offre = mDataset.get(position);
        /*holder.departTextField.setText("Depart : " + offre.getAdresse_depart());
        holder.ariveeTextField.setText("Arrivee : " + offre.getAdresse_arrivee());
        holder.payTextField.setText("Paiment : " + offre.getPaiement()+" points");
        holder.bagageTextField.setText("Bagage : " + offre.getBagage());*/
        holder.dateTextField.setText("Date : " + offre.getDate_proposee());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}