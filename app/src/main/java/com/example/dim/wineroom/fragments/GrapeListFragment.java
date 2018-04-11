package com.example.dim.wineroom.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dim.wineroom.MainActivity;
import com.example.dim.wineroom.R;
import com.example.dim.wineroom.data.entities.Grape;
import com.example.dim.wineroom.utils.GrapeRecyclerAdapter;
import com.example.dim.wineroom.utils.ListFragmentInterface;

import static com.example.dim.wineroom.MainActivity.ARG_GRAPE_COUNT;
import static com.example.dim.wineroom.MainActivity.ARG_GRAPE_ITEM;

public class GrapeListFragment extends Fragment
implements ListFragmentInterface<Grape>{

    private RecyclerView recyclerView;

    public static GrapeListFragment newInstance(SparseArray<Grape> grapes) {
        GrapeListFragment fragment = new GrapeListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_GRAPE_COUNT, grapes.size());
        for (int i = 0; i < grapes.size(); i++) {
            Integer key = grapes.keyAt(i);
            Grape g = grapes.get(key);
            bundle.putSerializable(ARG_GRAPE_ITEM + i, g);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FloatingActionButton fabCreate = (FloatingActionButton) getActivity().findViewById(R.id.fab_list_add);
        //fabCreate.setOnClickListener((OnClickListener)((EntityFragmentInterface)getArguments().getSerializable("LISTENER")).onCreateClickListener());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grape_list, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.list);
        recyclerView.setAdapter(new GrapeRecyclerAdapter((MainActivity)getActivity()));
        return rootView;
    }

    @Override
    public void updateRecyclerAdpater(Grape item, boolean isDelete) {
        recyclerView.setAdapter(new GrapeRecyclerAdapter((MainActivity)getActivity()));
    }

    @Override
    public void updateRecyclerAdapter() {
        recyclerView.setAdapter(new GrapeRecyclerAdapter((MainActivity)getActivity()));
    }


}
