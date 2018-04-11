package com.example.dim.wineroom.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dim.wineroom.R;
import com.example.dim.wineroom.data.entities.Grape;
import com.example.dim.wineroom.utils.DetailFragmentInterface;

import static com.example.dim.wineroom.MainActivity.ARG_DEBUG;

public class GrapeDetailFragment extends Fragment
    implements DetailFragmentInterface<Grape>{

    private TextView tv;

    public static GrapeDetailFragment newInstance() {
        GrapeDetailFragment fragment = new GrapeDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /**
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grape_detail, container, false);
        tv = ((TextView) rootView.findViewById(R.id.dtl_grape_label));
        //tv.setText(((Grape)getArguments().getSerializable(ARG_GRAPE_SELECTED)).getGrape_label());
        return rootView;
    }

    @Override
    public void updateDetailFragment(Grape item) {
        Log.i(ARG_DEBUG, "updateDetailFragment: "+item.toString());
        //getArguments().putSerializable(ARG_GRAPE_SELECTED, item);
        if (item.getGrape_label() != null) {
            tv.setText(item.getGrape_label());
        }
    }
}
