package groupei.al.blablacar.Tools;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import groupei.al.blablacar.Activities.ChoixAnnoncesActivity;
import groupei.al.blablacar.Entities.Annonce;
import groupei.al.blablacar.R;

public class AnnonceAdapterValid extends RecyclerView.Adapter<AnnonceAdapterValid.MyViewHolder> {
    private List<Annonce> mDataset;
    private ChoixAnnoncesActivity parent;
    private HashMap<Annonce,String> disponiblity;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public EditText mEditView;
        public LinearLayout layout;
        public MyViewHolder(LinearLayout v, TextView textView, EditText editText) {
            super(v);
            layout = v;
            mTextView = textView;
            layout.addView(mTextView);
            mEditView = editText;
            layout.addView(mEditView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AnnonceAdapterValid(List<Annonce> myDataset, ChoixAnnoncesActivity activity) {
        mDataset = myDataset;
        parent=activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnnonceAdapterValid.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.annonce_view, parent, false);
        TextView t = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        EditText e = (EditText) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_edit_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v,t,e);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).toString());
        holder.mEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                disponiblity.put(mDataset.get(position),editable.toString());
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public HashMap<Annonce,String> getDisponiblity(){return disponiblity;}
}