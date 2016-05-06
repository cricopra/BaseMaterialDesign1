package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.adt.AutenticarADT;
import co.playtech.otrosproductosrd.fragments.VerificacionTiqueteGanadorFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Printer;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * Created by Egonzalias on 02/02/2016.
 */
public class VerficacionTiqueteGanadorHandler implements View.OnClickListener {

    private VerificacionTiqueteGanadorFragment objFragment;
    private Activity activity;
    private int nuValorPremio;
    private JSONObject jsDataUser;
    private String sbSerie;
    private String sbConsecutivo;

    public VerficacionTiqueteGanadorHandler(VerificacionTiqueteGanadorFragment objFragment){
        this.objFragment = objFragment;
        activity = objFragment.getActivity();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnVerificarTiqueteGanador:
                validateData();
                break;
        }
    }

    private void validateData(){
        boolean blStatus = true;
        sbSerie = objFragment.etSerie.getText().toString();
        sbConsecutivo = objFragment.etConsecutivo.getText().toString();

        if(Utilities.isEmpty(sbSerie)){
            objFragment.etSerie.setError(objFragment.getString(R.string.msj_campo_requerido));
            blStatus = false;
        }
        if(Utilities.isEmpty(sbConsecutivo)){
            objFragment.etConsecutivo.setError(objFragment.getString(R.string.msj_campo_requerido));
            blStatus = false;
        }

        if(blStatus){
            sbSerie = Constants.INICIAL_SERIE+sbSerie;
            new VerificarTiqueteAsyncTask().execute(sbSerie,sbConsecutivo);
        }
    }

    private class VerificarTiqueteAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_ticket_win));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(objFragment.context);
                sbPeticion += Services.VERIFICAR_TIQUETE_GANADOR;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += params[0];
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += params[1];

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
                    procesarVerificacion(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarVerificacion(final String sbRespuesta) throws AppException {

        MessageError objError = new MessageError(sbRespuesta);
        if (objError.esExitosa()) {
            //0&2016-02-02&10:47:44&36|NACN|QUINELA|10|600|&5491|NACN|PALE|20|2000|
            final String sbData = sbRespuesta.substring(2);

            AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);
            builder.setMessage(objFragment.context.getString(R.string.msj_proceso_exitoso))
                    .setTitle(objFragment.context.getString(R.string.app_name))
                    .setCancelable(false)
                    .setPositiveButton(objFragment.context.getString(R.string.btn_imprimir),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    imprimirVerificacion(sbData);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            Utilities.showAlertDialog(objFragment.context,objError.sbDescripcion);
        }
    }

    private void imprimirVerificacion(String sbRespuesta){
        try {
            String sbImpresion = "";
            Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_REGISTRO);

            sbImpresion += "     "+objFragment.getString(R.string.print_titulo_reporte_ganador)+"\n\n";

            sbImpresion += objFragment.getString(R.string.print_fecha)+objParser.nextString()+" "+objParser.nextString()+"\n\n";

            sbImpresion += objFragment.getString(R.string.print_cabecera_ganadores)+"\n\n";

            nuValorPremio = 0;

            while (objParser.hasMoreTokens()){
                Parser objParserCampos = new Parser(objParser.nextString(), Constants.SEPARADOR_CAMPO);
                String sbNumero =  objParserCampos.nextString();
                String sbLoteria =  objParserCampos.nextString();
                String sbModalidad =  objParserCampos.nextString();
                String sbValor =  objParserCampos.nextString();
                String sbPremio =  objParserCampos.nextString();

                nuValorPremio += Integer.parseInt(sbValor);

                sbImpresion += sbNumero+" "+sbLoteria+" "+sbModalidad+" "+sbValor+" "+sbPremio+"\n";
            }
            new ImprimirAsyncTask().execute(sbImpresion);
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context,e.getMessage());
        }
    }

    private class ImprimirAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_print));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                Printer.print(params[0],null);
                return null;
            } catch (Exception e) {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            try {
                Utilities.dismiss();
                payPrize();
            } catch (Exception e) {

            }
        }
    }

    private void payPrize(){
        try{
            String sbData = Utilities.getDataUserPreferences(objFragment.context);
            jsDataUser = new JSONObject(sbData);

            AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);
            builder.setMessage(objFragment.context.getString(R.string.msj_pay_prize))
                    .setTitle(objFragment.context.getString(R.string.app_name))
                    .setCancelable(true)
                    .setPositiveButton(objFragment.context.getString(R.string.btn_aceptar),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new PagarPremioGanadorAsyncTask().execute();
                                }
                            }
                    );
            AlertDialog alert = builder.create();
            alert.show();
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private class PagarPremioGanadorAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_pay_prize));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(objFragment.context);
                sbPeticion += Services.PAGAR_PREMIO;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += jsDataUser.getString(Constants.CODIGO_USUARIO);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += nuValorPremio;
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += 0;//Retencion
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += jsDataUser.getString(Constants.TERMINAL);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += sbSerie;
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += sbConsecutivo;
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += "123";//Cedula


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
                    procesarPagoPremioGanador(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarPagoPremioGanador(String sbRespuesta) throws AppException {

        try{

            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {

                final String sbData = sbRespuesta.substring(2);

                AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);
                builder.setMessage(objFragment.context.getString(R.string.msj_proceso_exitoso))
                        .setTitle(objFragment.context.getString(R.string.app_name))
                        .setCancelable(false)
                        .setPositiveButton(objFragment.context.getString(R.string.btn_imprimir),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        printPayPrize(sbData);
                                    }
                                }
                        );
                AlertDialog alert = builder.create();
                alert.show();
            }
            else{
                Utilities.showAlertDialog(objFragment.context,objError.sbDescripcion);
            }
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private void printPayPrize(String sbData){
        try{
            sbData = "REDSOL|2016-02-18|17:01:30|T901003|100060|BCA.  Elena Redflow  |123|Usuario default|5.0|S|1413";
            String sbCadenaImpresion = "";

            Parser objParser = new Parser(sbData, Constants.SEPARADOR_CAMPO);

            String sbEmpresa = objParser.nextString();
            String sbFecha = objParser.nextString();
            String sbHora = objParser.nextString();
            String sbSerie = objParser.nextString();
            String sbConsecutivo = objParser.nextString();
            String sbVendedor = objParser.nextString();
            objParser.nextString();
            String sbCliente = objParser.nextString();
            String sbMonto = objParser.nextString();
            objParser.nextString();
            String sbCodigoAprobacion = objParser.nextString();

            sbCadenaImpresion += Utilities.centrarCadena(sbEmpresa)+"\n\n";

            String sbMensaje = objFragment.getString(R.string.msj_pay_prize_success);

            sbMensaje = sbMensaje.replace("{1}", sbMonto);
            sbMensaje = sbMensaje.replace("{2}", sbSerie);
            sbMensaje = sbMensaje.replace("{3}", sbConsecutivo);

            sbCadenaImpresion += sbMensaje+"\n\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_aprobacion)+sbCodigoAprobacion+"\n";

            sbCadenaImpresion += sbFecha+"    "+sbHora+"\n\n";

            new PrintPrayPrizeAsyncTask().execute(sbCadenaImpresion);

        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private class PrintPrayPrizeAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_print));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                Printer.print(params[0],null);
                return null;
            } catch (Exception e) {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result) {
            try {
                Utilities.dismiss();
            } catch (Exception e) {

            }
        }
    }

}
