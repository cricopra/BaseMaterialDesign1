package co.playtech.otrosproductosrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.handlers.CambiarClaveHandler;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CambiarClaveFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CambiarClaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CambiarClaveFragment extends Fragment {

    public Context context;
    public EditText etClaveActual;
    public EditText etClaveNueva;
    public EditText etClaveRepite;
    public Button btnCambiarClave;

    public CambiarClaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_cambiar_clave, container, false);
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_banca));

            context = getActivity();
            init(view);

        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }
        return view;
    }

    private void init(View v){
        etClaveActual = (EditText)v.findViewById(R.id.etClaveActual);
        etClaveNueva = (EditText)v.findViewById(R.id.etClaveNueva);
        etClaveRepite = (EditText)v.findViewById(R.id.etClaveRepite);
        btnCambiarClave = (Button)v.findViewById(R.id.btnCambiarClave);
        CambiarClaveHandler objHandler = new CambiarClaveHandler(this);
        btnCambiarClave.setOnClickListener(objHandler);
    }

}
