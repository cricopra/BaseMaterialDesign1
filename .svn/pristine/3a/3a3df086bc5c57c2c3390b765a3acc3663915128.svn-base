package co.playtech.otrosproductosrd.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.handlers.TotalVentaHandler;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class TotalVentaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ImageView ivDateInit;
    public ImageView ivDateEnd;
    public TextView tvDateInit;
    public TextView tvDateEnd;
    public Button btnConsultar;
    public Context context;
    public FragmentManager fragment;


    public TotalVentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_total_venta, container, false);
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_banca));

            context = getActivity();
            fragment = getActivity().getFragmentManager();
            init(view);
        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }

        return view;
        // Inflate the layout for this fragment
    }

    private void init(View v){
        ivDateInit = (ImageView)v.findViewById(R.id.ivDateInit);
        ivDateEnd = (ImageView)v.findViewById(R.id.ivDateEnd);
        tvDateInit = (TextView)v.findViewById(R.id.tvDateInit);
        tvDateEnd = (TextView)v.findViewById(R.id.tvDateEnd);
        btnConsultar = (Button)v.findViewById(R.id.btnConsultarTotalVenta);
        TotalVentaHandler objHandler = new TotalVentaHandler(this);
        ivDateInit.setOnClickListener(objHandler);
        ivDateEnd.setOnClickListener(objHandler);
        btnConsultar.setOnClickListener(objHandler);

    }


}
