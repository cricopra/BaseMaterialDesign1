package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONObject;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.fragments.AnulacionBancaFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * Created by Playtech2 on 07/03/2016.
 * Egonzalias
 */
public class AnulacionBancaHandler implements View.OnClickListener{

    private JSONObject jsDataUser;
    private Activity activity;
    private AnulacionBancaFragment objFragment;

    public AnulacionBancaHandler(AnulacionBancaFragment objFragment){
        try{
            this.objFragment = objFragment;
            this.activity = objFragment.getActivity();

            String sbData = Utilities.getDataUserPreferences(objFragment.context);
            jsDataUser = new JSONObject(sbData);

            this.objFragment.etSerie.setText(jsDataUser.getString(Constants.SERIE));
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAnularBanca:
                anularBanca();
                break;
        }
    }

    private void anularBanca(){
        try{
            String sbConsecutivo = objFragment.etConsecutivo.getText().toString();

            if(Utilities.isEmpty(sbConsecutivo)){
                objFragment.etConsecutivo.setError(objFragment.getString(R.string.msj_campo_requerido));
            }
            else{

                new AnularBancaAsyncTask().execute(sbConsecutivo);
            }

        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private class AnularBancaAsyncTask extends
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

                Connection objConexion = new Connection(objFragment.context);

                sbPeticion += Services.ANULAR_BANCA;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += jsDataUser.getString(Constants.SERIE);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += params[0];
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
                    Utilities.showAlertDialog(objFragment.context,data);
                }
                else{
                    procesarAnulacion(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarAnulacion(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {
                objFragment.etConsecutivo.setText(null);
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
