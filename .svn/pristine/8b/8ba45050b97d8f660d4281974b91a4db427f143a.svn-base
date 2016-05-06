package co.playtech.otrosproductosrd.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.adt.AutenticarADT;
import co.playtech.otrosproductosrd.handlers.BancaHandler;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BancaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BancaFragment#newInstance} factory method to
 * create an instance of this fragment.
 * Egonzalias
 */
public class BancaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Others
    public Context context;
    private BancaHandler objHandler;
    public GridLayoutManager gridLayoutManager;
    public LinearLayout llEmpty;

    //Elements GUI
    public RecyclerView rvBancaItems;
    public FloatingActionButton fabAddItem;
    public LinearLayout llActualizarValores;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BancaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BancaFragment newInstance(String param1, String param2) {
        BancaFragment fragment = new BancaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BancaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_banca, container, false);

            context = getActivity();
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_banca));
            init(view);
            setHasOptionsMenu(true);
        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }

        return view;
    }

    private void init(View view){
        llEmpty = (LinearLayout)view.findViewById(R.id.llRecylcleriewEmptyApuestas);
        llActualizarValores = (LinearLayout)view.findViewById(R.id.llActualizarValores);
        fabAddItem = (FloatingActionButton) view.findViewById(R.id.fabAddItem);
        //fabAddItem.setBackgroundTintList(ColorStateList.valueOf());
        rvBancaItems = (RecyclerView)view.findViewById(R.id.rvDetalleApuesta);
        objHandler = new BancaHandler(this);
        fabAddItem.setOnClickListener(objHandler);
        llActualizarValores.setOnClickListener(objHandler);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.banca, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        objHandler.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(objHandler.lstBancaItem != null ){
            if(objHandler.lstBancaItem.isEmpty())
                llEmpty.setVisibility(View.VISIBLE);
            else
                llEmpty.setVisibility(View.GONE);
        }
    }
}
