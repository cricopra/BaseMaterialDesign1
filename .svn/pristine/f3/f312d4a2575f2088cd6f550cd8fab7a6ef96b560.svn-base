package co.playtech.otrosproductosrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.handlers.VerificacionTiqueteHandler;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VerificacionTiqueteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerificacionTiqueteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static VerificacionTiqueteFragment instance;
    public Context context;
    public EditText etCodigoVerificacion;
    public ImageView ivScanTicket;
    public Button btnVerificar;
    private Activity activity;
    private VerificacionTiqueteHandler objHandler;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerificacionTiqueteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerificacionTiqueteFragment newInstance(String param1, String param2) {
        VerificacionTiqueteFragment fragment = new VerificacionTiqueteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public VerificacionTiqueteFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_verificacion_tiquete, container, false);
            instance = this;
            context = getActivity();

            init(view);
        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }

        return view;
    }

    private void init(View v){
        etCodigoVerificacion = (EditText)v.findViewById(R.id.etVerificacionTiquete);
        ivScanTicket = (ImageView)v.findViewById(R.id.ivScanCode);
        btnVerificar = (Button)v.findViewById(R.id.btnVerificarTiquete);
        objHandler = new VerificacionTiqueteHandler(this);
        ivScanTicket.setOnClickListener(objHandler);
        btnVerificar.setOnClickListener(objHandler);
    }

    public static VerificacionTiqueteFragment getInstance() {
        return instance == null ? new VerificacionTiqueteFragment() : instance;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                objHandler.verrifyTicket(contents);
            }
        }
    }
}
