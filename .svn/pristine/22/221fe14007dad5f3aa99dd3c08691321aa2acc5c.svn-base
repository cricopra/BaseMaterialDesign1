package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.adapters.RecargasAdapter;
import co.playtech.otrosproductosrd.fragments.AnulacionRecargasFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;
import co.playtech.otrosproductosrd.objects.Recarga;

/**
 * Created by Egonzalias on 04/03/2016.
 */
public class AnulacionRecargasHandler implements RecargasAdapter.OnItemClickListener, View.OnClickListener{

    private final int BUTTON_ID_ANULAR = 0x1f130804;
    private AnulacionRecargasFragment fragment;
    private Activity activity;
    private AlertDialog alert;
    private JSONObject jsDataUser;
    private List<Recarga>lstRecargas;
    private RecargasAdapter objAdapter;
    private String sbIdCurrentTransaction;
    private String sbIdCurrentPosition;

    public AnulacionRecargasHandler(AnulacionRecargasFragment objFragment){
        fragment = objFragment;
        activity = fragment.getActivity();
        init();
    }

    private void init(){
        try{
            String sbData = Utilities.getDataUserPreferences(fragment.context);
            jsDataUser = new JSONObject(sbData);

            fragment.gridLayoutManager = new GridLayoutManager(fragment.context,
                    1, //The number of Columns in the grid
                    LinearLayoutManager.VERTICAL,
                    false);
            fragment.rvRecargas.setLayoutManager(fragment.gridLayoutManager);

            lstRecargas = new ArrayList<>();

            objAdapter = new RecargasAdapter(fragment.context, lstRecargas);
            fragment.rvRecargas.setAdapter(objAdapter);
            objAdapter.setOnItemClickListener(this);

            new UltimasRecargasAsyncTask().execute();

        }catch (Exception e){
            Utilities.showAlertDialog(fragment.context, e.getMessage());
        }
    }

    @Override
    public void onItemClick(RecargasAdapter.ViewHolder item, int position) {

        sbIdCurrentTransaction = item.getIdTransaccion().toString();
        sbIdCurrentPosition = String.valueOf(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.context);

        // Get the layout inflater
        LayoutInflater inflater = fragment.getLayoutInflater(null);

        View viewPopup = inflater
                .inflate(R.layout.pupup_anular_recarga, null);

        TextView tvNumero = (TextView) viewPopup.findViewById(R.id.tvNumeroRecargaAnular);
        TextView tvValor = (TextView) viewPopup.findViewById(R.id.tvValorRecagarAnular);

        tvNumero.setText(item.tvNumero.getText().toString());
        tvValor.setText(item.tvValor.getText().toString());

        builder.setView(viewPopup)
                .setTitle(fragment.getString(R.string.app_name))
                .setPositiveButton(
                        fragment.getString(R.string.btn_anular),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        alert = builder.create();
        alert.show();

        Button btnAnular = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        btnAnular.setId(BUTTON_ID_ANULAR);
        btnAnular.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case BUTTON_ID_ANULAR:
                new AnularRecargasAsyncTask().execute();
                break;
        }
    }

    private class UltimasRecargasAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_latest_reload));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(fragment.context);
                sbPeticion += Services.CONSULTA_ULTIMAS_RECARGAS;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += Utilities.getDate();
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += Utilities.getDate();
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += -1;
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += jsDataUser.getString(Constants.FICHO);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += "A";
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += jsDataUser.getString(Constants.CODIGO_USUARIO);

                return objConexion.sendTransaction(sbPeticion);
            } catch (Exception e) {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            try {
                Utilities.dismiss();
                String data = MessageError.validateTypeError(result);
                if(!Utilities.isEmpty(data)){
                    Utilities.showAlertDialog(fragment.context,data);
                }
                else{
                    procesarUltimasRecargas(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(fragment.context, e.getMessage());
            }
        }
    }

    private void procesarUltimasRecargas(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {
                //0&3125089368|20000|2016-03-04|DIGITEL|15:22:33|262794&3172986803|3000|2016-03-04|VIVA|15:20:57|262793
                sbRespuesta = sbRespuesta.substring(2);
                Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_REGISTRO);

                int i=0;
                while(objParser.hasMoreTokens()){

                    Parser objParserCampos = new Parser(objParser.nextString(), Constants.SEPARADOR_CAMPO);
                    Recarga objRecarga = new Recarga();

                    objRecarga.setNumero(objParserCampos.nextString());
                    objRecarga.setValor(objParserCampos.nextString());
                    objRecarga.setFecha(objParserCampos.nextString());
                    objParserCampos.nextString();//operador
                    objParserCampos.nextString();//hora
                    objRecarga.setIdTrasnaccion(objParserCampos.nextString());
                    lstRecargas.add(i,objRecarga);
                    i++;
                }
            }
            else{
                Utilities.showAlertDialog(fragment.context, objError.sbDescripcion);
            }

            if(lstRecargas.isEmpty()){
                fragment.llEmpty.setVisibility(View.VISIBLE);
                //fragment.rvRecargas.setVisibility(View.GONE);
            }
            else{
                fragment.llEmpty.setVisibility(View.GONE);
                //fragment.rvRecargas.setVisibility(View.VISIBLE);
            }
            objAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Utilities.showAlertDialog(fragment.context, e.getMessage());
        }
    }

    private class AnularRecargasAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_nullify_latest_reload));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(fragment.context);
                sbPeticion += Services.ANULAR_RECARGA;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += sbIdCurrentTransaction;

                return objConexion.sendTransaction(sbPeticion);
            } catch (Exception e) {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            try {
                Utilities.dismiss();
                String data = MessageError.validateTypeError(result);
                if(!Utilities.isEmpty(data)){
                    Utilities.showAlertDialog(fragment.context,data);
                }
                else{
                    procesarAnulacionRecarga(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(fragment.context, e.getMessage());
            }
        }
    }

    private void procesarAnulacionRecarga(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {
                if(alert!=null)
                    alert.cancel();
                objAdapter.remove(Integer.parseInt(sbIdCurrentPosition));
                Utilities.showAlertDialog(fragment.context, fragment.getString(R.string.msj_proceso_exitoso));
            }
            else{
                Utilities.showAlertDialog(fragment.context, objError.sbDescripcion);
            }

        }catch (Exception e){
            Utilities.showAlertDialog(fragment.context, e.getMessage());
        }
    }
}
