package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.ConfiguracionActivity;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.activities.SplashActivity;
import co.playtech.otrosproductosrd.adt.AutenticarADT;
import co.playtech.otrosproductosrd.adt.BancaADT;
import co.playtech.otrosproductosrd.fragments.AutenticarFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Printer;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;
import co.playtech.otrosproductosrd.objects.Loteria;
import co.playtech.otrosproductosrd.objects.ProveedorRecarga;

/**
 * Created by Egonzalias on 01/02/2016.
 */
public class AutenticarHandler implements View.OnClickListener {

    private AutenticarFragment fragment;
    private Activity activity;
    private AlertDialog alert;
    private EditText etClaveAdmin;
    private String sbClave;
    private final int BUTTON_ID_LOGIN_ADMIN = 0x2f010001;
    private SharedPreferences objShared;
    private JSONObject jsonDataSession;

    public AutenticarHandler(AutenticarFragment fragment){
        this.fragment = fragment;
        activity = this.fragment.getActivity();
        objShared = PreferenceManager.getDefaultSharedPreferences(fragment.context);
        jsonDataSession = new JSONObject();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAutenticar:
                validateLogin();
                break;

            case R.id.tvConfiguracion:
                showPopupLoginAdmin();
                break;

            case BUTTON_ID_LOGIN_ADMIN:
                validateLoginAdmin();
                break;
        }
    }

    private void showPopupLoginAdmin(){

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.context);

        // Get the layout inflater
        LayoutInflater inflater = fragment.getLayoutInflater(null);

        View viewPopup = inflater
                .inflate(R.layout.popup_admin, null);

        etClaveAdmin = (EditText) viewPopup.findViewById(R.id.etClaveAdministracion);
        //etClaveAdmin.setText("1234");

        builder.setView(viewPopup)
                .setPositiveButton(
                        fragment.getString(R.string.btn_aceptar),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        alert = builder.create();
        alert.show();

        Button btnAceptar = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        btnAceptar.setId(BUTTON_ID_LOGIN_ADMIN);
        btnAceptar.setOnClickListener(this);
    }

    private void validateLoginAdmin(){
        boolean blState = true;

        String sbClave = etClaveAdmin.getText().toString();

        if(Utilities.isEmpty(sbClave)){
            etClaveAdmin.setError(fragment.getString(R.string.msj_campo_requerido));
            blState = false;
        }

        String sbClaveActual = objShared.getString(Constants.PASSWORD_ADMIN, Constants.PASSWORD_ADMIN_VALUE);

        if(!sbClaveActual.equals(sbClave)){
            etClaveAdmin.setError(fragment.getString(R.string.msj_password_admin_invalid));
            blState = false;
        }

        if(blState){
            if(alert != null)
                alert.cancel();
            Intent objIntent = new Intent(fragment.context, ConfiguracionActivity.class);
            fragment.startActivity(objIntent);
        }
    }

    private void validateLogin(){
        boolean blStatus = true;
        String sbUsuario = fragment.etUsuario.getText().toString();
        sbClave = fragment.etClave.getText().toString();

        if(Utilities.isEmpty(sbUsuario)){
            fragment.etUsuario.setError(fragment.getString(R.string.msj_campo_requerido));
            blStatus = false;
        }

        if(Utilities.isEmpty(sbClave)){
            fragment.etClave.setError(fragment.getString(R.string.msj_campo_requerido));
            blStatus = false;
        }

        if(blStatus){
            new AutenticarAsyncTask().execute(sbUsuario, sbClave);
        }
    }


    private class AutenticarAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_autenticando));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";

                Connection objConexion = new Connection(fragment.context);

                sbPeticion += Services.VALIDAR_USUARIO;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += params[0];
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += params[1];
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += "";//Id terminal
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += "Spec01";
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += "seri";
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += "0";

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
                    procesarAutenticacion(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(fragment.context, e.getMessage());
            }
        }
    }

    private void procesarAutenticacion(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {
                sbRespuesta = sbRespuesta.substring(2);
                Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_CAMPO);

//0&T901003|100001|1000000|901003|15:05:52|901-003|BCA.  Elena Redflow  |2016-02-01|F|T901003|*@*|16&7|1NacionalDia|A|NACD@8|4NacionalNoche|A|NACN@9|2LotRealDia|A|LREDI@12|6LotekaNoche|A|LTKN@13|5LeidNoche|A|LENoc@17|NEWYORKDIA|A|NYDIA@10|3LoteKaDia|I|LTKD@11|Prueba|I|PRUE@14|domingo|I|re@15|Leidsa dia|I|LEdia@16|LoteriaRealN|I|LRnoche@

                jsonDataSession.put(Constants.SERIE, objParser.nextString());
                jsonDataSession.put(Constants.CONSECUTIVO_ACTUAL, objParser.nextString());
                jsonDataSession.put(Constants.CONSECUTIVO_FINAL, objParser.nextString());
                jsonDataSession.put(Constants.CODIGO_USUARIO, objParser.nextString());
                jsonDataSession.put(Constants.HORA, objParser.nextString());
                jsonDataSession.put(Constants.FICHO, objParser.nextString());
                jsonDataSession.put(Constants.VENDEDOR, objParser.nextString());
                jsonDataSession.put(Constants.FECHA_LOGIN, objParser.nextString());
                objParser.nextString();//bloqueo
                jsonDataSession.put(Constants.TERMINAL, objParser.nextString());
                jsonDataSession.put(Constants.MENSAJE, objParser.nextString());
                /*AutenticarADT.sbMensaje1 = objParser.nextString();
                AutenticarADT.sbMensaje2 = objParser.nextString();*/
                jsonDataSession.put(Constants.IVA, objParser.nextString());
                jsonDataSession.put(Constants.CLAVE, sbClave);

                new ProveedoresRecargaAsyncTask().execute();
            }
            else{
                //objError.nuCodigoError
                Utilities.showAlertDialog(fragment.context, objError.sbDescripcion);
            }
        }catch (Exception e){
            Utilities.showAlertDialog(fragment.context, e.getMessage());
        }
    }

    private class ProveedoresRecargaAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_proveedores_recarga));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(fragment.context);
                sbPeticion += Services.CONSULTAR_PROVEEDORES_RECARGA;
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
                    procesarProveedoresRecarga(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(fragment.context, e.getMessage());
            }
        }
    }

    private void procesarProveedoresRecarga(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            //ProveedorRecarga.listProveedores = null;
            if (objError.esExitosa()) {
                sbRespuesta = sbRespuesta.substring(2);
                Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_REGISTRO);

                //ProveedorRecarga.listProveedores = new ArrayList<>();
                JSONObject jsProveedoresRecarga = new JSONObject();
                while(objParser.hasMoreTokens()){

                    Parser objParserCampos = new Parser(objParser.nextString(), Constants.SEPARADOR_CAMPO);
                    String sbCodigo = objParserCampos.nextString();
                    String sbNombre = objParserCampos.nextString();

                    //ProveedorRecarga.listProveedores.add(new ProveedorRecarga(sbCodigo, sbNombre));
                    jsProveedoresRecarga.put(sbCodigo, sbNombre);
                }
                jsonDataSession.put(Constants.PROVEEDORES_RECARGA, jsProveedoresRecarga);
                new InformacionEmpresaAsyncTask().execute();
            }
            else{
                new InformacionEmpresaAsyncTask().execute();
            }
        }catch (Exception e){
            Utilities.showAlertDialog(fragment.context, e.getMessage());
        }
    }

    private class InformacionEmpresaAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_info_empresa));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(fragment.context);
                sbPeticion += Services.CONSULTAR_INFORMACION_EMPRESA;
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
                    procesarInformacionEmpresa(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(fragment.context, e.getMessage());
            }
        }
    }

    private void procesarInformacionEmpresa(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {
                sbRespuesta = sbRespuesta.substring(2);
                Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_CAMPO);
                jsonDataSession.put(Constants.NOMBRE_EMPRESA_FULL,objParser.nextString());
                jsonDataSession.put(Constants.NOMBRE_EMPRESA,objParser.nextString());

                new LoteriasAsyncTask().execute();
            }
            else{
                new LoteriasAsyncTask().execute();
                //Utilities.showAlertDialog(fragment.context,objError.sbDescripcion);
            }
        }catch (Exception e){
            Utilities.showAlertDialog(fragment.context, e.getMessage());
        }
    }

    private class LoteriasAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_loterias));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(fragment.context);
                sbPeticion += Services.CONSULTAR_LOTERIAS;
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
                    procesarLoterias(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(fragment.context, e.getMessage());
            }
        }
    }

    private void procesarLoterias(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {
                sbRespuesta = sbRespuesta.substring(2);
                Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_REGISTRO);

                //0&8|2|20:55:00|4|NACN|4NacionalNoche&13|2|20:55:00|5|LENoc|5LeidNoche&12|2|18:35:00|6|LTKN|6LotekaNoche
                //BancaADT.htLoterias = new Hashtable<>();
                JSONObject jsLoterias = new JSONObject();

                while(objParser.hasMoreTokens()){
                    Loteria objLoteria = new Loteria();
                    Parser objParserCampos = new Parser(objParser.nextString(), Constants.SEPARADOR_CAMPO);
                    String sbCodigo = objParserCampos.nextString();
                    objLoteria.setCodigo(sbCodigo);
                    objParserCampos.nextString();
                    String sbHoraCierre = objParserCampos.nextString();
                    objParserCampos.nextString();
                    String sbNombre = objParserCampos.nextString();
                    //BancaADT.htLoterias.put(sbCodigo, objLoteria);


                    JSONObject jsDetalleLoteria = new JSONObject();
                    jsDetalleLoteria.put(Constants.HORA_CIERRE, sbHoraCierre);
                    jsDetalleLoteria.put(Constants.NOMBRE_LOTERIA, sbNombre);


                    jsLoterias.put(sbCodigo, jsDetalleLoteria);
                }

                jsonDataSession.put(Constants.LOTERIAS, jsLoterias);

                objShared = PreferenceManager.getDefaultSharedPreferences(fragment.context);
                SharedPreferences.Editor edit = objShared.edit();
                edit.putBoolean(Constants.SESSION_CURRENT, true);
                edit.putString(Constants.JSON_USER, jsonDataSession.toString());
                edit.commit();

                Intent objIntent = new Intent(fragment.context, MainActivity.class);
                fragment.startActivity(objIntent);
                activity.finish();
            }
            else{
                Utilities.showAlertDialog(fragment.context,objError.sbDescripcion);
            }
        }catch (Exception e){
            Utilities.showAlertDialog(fragment.context, e.getMessage());
        }
    }
}
