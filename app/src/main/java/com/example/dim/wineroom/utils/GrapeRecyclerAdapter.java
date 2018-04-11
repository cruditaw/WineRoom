package com.example.dim.wineroom.utils;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dim.wineroom.MainActivity;
import com.example.dim.wineroom.R;
import com.example.dim.wineroom.data.entities.Grape;

import static com.example.dim.wineroom.MainActivity.ARG_DEBUG;

public class GrapeRecyclerAdapter
        extends RecyclerView.Adapter<GrapeRecyclerAdapter.ViewHolder>
        implements ListFragmentRecyclerInterface<Grape> {

    private View.OnClickListener mOnClickListener;
    private MainActivity parentActivity;

    public GrapeRecyclerAdapter(final MainActivity parentActivity) {
        this.parentActivity = parentActivity;

        mOnClickListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == view.getTag()) {
                    Log.i(ARG_DEBUG, "onClick: VIEW TAG IS NULL !");
                    return;
                }

                String[] str = view.getTag().toString().split("_");
                if (str[0].isEmpty()) {
                    Log.i(ARG_DEBUG, "onClick: VIEW TAG ACTION IS EMPTY");
                    return;
                }

                if (str[1].isEmpty()) {
                    Log.i(ARG_DEBUG, "onClick: VIEW TAG ITEM IS EMPTY");
                    return;
                }

                int key = (Integer.valueOf(str[1]));
                if (str[0].equalsIgnoreCase("READ")) {
                    updateSelectedItem(parentActivity.getGrapesModel().get(key));
                    return;
                }

                if (str[0].equalsIgnoreCase("DELETE")) {
                    removeBottle(parentActivity.getGrapesModel().get(key));
                    return;
                }

                if (str[0].equalsIgnoreCase("CREATE")) {
                    addBottle(parentActivity.getGrapesModel().get(key));
                    return;
                }
            }
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_grape_item, parent, false);
        //Log.i("DEBUG_RECYCLER", "onCreateViewHolder: passed");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int keyAt = parentActivity.getGrapesModel().keyAt(position);

        holder.mIdView.setText(String.valueOf(position+1));
        holder.mContentView.setText(parentActivity.getGrapesModel().get(keyAt).getGrape_label());
        holder.mNumber.setText(String.valueOf(parentActivity.getGrapesModel().get(keyAt).getNumber()));

        holder.itemLayout.setTag("READ_" + String.valueOf(parentActivity.getGrapesModel().get(keyAt).getId()));
        holder.ibtnRemove.setTag("DELETE_" + String.valueOf(parentActivity.getGrapesModel().get(keyAt).getId()));
        holder.ibtnCreate.setTag("CREATE_" + String.valueOf(parentActivity.getGrapesModel().get(keyAt).getId()));
    }

    @Override
    public int getItemCount() {
        return parentActivity.getGrapesModel().size();
    }

    @Override
    public void updateSelectedItem(Grape selectedItem) {
        parentActivity.updateSelectedItem(selectedItem);
    }

    @Override
    public void addBottle(Grape selected) {
        parentActivity.addBottle(selected);
    }

    @Override
    public void removeBottle(Grape selected) {
        parentActivity.removeBottle(selected);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mIdView;
        final TextView mContentView;
        final TextView mNumber;

        final ConstraintLayout itemLayout;
        final ImageButton ibtnCreate;
        final ImageButton ibtnRemove;

        ViewHolder(View view) {
            super(view);
            itemLayout = (ConstraintLayout) view.findViewById(R.id.li_grape_item_layout);
            itemLayout.setOnClickListener(mOnClickListener);

            mIdView = (TextView) view.findViewById(R.id.id_text);
            mContentView = (TextView) view.findViewById(R.id.content);
            mNumber = (TextView) view.findViewById(R.id.number);

            ibtnCreate = (ImageButton) view.findViewById(R.id.li_grape_button_add);
            ibtnCreate.setOnClickListener(mOnClickListener);

            ibtnRemove = (ImageButton) view.findViewById(R.id.li_grape_button_remove);
            ibtnRemove.setOnClickListener(mOnClickListener);
        }
    }
}
