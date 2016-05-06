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

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.handlers.ConsultasRecargaHandler;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConsultasRecargaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConsultasRecargaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultasRecargaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Context context;
    public Button btnUltimaRecarga;
    public Button btnTotalVentaRecarga;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultasRecargaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultasRecargaFragment newInstance(String param1, String param2) {
        ConsultasRecargaFragment fragment = new ConsultasRecargaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ConsultasRecargaFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_consultas_recarga, container, false);

            context = getActivity();
            init(view);

        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }
        return view;
    }

    private void init (View v){
        btnUltimaRecarga = (Button)v.findViewById(R.id.btnUltimaRecarga);
        btnTotalVentaRecarga = (Button)v.findViewById(R.id.btnTotalVentaRecargas);
        ConsultasRecargaHandler objHandler = new ConsultasRecargaHandler(this);
        btnUltimaRecarga.setOnClickListener(objHandler);
        btnTotalVentaRecarga.setOnClickListener(objHandler);
    }

}
