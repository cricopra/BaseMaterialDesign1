package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.adt.AutenticarADT;
import co.playtech.otrosproductosrd.fragments.VentaRecargaFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Printer;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;
import co.playtech.otrosproductosrd.objects.ProveedorRecarga;

/**
 * Created by Egonzalias on 04/02/2016.
 */
public class VentaRecargaHandler implements View.OnClickListener {

    private VentaRecargaFragment objFragment;
    private Activity activity;

    private String sbPrintProveedor = "";
    private String sbPrintNumero = "";
    private String sbPrintValor = "";

    private JSONObject objDataUser;
    private JSONObject objProveedores;
    private ArrayList<String> arrProveedores;

    public VentaRecargaHandler(VentaRecargaFragment objFragment){
        this.objFragment = objFragment;
        this.activity = objFragment.getActivity();
        init();
    }

    private void init(){
        try{
            arrProveedores = new ArrayList<>();

            SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(objFragment.context);
            String sbData = objShared.getString(Constants.JSON_USER, "");

            objDataUser = new JSONObject(sbData);
            objProveedores = objDataUser.getJSONObject(Constants.PROVEEDORES_RECARGA);

            Iterator<String> iterator = objProveedores.keys();
            while(iterator.hasNext()){
                String sbCodigo = (String)iterator.next();
                arrProveedores.add(objProveedores.getString(sbCodigo));
            }

            /*if(ProveedorRecarga.listProveedores != null){
                for(int i=0; i < ProveedorRecarga.listProveedores.size(); i++){
                    spinnerArray.add(ProveedorRecarga.listProveedores.get(i).getNombre());
                }
            }*/

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(objFragment.context, android.R.layout.simple_spinner_item, arrProveedores); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            objFragment.spnOperadores.setAdapter(spinnerArrayAdapter);
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnRecargar:
                validarRecarga();
                break;
        }
    }

    private void validarRecarga(){

        try{
            boolean blState = true;
            String sbProveedor = "";
            int nuPosicion = objFragment.spnOperadores.getSelectedItemPosition();
            String sbNumero = objFragment.etNumeroRecarga.getText().toString();
            String sbValor = objFragment.etValorRecarga.getText().toString();

            if(Utilities.isEmpty(sbNumero)){
                blState = false;
                objFragment.etNumeroRecarga.setError(objFragment.getString(R.string.msj_campo_requerido));
            }

            if(Utilities.isEmpty(sbValor)){
                blState = false;
                objFragment.etValorRecarga.setError(objFragment.getString(R.string.msj_campo_requerido));
            }


            String sbNombreProveedor = arrProveedores.get(nuPosicion);

            Iterator<String> iterator = objProveedores.keys();
            while(iterator.hasNext()){
                String sbCodigo = iterator.next();
                String sbNombre = objProveedores.getString(sbCodigo);
                if(sbNombre.equals(sbNombreProveedor)){
                    sbProveedor = sbCodigo;
                    sbPrintProveedor = sbNombre;
                    break;
                }
            }


            if(Utilities.isEmpty(sbProveedor)){
                blState = false;
            }

            if(blState){
                sbPrintNumero = sbNumero;
                sbPrintValor = sbValor;
                new RealizarRecargaAsyncTask().execute(sbNumero, sbValor, sbProveedor);
            }
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private class RealizarRecargaAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_recarga_sale));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";

                Connection objConexion = new Connection(objFragment.context);

                sbPeticion += Services.REALIZAR_RECARGA;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += objDataUser.getString(Constants.FICHO);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += params[2];//Proveedor
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += params[1];//Valor
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += params[0];//Telefono
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += objDataUser.getString(Constants.SERIE);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += objDataUser.getString(Constants.CONSECUTIVO_ACTUAL);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += objDataUser.getString(Constants.CLAVE);

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
                    procesarRecarga(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarRecarga(String sbRespuesta) throws AppException {

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
                                    printRecarga(sbData);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();

        }
        else{
            Utilities.showAlertDialog(objFragment.context,objError.sbDescripcion);
        }
    }

    private void printRecarga(String sbRespuesta){

        try {
            String sbImpresion = "";
            Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_CAMPO);
            String sbId = objParser.nextString();
            String sbEmpresa = objParser.nextString();
            String sbFecha = objParser.nextString();
            String sbHora = objParser.nextString();
            objParser.nextString();//Celular
            objParser.nextString();//total recarga
            String sbFactura = objParser.nextString();
            String sbResolucion = objParser.nextString();

            sbImpresion += Utilities.centrarCadena(sbEmpresa)+"\n\n";
            sbImpresion += objFragment.getString(R.string.print_id)+sbId+"\n";
            sbImpresion += objFragment.getString(R.string.print_fecha)+sbFecha+"\n";
            sbImpresion += objFragment.getString(R.string.print_hora)+sbHora+"\n";
            sbImpresion += objFragment.getString(R.string.print_vendedor)+objDataUser.getString(Constants.FICHO)+" "+objFragment.getString(R.string.print_vendedor)+objDataUser.getString(Constants.TERMINAL)+"\n";
            sbImpresion += objFragment.getString(R.string.print_operardor)+sbPrintProveedor+"\n";
            sbImpresion += "-------------------------\n";
            sbImpresion += objFragment.getString(R.string.print_celular)+sbPrintNumero+"\n";
            sbImpresion += objFragment.getString(R.string.print_valor)+sbPrintValor+"\n";

            if(!sbFactura.startsWith("*")){
                sbImpresion += objFragment.getString(R.string.print_factura)+sbFactura+"\n";
                sbImpresion +=sbResolucion+"\n";
            }

            sbImpresion += "-------------------------\n\n\n";

            new ImprimirAsyncTask().execute(sbImpresion);
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
