package co.playtech.otrosproductosrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Handler;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.handlers.AutenticarHandler;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AutenticarFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AutenticarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutenticarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Context context;

    public EditText etUsuario;
    public EditText etClave;
    public Button btnAutenticar;
    public TextView tvConfiguracion;
    public TextView tvVersion;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AutenticarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AutenticarFragment newInstance(String param1, String param2) {
        AutenticarFragment fragment = new AutenticarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AutenticarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_autenticar, container, false);
            //((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_banca));

            context = getActivity();
            init(view);

        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }

        return view;
    }

    private void init(View v){
        tvVersion = (TextView)v.findViewById(R.id.tvVersionApp);
        tvConfiguracion = (TextView)v.findViewById(R.id.tvConfiguracion);
        etUsuario = (EditText)v.findViewById(R.id.etUsuario);
        etClave = (EditText)v.findViewById(R.id.etClave);
        btnAutenticar = (Button)v.findViewById(R.id.btnAutenticar);
        AutenticarHandler objHandler = new AutenticarHandler(this);
        btnAutenticar.setOnClickListener(objHandler);
        tvConfiguracion.setOnClickListener(objHandler);

        tvVersion.setText(context.getString(R.string.lblVersion)+Utilities.getVersion(context));
    }


}
