package tads.tdm.mediaescolar.fragmets;

import android.tdm.mediaescolar.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ModeloFragment extends Fragment {

    View view;

    public ModeloFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(tads.tdm.mediaescolar.R.layout.fragment_modelo, container, false);

        return view;
    }


}
