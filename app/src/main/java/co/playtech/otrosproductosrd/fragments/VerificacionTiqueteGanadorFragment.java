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
import co.playtech.otrosproductosrd.handlers.VerficacionTiqueteGanadorHandler;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerificacionTiqueteGanadorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerificacionTiqueteGanadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerificacionTiqueteGanadorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Context context;
    public EditText etSerie;
    public EditText etConsecutivo;
    public Button btnVerificar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerificacionTiqueteGanadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerificacionTiqueteGanadorFragment newInstance(String param1, String param2) {
        VerificacionTiqueteGanadorFragment fragment = new VerificacionTiqueteGanadorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public VerificacionTiqueteGanadorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_verificacion_tiquete_ganador, container, false);
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_banca));

            context = getActivity();
            init(view);
        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }

        return view;
    }

    private void init(View v){
        etSerie = (EditText)v.findViewById(R.id.etSerieTiqueteGanador);
        etConsecutivo = (EditText)v.findViewById(R.id.etConsecutivoTiqueteGanador);
        btnVerificar = (Button)v.findViewById(R.id.btnVerificarTiqueteGanador);
        VerficacionTiqueteGanadorHandler objHandler = new VerficacionTiqueteGanadorHandler(this);
        btnVerificar.setOnClickListener(objHandler);
    }

}
