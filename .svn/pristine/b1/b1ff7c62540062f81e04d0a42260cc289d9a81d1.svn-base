package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.json.JSONObject;

import java.util.ArrayList;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.adt.AutenticarADT;
import co.playtech.otrosproductosrd.fragments.CambiarClaveFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;
import co.playtech.otrosproductosrd.objects.ProveedorRecarga;

/**
 * Created by Playtech2 on 05/02/2016.
 * Egonzalias
 */
public class CambiarClaveHandler implements View.OnClickListener{

    private String sbClaveNueva;
    private CambiarClaveFragment objFragment;
    private Activity activity;
    private JSONObject jsDataUser;

    public CambiarClaveHandler(CambiarClaveFragment objFragment){
        this.objFragment = objFragment;
        this.activity = objFragment.getActivity();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnCambiarClave:
                validateFields();
                break;
        }
    }

    private void validateFields(){
        try{
            boolean blState = true;
            String sbClaveActual = objFragment.etClaveActual.getText().toString();
            sbClaveNueva = objFragment.etClaveNueva.getText().toString();
            String sbClaveRepite = objFragment.etClaveRepite.getText().toString();

            String sbData = Utilities.getDataUserPreferences(objFragment.context);
            jsDataUser = new JSONObject(sbData);

            if(Utilities.isEmpty(sbClaveActual)){
                blState = false;
                objFragment.etClaveActual.setError(objFragment.getString(R.string.msj_campo_requerido));
            }

            if(Utilities.isEmpty(sbClaveNueva)){
                blState = false;
                objFragment.etClaveNueva.setError(objFragment.getString(R.string.msj_campo_requerido));
            }

            if(Utilities.isEmpty(sbClaveRepite)){
                blState = false;
                objFragment.etClaveRepite.setError(objFragment.getString(R.string.msj_campo_requerido));
            }

            if(!sbClaveRepite.equals(sbClaveNueva)){
                blState = false;
                Snackbar.make(objFragment.getView(), objFragment.getString(R.string.msj_password_invalid), Snackbar.LENGTH_SHORT).show();
            }

            if(!sbClaveActual.equals(jsDataUser.getString(Constants.CLAVE))){
                blState = false;
                Snackbar.make(objFragment.getView(), objFragment.getString(R.string.msj_password_current_invalid), Snackbar.LENGTH_SHORT).show();
            }

            if(blState){
                new CambiarClaveAsyncTask().execute(sbClaveActual, sbClaveNueva);
            }
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private class CambiarClaveAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            objFragment.getString(R.string.load_change_password));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(objFragment.context);
                sbPeticion += Services.REALIZAR_CAMBIO_CLAVE;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += jsDataUser.getString(Constants.CODIGO_USUARIO);
                sbPeticion += Constants.SEPARADOR_CAMPO;
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
                    procesarCambioClave(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarCambioClave(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {
                jsDataUser.put(Constants.CLAVE, sbClaveNueva);
                Utilities.setDataUserPreferences(objFragment.context, jsDataUser);
                Utilities.showAlertDialog(objFragment.context,objFragment.getString(R.string.msj_proceso_exitoso));
            }
            else{
                Utilities.showAlertDialog(objFragment.context,objError.sbDescripcion);
            }
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

}
