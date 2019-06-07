package com.ufrpe.wallot_app.infra.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufrpe.wallot_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraficoFragment extends Fragment {


    public GraficoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grafico, container, false);
    }

}
