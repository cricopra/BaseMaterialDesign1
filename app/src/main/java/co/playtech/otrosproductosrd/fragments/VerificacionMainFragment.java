package co.playtech.otrosproductosrd.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.adapters.ViewPagerAdapter;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerificacionMainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerificacionMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerificacionMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //
    public Context context;
    private ViewPager pagerVerificacion;
    private ViewPagerAdapter adapterVerificacion;
    private TabLayout tabsVerificacion;
    private CharSequence[] arrTitles;
    private Class[] arrFragmentsClass;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerificacionMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerificacionMainFragment newInstance(String param1, String param2) {
        VerificacionMainFragment fragment = new VerificacionMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public VerificacionMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;

        try{
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_verificacion_main, container, false);
            ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_banca));

            context = getActivity();
            init(view);
        }catch(Exception e){
            Utilities.showAlertDialog(context, e.getMessage());
        }

        return view;
    }

    private void init(View v){
        tabsVerificacion = (TabLayout) v.findViewById(R.id.tabsVerificacion);
        pagerVerificacion = (ViewPager) v.findViewById(R.id.pagerVerificacion);
        arrTitles = new CharSequence[]{context.getString(R.string.tab_verificacion), context.getString(R.string.tab_verificacion_ganador)};
        arrFragmentsClass = new Class[]{VerificacionTiqueteFragment.class, VerificacionTiqueteGanadorFragment.class};

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the tabsConference and Number Of tabsConference.
        adapterVerificacion =  new ViewPagerAdapter(getChildFragmentManager(), arrTitles, arrFragmentsClass);

        // Assigning ViewPager View and setting the adapter
        pagerVerificacion.setAdapter(adapterVerificacion);

        /**+-
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabsVerificacion.post(new Runnable() {
            @Override
            public void run() {
                tabsVerificacion.setupWithViewPager(pagerVerificacion);
                if (getArguments() != null) {
                    pagerVerificacion.setCurrentItem(getArguments().getInt("currenTab"));
                }
            }
        });
    }

}
