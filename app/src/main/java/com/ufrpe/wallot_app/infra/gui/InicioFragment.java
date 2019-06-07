package com.ufrpe.wallot_app.infra.gui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ufrpe.wallot_app.R;
import com.ufrpe.wallot_app.infra.app.WallotApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment /*implements View.OnClickListener*/{
    private  View v;
    private Context context;
    private Button buttonFab;

    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v =  inflater.inflate(R.layout.fragment_inicio, container, false);
        /*buttonFab =(Button) v.findViewById(R.id.buttonFab);
        buttonFab.setOnClickListener(this);*/

        return v;
    }


    /*@Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonFab:
                Toast.makeText(getActivity(),"clicado otaraio",Toast.LENGTH_LONG).show();
                break;
        }


    }*/
}
