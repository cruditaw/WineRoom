package com.example.dim.wineroom.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.dim.wineroom.MainActivity;
import com.example.dim.wineroom.R;
import com.example.dim.wineroom.data.entities.Grape;
import com.example.dim.wineroom.utils.EditFragmentInterface;

import static com.example.dim.wineroom.MainActivity.ARG_DEBUG;

public class GrapeEditFragment extends Fragment
implements EditFragmentInterface<Grape>{

    private EditText tv;

    public static GrapeEditFragment newInstance() {
        GrapeEditFragment fragment = new GrapeEditFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_grape_edit, container, false);
        tv = ((EditText) rootView.findViewById(R.id.dtl_grape_edit_label));
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateActivity(new Grape(tv.getText().toString()));
            }
        });
        return rootView;
    }

    @Override
    public void updateEditFragment(Grape item) {
        Log.i(ARG_DEBUG, "updateEditFragment: "+item.toString());
        //getArguments().putSerializable(ARG_GRAPE_SELECTED, item);
        if (item.getGrape_label() != null) {
            tv.setText(item.getGrape_label());
        }
    }

    @Override
    public void updateActivity(Grape item) {
        ((MainActivity)getActivity()).updateActivity(item);
    }
}
