package co.playtech.otrosproductosrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.handlers.AnulacionBancaHandler;
import co.playtech.otrosproductosrd.handlers.AnulacionRecargasHandler;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AnulacionBancaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnulacionBancaFragment extends Fragment {
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
    public Button btnAnular;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnulacionBancaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnulacionBancaFragment newInstance(String param1, String param2) {
        AnulacionBancaFragment fragment = new AnulacionBancaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AnulacionBancaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_anulacion_banca, container, false);

            context = getActivity();
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_anulacion));
            init(view);

        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }

        return view;
    }

    private void init(View v){
        try{
            etSerie = (EditText)v.findViewById(R.id.etSerieAnulacion);
            etConsecutivo = (EditText)v.findViewById(R.id.etConsecutivoAnulacion);
            btnAnular = (Button)v.findViewById(R.id.btnAnularBanca);
            AnulacionBancaHandler objHandler = new AnulacionBancaHandler(this);
            btnAnular.setOnClickListener(objHandler);
        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }
    }

}
