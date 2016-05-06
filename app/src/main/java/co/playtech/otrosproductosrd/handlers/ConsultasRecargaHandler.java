package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.adt.AutenticarADT;
import co.playtech.otrosproductosrd.fragments.ConsultasRecargaFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * Created by Playtech2 on 04/02/2016.
 * Egonzalias
 */
public class ConsultasRecargaHandler implements View.OnClickListener{

    private ConsultasRecargaFragment objFragment;
    private Activity activity;
    private AlertDialog alert;
    private JSONObject objDataUser;

    public ConsultasRecargaHandler(ConsultasRecargaFragment objFragment){
        try{
            this.objFragment = objFragment;
            this.activity = objFragment.getActivity();

            SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(objFragment.context);
            String sbData = objShared.getString(Constants.JSON_USER, "");

            objDataUser = new JSONObject(sbData);
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context,e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnUltimaRecarga:
                new UltimaRecargaAsyncTask().execute();
                break;

            case R.id.btnTotalVentaRecargas:
                new TotalVentaRecargasAsyncTask().execute();
                break;
        }
    }

    private class UltimaRecargaAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_recharge_last));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";

                Connection objConexion = new Connection(objFragment.context);

                sbPeticion += Services.CONSULTAR_ULTIMA_RECARGA;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += objDataUser.getString(Constants.FICHO);

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
                    Utilities.showAlertDialog(objFragment.context,data);
                }
                else{
                    procesarUltimaRecarga(result.toString());
                }
            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarUltimaRecarga(String sbRespuesta) throws AppException {

        MessageError objError = new MessageError(sbRespuesta);
        if (objError.esExitosa()) {
            sbRespuesta = sbRespuesta.substring(2);
            Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_CAMPO);
            //0&09:54:05|1|3125824896|2000|262789
            String sbHora = objParser.nextString();
            objParser.nextString();
            String sbNumero = objParser.nextString();
            String sbValor = objParser.nextString();

            AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);

            // Get the layout inflater
            LayoutInflater inflater = objFragment.getLayoutInflater(null);

            View viewPopup = inflater.inflate(R.layout.popup_consultas_recargas, null);
            TextView tvTitulo = (TextView) viewPopup.findViewById(R.id.tvTitleConsultasRecarga);
            TextView tvHora = (TextView) viewPopup.findViewById(R.id.tvTitleConsultaRecarga1);
            TextView tvNumero = (TextView) viewPopup.findViewById(R.id.tvTitleConsultaRecarga2);
            TextView tvValor = (TextView) viewPopup.findViewById(R.id.tvTitleConsultaRecarga3);
            TextView tvHoraData = (TextView) viewPopup.findViewById(R.id.tvLabelConsultaRecarga1);
            TextView tvNumeroData = (TextView) viewPopup.findViewById(R.id.tvLabelConsultaRecarga2);
            TextView tvValorData = (TextView) viewPopup.findViewById(R.id.tvLabelConsultaRecarga3);

            //set values
            tvTitulo.setText(objFragment.getString(R.string.lblUltimaRecarga));
            tvHora.setText(objFragment.getString(R.string.lblFecha));
            tvNumero.setText(objFragment.getString(R.string.lblNumero));
            tvValor.setText(objFragment.getString(R.string.lblValor));
            //set data
            tvHoraData.setText(sbHora);
            tvNumeroData.setText(sbNumero);
            tvValorData.setText(sbValor);

            builder.setView(viewPopup)
                    .setPositiveButton(
                            objFragment.getString(R.string.btn_aceptar),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
            alert = builder.create();
            alert.show();
        }
        else{
            //objError.nuCodigoError
            Utilities.showAlertDialog(objFragment.context,objError.sbDescripcion);
        }
    }

    private class TotalVentaRecargasAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_recharge_last));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";

                Connection objConexion = new Connection(objFragment.context);

                sbPeticion += Services.CONSULTAR_TOTAL_VENTA_RECARGAS;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += objDataUser.getString(Constants.FICHO);

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
                    Utilities.showAlertDialog(objFragment.context,data);
                }
                else{
                    procesarTotalVentas(result.toString());
                }
            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarTotalVentas(String sbRespuesta) throws AppException {

        MessageError objError = new MessageError(sbRespuesta);
        if (objError.esExitosa()) {
            //0&102013.0|0.0|2000.0
            sbRespuesta = sbRespuesta.substring(2);
            Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_CAMPO);
            //0&09:54:05|1|3125824896|2000|262789
            objParser.nextString();//Valor deuda
            String sbCupo = objParser.nextString();
            String sbVentaHoy = objParser.nextString();

            AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);

            // Get the layout inflater
            LayoutInflater inflater = objFragment.getLayoutInflater(null);

            View viewPopup = inflater.inflate(R.layout.popup_consultas_recargas, null);
            TextView tvTitulo = (TextView) viewPopup.findViewById(R.id.tvTitleConsultasRecarga);
            TextView tvCupo = (TextView) viewPopup.findViewById(R.id.tvTitleConsultaRecarga1);
            TextView tvVentaHoy = (TextView) viewPopup.findViewById(R.id.tvTitleConsultaRecarga2);
            TextView tvLabel = (TextView) viewPopup.findViewById(R.id.tvTitleConsultaRecarga3);
            TextView tvCupoData = (TextView) viewPopup.findViewById(R.id.tvLabelConsultaRecarga1);
            TextView tvVentaHoyData = (TextView) viewPopup.findViewById(R.id.tvLabelConsultaRecarga2);
            TextView tvLabelData = (TextView) viewPopup.findViewById(R.id.tvLabelConsultaRecarga3);

            //set values
            tvTitulo.setText(objFragment.getString(R.string.lblTotalVentaRecarga));
            tvCupo.setText(objFragment.getString(R.string.lblCupoDisponible));
            tvVentaHoy.setText(objFragment.getString(R.string.lblVentaHoy));
            tvLabel.setVisibility(View.INVISIBLE);
            //set data
            tvCupoData.setText(sbCupo);
            tvVentaHoyData.setText(sbVentaHoy);
            tvLabelData.setVisibility(View.INVISIBLE);

            builder.setView(viewPopup)
                    .setPositiveButton(
                            objFragment.getString(R.string.btn_aceptar),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
            alert = builder.create();
            alert.show();
        }
        else{
            //objError.nuCodigoError
            Utilities.showAlertDialog(objFragment.context,objError.sbDescripcion);
        }
    }

}
