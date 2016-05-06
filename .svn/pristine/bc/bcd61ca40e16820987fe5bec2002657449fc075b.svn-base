package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.fragments.ResultadosFragment;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * Created by Egonzalias on 25/02/2016.
 */
public class ResultadosHandler implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private static final String TAG_CALENDAR = "date_resultados";
    private ResultadosFragment objFragment;
    private Activity activity;

    public ResultadosHandler(ResultadosFragment objFragment){
        this.objFragment = objFragment;
        this.activity = objFragment.getActivity();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case  R.id.ivDateResultos:
                showCalendar();
                break;

            case R.id.btnConsultarResultados:
                String sbFecha = objFragment.tvFecha.getText().toString();
                if(!Utilities.isEmpty(sbFecha))
                    new ConsultarResultadosVentaAsyncTask().execute(sbFecha);
                else
                    Snackbar.make(objFragment.getView(), objFragment.getString(R.string.msj_campo_requerido), Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    private void showCalendar(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(objFragment.fragment, TAG_CALENDAR);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String tag = view.getTag();
        monthOfYear++;
        if(tag.equals(TAG_CALENDAR)){
            objFragment.tvFecha.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
        }
    }

    private class ConsultarResultadosVentaAsyncTask extends
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
                sbPeticion += Services.CONSULTA_RESULTADOS_LOTERIAS;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += params[0];

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
                    procesarResultados(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarResultados(String sbRespuesta){
        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {
                objFragment.tvFecha.setText("");
                int aux;
                //0&LTKN|654321&LREDI|123456
                sbRespuesta = sbRespuesta.substring(2);
                Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_REGISTRO);

                String sbCadena = "";

                while(objParser.hasMoreTokens()){
                    aux = 10;
                    String sbSpace = " ";
                    Parser objParserCampos = new Parser(objParser.nextString(), Constants.SEPARADOR_CAMPO);
                    String sbLoteria = objParserCampos.nextString();
                    String sbNumero = objParserCampos.nextString();

                    aux = aux - sbNumero.length();
                    for(int i=0; i < aux; i++)
                        sbSpace += sbSpace;

                    sbCadena += sbLoteria+sbSpace+sbNumero+"\n";
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);
                builder.setMessage(sbCadena)
                        .setTitle(objFragment.context.getString(R.string.app_name))
                        .setCancelable(false)
                        .setPositiveButton(objFragment.context.getString(R.string.btn_aceptar),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                }
                        );
                AlertDialog alert = builder.create();
                alert.show();
            }
            else{
                Utilities.showAlertDialog(objFragment.context, objError.sbDescripcion);
            }
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }
}
