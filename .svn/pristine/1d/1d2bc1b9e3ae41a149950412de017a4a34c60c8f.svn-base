package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONObject;

import java.util.Calendar;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.fragments.TotalVentaFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Printer;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * Created by Egonzalias on 18/02/2016.
 */
public class TotalVentaHandler implements View.OnClickListener,  DatePickerDialog.OnDateSetListener{

    private static final String DATE_INIT = "DatePickerInit";
    private static final String DATE_END = "DatePickerEnd";
    private TotalVentaFragment objFragment;
    private Activity activity;
    private JSONObject jsDataUser;
    private String sbFechaInicial;
    private String sbFechaFinal;

    public TotalVentaHandler(TotalVentaFragment objFragment){
        this.objFragment = objFragment;
        this.activity = objFragment.getActivity();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivDateInit:
                show(DATE_INIT);
            break;

            case R.id.ivDateEnd:
                show(DATE_END);
                break;

            case R.id.btnConsultarTotalVenta:
                validateDates();
                break;
        }
    }

    private void show(String tag){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(objFragment.fragment, tag);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String tag = view.getTag();
        monthOfYear++;
        if(tag.equals(DATE_INIT)){
            objFragment.tvDateInit.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
        else if (tag.equals(DATE_END)){
            objFragment.tvDateEnd.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }

    }

    private void validateDates(){
        try{
            boolean blState = true;
            sbFechaInicial = objFragment.tvDateInit.getText().toString();
            sbFechaFinal = objFragment.tvDateEnd.getText().toString();

            String sbData = Utilities.getDataUserPreferences(objFragment.context);
            jsDataUser = new JSONObject(sbData);

            if(Utilities.isEmpty(sbFechaInicial)){
                blState = false;
                objFragment.tvDateInit.setError(objFragment.getString(R.string.msj_campo_requerido));
            }

            if(Utilities.isEmpty(sbFechaFinal)){
                blState = false;
                objFragment.tvDateEnd.setError(objFragment.getString(R.string.msj_campo_requerido));
            }

            if(blState){
                new ConsultarTotalVentaAsyncTask().execute();
            }
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private class ConsultarTotalVentaAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            objFragment.getString(R.string.load_total_sale));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(objFragment.context);
                sbPeticion += Services.CONSULTA_TOTAL_VENTA_HOY;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += jsDataUser.getString(Constants.FICHO);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += sbFechaInicial;
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += sbFechaFinal;

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
                    procesarTotalVenta(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarTotalVenta(String sbRespuesta) throws AppException {

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
                                        printReport(sbData);
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

    private void printReport(String sbRespuesta){
        try{
            String sbCadenaImpresion = "";

            //TODO Egonzalias La trama se divide en 6 partes separadas por @, si cada parte de la trama inicia con '#' se ignora el parseo
            //#&#&#&0|0|0|0&0|0|0|0|0&0|0|0|0|0
            Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_REGISTRO);

            String sbCadenaAnulados = objParser.nextString();
            String sbCadenaGanadores = objParser.nextString();
            String sbCadenaVentaRecarga = objParser.nextString();
            String sbCadenaResumenRecarga = objParser.nextString();
            String sbCadenaResumenLoteria = objParser.nextString();
            String sbCadenaResumenTotal = objParser.nextString();

            sbCadenaImpresion += Utilities.centrarCadena(jsDataUser.getString(Constants.NOMBRE_EMPRESA_FULL))+"\n";
            sbCadenaImpresion += Utilities.centrarCadena(objFragment.getString(R.string.print_titulo_reporte_ventas))+"\n\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_fecha)+jsDataUser.getString(Constants.FECHA_LOGIN)+"\n";
            sbCadenaImpresion += jsDataUser.getString(Constants.VENDEDOR)+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_de)+sbFechaInicial+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_hasta)+sbFechaFinal+"\n\n";

            /**RESUMEN RECARGA*/
            Parser objParserResumenRecarga = new Parser(sbCadenaResumenRecarga, Constants.SEPARADOR_CAMPO);
            sbCadenaImpresion += Utilities.centrarCadena(objFragment.getString(R.string.print_titulo_resumen_recarga))+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_venta)+objParserResumenRecarga.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_comision)+objParserResumenRecarga.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_anulaciones)+objParserResumenRecarga.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_ganancias)+objParserResumenRecarga.nextString()+"\n\n";

            /**RESUMEN LOTERIAS*/
            Parser objParserResumenLoteria = new Parser(sbCadenaResumenLoteria, Constants.SEPARADOR_CAMPO);
            sbCadenaImpresion += Utilities.centrarCadena(objFragment.getString(R.string.print_titulo_resumen_loterias))+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_venta_bruta)+objParserResumenLoteria.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_comision)+objParserResumenLoteria.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_venta_neta)+objParserResumenLoteria.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_total_ganadores)+objParserResumenLoteria.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_ganancias)+objParserResumenLoteria.nextString()+"\n\n";

            /**RESUMEN TOTAL*/
            Parser objParserResumenTotal = new Parser(sbCadenaResumenTotal, Constants.SEPARADOR_CAMPO);
            sbCadenaImpresion += Utilities.centrarCadena(objFragment.getString(R.string.print_titulo_resumen_total))+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_venta_bruta)+objParserResumenTotal.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_comision)+objParserResumenTotal.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_venta_neta)+objParserResumenTotal.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_total_ganadores)+objParserResumenTotal.nextString()+"\n";
            sbCadenaImpresion += objFragment.getString(R.string.print_ganancias)+objParserResumenTotal.nextString()+"\n\n";

            new ImprimirAsyncTask().execute(sbCadenaImpresion);
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
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
            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }
}
