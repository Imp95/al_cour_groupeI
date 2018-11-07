package groupei.al.blablacar.Tools;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        public TextView mTextView;
        public MyViewHolder(TextView v) {
            super(v);
            mTextView = v;
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
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).toString());
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                panier.add(mDataset.get(position));
                parent.getCart().setText(""+panier.size());
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}