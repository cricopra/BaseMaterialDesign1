package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.adapters.BancaAdapter;
import co.playtech.otrosproductosrd.adapters.LoteriaAdapter;
import co.playtech.otrosproductosrd.fragments.BancaFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.BancaTouchHelper;
import co.playtech.otrosproductosrd.help.Connection;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.MessageError;
import co.playtech.otrosproductosrd.help.Parser;
import co.playtech.otrosproductosrd.help.Printer;
import co.playtech.otrosproductosrd.help.Services;
import co.playtech.otrosproductosrd.help.TEA;
import co.playtech.otrosproductosrd.help.Utilities;
import co.playtech.otrosproductosrd.objects.Banca;
import co.playtech.otrosproductosrd.objects.Loteria;

/**
 * Created by Egonzaliass on 27/01/2016.
 */
public class BancaHandler implements BancaAdapter.OnItemClickListener, View.OnClickListener{

    private Activity activity;
    private final int BUTTON_ID_ADD = 0x7f030004;
    private final int BUTTON_ID_EDIT = 0x6f040003;
    private final int BUTTON_ID_LOAD = 0x5f050005;
    private BancaFragment objFragment;
    public List<Banca> lstBancaItem;
    private List<Loteria> lstLoterias;
    private List<Loteria> loteriasCurrent;

    private BancaAdapter objAdapter;
    private LoteriaAdapter objAdapterLoterias;
    private AlertDialog alert;
    //Edit items
    private int nuPositionRowEditCurrent;
    private int nuIdRowEditCurrent;//Id del textview (Numero, valor, producto) para saber que columna de la fila acutalizar
    private EditText etDataCurrent;
    private Spinner spnProductoCurrent;
    //Elements Popup
    private EditText etNumeroApuesta;
    private EditText etValorApuesta;
    private Spinner spnProductoApuesta;
    //Loterias popup
    private RecyclerView rvLoterias;
    //Load bet popup
    private EditText etSerie;
    private EditText etConsecutivo;
    //printer
    private String sbPrintConsecutivo;
    private String sbPrintLoterias;
    //data user
    private JSONObject jsDataUser;


    public BancaHandler(BancaFragment objFragment){
        this.objFragment = objFragment;
        this.activity = objFragment.getActivity();
        init();
    }


    private void init(){
        try{
            loadPreferences();

            objFragment.gridLayoutManager = new GridLayoutManager(objFragment.context,
                    1, //The number of Columns in the grid
                    LinearLayoutManager.VERTICAL,
                    false);
            objFragment.rvBancaItems.setLayoutManager(objFragment.gridLayoutManager);

            //init empty recyclerview
            lstBancaItem = new ArrayList<>();
            //Set up adapter
            objAdapter = new BancaAdapter(objFragment.context, lstBancaItem, activity, objFragment);
            objFragment.rvBancaItems.setAdapter(objAdapter);

            objFragment.llEmpty.setVisibility(View.VISIBLE);

            //Set up ItemTouchListener
            //Listener para habiliar eventos para gestos hacia derecha o izquierda y poder remover elementos del recycler view
            ItemTouchHelper.Callback callback = new BancaTouchHelper(objAdapter);
            ItemTouchHelper helper = new ItemTouchHelper(callback);
            helper.attachToRecyclerView(objFragment.rvBancaItems);

            objAdapter.setOnItemClickListener(this);

        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private void loadPreferences(){
        try{
            String sbData = Utilities.getDataUserPreferences(objFragment.context);
            jsDataUser = new JSONObject(sbData);
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    @Override
    public void onItemClick(BancaAdapter.ViewHolder item, int position, int id) {
        nuPositionRowEditCurrent = position;
        nuIdRowEditCurrent = id;
        Banca itemBanca = lstBancaItem.get(position);

        if(id!=-1)
            showPopupEditItem(itemBanca, id);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fabAddItem:
                showPopupAddItem();
                break;

            case BUTTON_ID_ADD:
                validateAdditionApuesta();
                break;

            case R.id.llActualizarValores:
                Toast.makeText(objFragment.context, "Opcion para actualizar todos los valores", Toast.LENGTH_SHORT).show();
                break;

            case BUTTON_ID_EDIT:
                addItemEdit();
                break;

            case BUTTON_ID_LOAD:
                loadLastBet();
                break;
        }
    }

    //Adiciona elementos que han sido editados y actualiza el recycler view con previas validaciones
    private void addItemEdit(){
        Banca objBanca;
        String sbData = etDataCurrent.getText().toString();
        objBanca = lstBancaItem.get(nuPositionRowEditCurrent);

        if(nuIdRowEditCurrent == R.id.tvProductoApuesta){
            int position = spnProductoCurrent.getSelectedItemPosition();
            String producto = (String)spnProductoCurrent.getSelectedItem();
            spnProductoCurrent.setSelection(position);
            objBanca.setProducto(producto);
        }

        if(sbData != null){
            if(nuIdRowEditCurrent == R.id.tvNumeroApuesta){
                objBanca.setNumero(sbData);
            }
            if(nuIdRowEditCurrent == R.id.tvValorApuesta){
                objBanca.setValor(sbData);
            }

            lstBancaItem.set(nuPositionRowEditCurrent, objBanca);
            objAdapter.notifyItemChanged(nuPositionRowEditCurrent);
        }

        if(alert != null)
            alert.cancel();
    }

    private void showPopupAddItem(){

        AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);

        // Get the layout inflater
        LayoutInflater inflater = objFragment.getLayoutInflater(null);

        View viewPopup = inflater
                .inflate(R.layout.popup_item_banca, null);

        etNumeroApuesta = (EditText) viewPopup.findViewById(R.id.etNumeroApuesta);
        etValorApuesta = (EditText) viewPopup.findViewById(R.id.etValorApuesta);
        spnProductoApuesta = (Spinner) viewPopup.findViewById(R.id.spnProductoApuesta);

        etNumeroApuesta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cambiarProductos(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.setView(viewPopup)
        .setPositiveButton(
                objFragment.getString(R.string.btn_adicionar),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        alert = builder.create();
        alert.show();

        Button btnAdicionar = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        btnAdicionar.setId(BUTTON_ID_ADD);
        btnAdicionar.setOnClickListener(this);
    }

    /**
     * Filtra la lista de productos/modalidad de acuerdo a la longitud del numero a apostar digitada
     */
    private void cambiarProductos(int nuLongitud){

        String[] modalidades = objFragment.getResources().getStringArray(R.array.array_productos);
        ArrayList<String> spinnerArray = new ArrayList<String>();


        if(nuLongitud == 2){
            spinnerArray.add(modalidades[0]);
        }
        else if(nuLongitud == 4){
            spinnerArray.add(modalidades[1]);
            spinnerArray.add(modalidades[3]);
        }
        else if(nuLongitud == 6){
            spinnerArray.add(modalidades[2]);
        }


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(objFragment.context, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spnProductoApuesta.setAdapter(spinnerArrayAdapter);
    }

    private void validateAdditionApuesta(){
        boolean blEstado = true;
        String sbCodigoProducto = "";
        String sbMensaje = objFragment.getString(R.string.msj_completar_campos);
        String sbNumero = etNumeroApuesta.getText().toString();
        String sbValor = etValorApuesta.getText().toString();
        String sbProducto = (String)spnProductoApuesta.getSelectedItem();

        if(sbNumero == null || sbNumero.equals("")){
            etNumeroApuesta.setError(objFragment.getString(R.string.msj_campo_requerido));
            blEstado = false;
        }

        if(sbValor == null || sbValor.equals("")){
            etValorApuesta.setError(objFragment.getString(R.string.msj_campo_requerido));
            blEstado = false;
        }

        if(sbNumero.length() == 1 || sbNumero.length() == 3 || sbNumero.length() == 5){
            etNumeroApuesta.setError(objFragment.getString(R.string.msj_longitud_no_valida));
            blEstado = false;
        }

        String[] modalidades = objFragment.getResources().getStringArray(R.array.array_productos);

        if(sbProducto == null){
            etNumeroApuesta.setError(objFragment.getString(R.string.msj_longitud_no_valida));
            blEstado = false;
        }

        if(blEstado){
            //QUINELA
            if(sbProducto.equals(modalidades[0]))
                sbCodigoProducto = Loteria.COD_QUINELA;

                //PALE
            else if(sbProducto.equals(modalidades[1]))
                sbCodigoProducto = Loteria.COD_PALE;

                //TRIPLETA
            else if(sbProducto.equals(modalidades[2]))
                sbCodigoProducto = Loteria.COD_TRIPLETA;

                //SUPER PALE
            else if(sbProducto.equals(modalidades[3]))
                sbCodigoProducto = Loteria.COD_SUPER_PALE;

            //int nuPosition = lstBancaItem.size();
            lstBancaItem.add(new Banca(sbNumero, sbValor, sbProducto, sbCodigoProducto));
            objFragment.llEmpty.setVisibility(View.GONE);
            ((MainActivity) activity).setActionBarTitle(objFragment.context.getString(R.string.title_banca) + " (" + lstBancaItem.size() + ")");

            objAdapter.notifyDataSetChanged();
            if(alert != null)
                alert.cancel();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_total) {
            showTotal();
        }
        if(id == R.id.action_loterias){
            showPopupAddLoterias();
        }
        if(id == R.id.action_bet){
            sendBet();
        }
        if(id == R.id.action_load_bet){
            showPopupLoadLastBet();
        }
        return true;
    }

    private String getTotal(){
        String sbTotal = "";
        int nuTotal = 0;
        int nuTotalFinal = 0;
        int nuCantidadLoterias = 0;

        if(loteriasCurrent != null){
            for(int i=0; i < loteriasCurrent.size(); i++){
                Loteria objLoteria = loteriasCurrent.get(i);
                if(objLoteria.isSelected()){
                    nuCantidadLoterias++;
                }
            }
        }

        for(int i=0; i < lstBancaItem.size(); i++){
            Banca objItem = (Banca) lstBancaItem.get(i);
            if(objItem.getCodigo_producto() == Loteria.COD_SUPER_PALE)
                nuTotal = Integer.parseInt(objItem.getValor()) * 1;
            else
                nuTotal = Integer.parseInt(objItem.getValor()) * nuCantidadLoterias;
            nuTotalFinal += nuTotal;
        }

        if(loteriasCurrent != null){
            for(int i=0; i < loteriasCurrent.size(); i++){
                Loteria objLoteria = loteriasCurrent.get(i);
                if(objLoteria.isSelected()){
                    objLoteria.getCodigo();
                }
            }
        }

        return String.valueOf(nuTotalFinal);
    }

    private void showTotal(){

        Snackbar.make(objFragment.getView(), "Total: $ "+getTotal(), Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    private void showPopupAddLoterias(){

        try{
            AlertDialog alertLoterias;

            AlertDialog.Builder builderLoterias = new AlertDialog.Builder(objFragment.context);

            // Get the layout inflater
            LayoutInflater inflaterLoterias = objFragment.getLayoutInflater(null);

            View viewPopupLoterias = inflaterLoterias.inflate(R.layout.popup_loterias, null);

            rvLoterias = (RecyclerView) viewPopupLoterias.findViewById(R.id.rvLoterias);

            lstLoterias = new ArrayList<>();

            SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(objFragment.context);
            String sbData = objShared.getString(Constants.JSON_USER, "");

            JSONObject objDataUser = new JSONObject(sbData);

            JSONObject objLoterias = objDataUser.getJSONObject(Constants.LOTERIAS);

            Iterator<String> iterator = objLoterias.keys();
            while(iterator.hasNext()){
                String sbCodigo = (String)iterator.next();
                JSONObject jsDetalleLoteria = objLoterias.getJSONObject(sbCodigo);
                System.out.println(jsDetalleLoteria);

                Loteria objLoteria = new Loteria();
                objLoteria.setCodigo(sbCodigo);
                objLoteria.setNombre(jsDetalleLoteria.getString(Constants.NOMBRE_LOTERIA));
                objLoteria.setHoraCierre(jsDetalleLoteria.getString(Constants.HORA_CIERRE));
                lstLoterias.add(objLoteria);
            }

           /* if(BancaADT.htLoterias != null){
                Enumeration<String> enumeration = BancaADT.htLoterias.keys();

                while(enumeration.hasMoreElements()){
                    String codigo = enumeration.nextElement();
                    Loteria objLoteria = BancaADT.htLoterias.get(codigo);
                    lstLoterias.add(objLoteria);
                }
            }*/

            if(loteriasCurrent == null)
                loteriasCurrent = lstLoterias;

            objAdapterLoterias = new LoteriaAdapter(objFragment.context, loteriasCurrent);
            rvLoterias.setAdapter(objAdapterLoterias);
            rvLoterias.setLayoutManager(new LinearLayoutManager(objFragment.context));
            objAdapterLoterias.setOnItemClickListener(new LoteriaAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(LoteriaAdapter.ViewHolder item, int position) {

                }
            });

            builderLoterias.setView(viewPopupLoterias)
                    .setPositiveButton(
                            objFragment.getString(R.string.btn_adicionar),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

            alertLoterias = builderLoterias.create();
            alertLoterias.show();
        }catch (Exception e){
            Utilities.showAlertDialog(objFragment.context, e.getMessage());
        }
    }

    private void showPopupEditItem(Banca item, int nuIdType){
        AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);

        // Get the layout inflater
        LayoutInflater inflater = objFragment.getLayoutInflater(null);

        View viewPopup = inflater
                .inflate(R.layout.popup_editar_item, null);

        etDataCurrent = (EditText) viewPopup.findViewById(R.id.etItemEdicion);
        spnProductoCurrent = (Spinner) viewPopup.findViewById(R.id.spnProductoEdicion);
        TextView tvLabel = (TextView) viewPopup.findViewById(R.id.tvLabelEdicion);

        etDataCurrent.setVisibility(View.VISIBLE);
        spnProductoCurrent.setVisibility(View.GONE);

        if(nuIdType == R.id.tvNumeroApuesta) {
            tvLabel.setText(objFragment.getString(R.string.lblNumero));
            etDataCurrent.setText(item.getNumero());
            etDataCurrent.setSelection(etDataCurrent.getText().length());
        }
        else if(nuIdType == R.id.tvValorApuesta){
            tvLabel.setText(objFragment.getString(R.string.lblValor));
            etDataCurrent.setText(item.getValor());
            etDataCurrent.setSelection(etDataCurrent.getText().length());
        }
        else if(nuIdType == R.id.tvProductoApuesta){
            etDataCurrent.setVisibility(View.GONE);
            spnProductoCurrent.setVisibility(View.VISIBLE);
            spnProductoCurrent.setSelection(spnProductoCurrent.getSelectedItemPosition());
            tvLabel.setText(objFragment.getString(R.string.lblProducto));
        }

        builder.setView(viewPopup)
                .setPositiveButton(
                        objFragment.getString(R.string.btn_adicionar),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        alert = builder.create();
        alert.show();

        Button btnEditarItem = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        btnEditarItem.setId(BUTTON_ID_EDIT);
        btnEditarItem.setOnClickListener(this);

    }

    private void sendBet(){

        //Numero - Producto - Valor - Loterias, es el orden de la cadena
        boolean blStatus = true;
        String sbTotal = getTotal();
        String sbCadenaLoterias = "";
        String sbCadenaDetalle = "";

        if(loteriasCurrent != null){
            sbPrintLoterias = "";
            for(int i=0; i < loteriasCurrent.size(); i++){
                Loteria objLoteria = loteriasCurrent.get(i);
                if(objLoteria.isSelected()){
                    sbCadenaLoterias += objLoteria.getCodigo()+Constants.SEPARADOR_LOTERIAS;
                    sbPrintLoterias += objLoteria.getNombre()+Constants.SEPARADOR_LOTERIAS;
                }
            }
        }
        else
            blStatus = false;

        if(Utilities.isEmpty(sbCadenaLoterias)){
            blStatus = false;
            Snackbar.make(objFragment.getView(), objFragment.getString(R.string.msj_seleccione_loterias), Snackbar.LENGTH_SHORT).show();
        }
        else {
            sbCadenaLoterias = sbCadenaLoterias.substring(0, sbCadenaLoterias.length() - 1);
            sbPrintLoterias = sbPrintLoterias.substring(0, sbPrintLoterias.length() - 1);
        }

        if(lstBancaItem != null && lstBancaItem.size()>0){
            for(int i=0; i < lstBancaItem.size(); i++){
                Banca objItem = lstBancaItem.get(i);
                sbCadenaDetalle += objItem.getNumero();
                sbCadenaDetalle += Constants.SEPARADOR_CAMPO;
                sbCadenaDetalle += objItem.getCodigo_producto();
                sbCadenaDetalle += Constants.SEPARADOR_CAMPO;
                sbCadenaDetalle += objItem.getValor();
                sbCadenaDetalle += Constants.SEPARADOR_CAMPO;
                sbCadenaDetalle += sbCadenaLoterias;
                sbCadenaDetalle += Constants.SEPARADOR_REGISTRO;
            }
        }else
            blStatus = false;

        if(Utilities.isEmpty(sbCadenaDetalle)){
            blStatus = false;
            Snackbar.make(objFragment.getView(), objFragment.getString(R.string.msj_seleccione_items), Snackbar.LENGTH_SHORT).show();
        }

        if(blStatus){
            sbCadenaDetalle = sbCadenaDetalle.substring(0, sbCadenaDetalle.length()-1);

            new RealizarApuestaAsyncTask().execute(sbTotal, sbCadenaDetalle);
        }
    }

    private class RealizarApuestaAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_sale));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(objFragment.context);
                sbPeticion += Services.REALIZAR_APUESTA;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += 0;//Codigo tipo juego
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += jsDataUser.getString(Constants.SERIE);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += jsDataUser.getString(Constants.CONSECUTIVO_ACTUAL);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += "";//Cedula cliente
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += jsDataUser.getString(Constants.CODIGO_USUARIO);
                sbPeticion += Constants.SEPARADOR_CAMPO;
                sbPeticion += params[0];
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += params[1];

                //Utilities

                sbPrintConsecutivo = jsDataUser.getString(Constants.CONSECUTIVO_ACTUAL);

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
                    procesarVenta(result.toString());
                }

            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }



    private void procesarVenta(String sbRespuesta) throws AppException {

        try{
            MessageError objError = new MessageError(sbRespuesta);
            if (objError.esExitosa()) {

                //0&T901003|100003|2016-02-02|10:54:57|||901003||REDSOL|0&
                final String sbData = sbRespuesta.substring(2);

                Parser objParser = new Parser(sbData, Constants.SEPARADOR_REGISTRO);
                Parser objParserCampos = new Parser(objParser.nextString(), Constants.SEPARADOR_CAMPO);

                jsDataUser.put(Constants.SERIE, objParserCampos.nextString());
                jsDataUser.put(Constants.CONSECUTIVO_ACTUAL, objParserCampos.nextString());
                //Save current consecutive
                Utilities.setDataUserPreferences(objFragment.context, jsDataUser);

                AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);
                builder.setMessage(objFragment.context.getString(R.string.msj_proceso_exitoso))
                        .setTitle(objFragment.context.getString(R.string.app_name))
                        .setCancelable(false)
                        .setPositiveButton(objFragment.context.getString(R.string.btn_imprimir),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        printSale(sbData);
                                    }
                                }
                        )
                        .setNegativeButton(objFragment.context.getString(R.string.btn_no),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        refreshForm();
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
            Utilities.showAlertDialog(objFragment.context,e.getMessage());
        }
    }

    private void printSale(String data)  {
        try{
            String sbImpresion = "";
            String sbImpresionAux = "";
            Parser objParser = new Parser(data, Constants.SEPARADOR_REGISTRO);
            Parser objParserCampos = new Parser(objParser.nextString(), Constants.SEPARADOR_CAMPO);

            /**TODO ingnorados estos dos campos porque se actualizan al momento de que sea exitosa la apuesta*/
            String sbSerie = objParserCampos.nextString();//serie
            String sbConsecutivos = objParserCampos.nextString();//consecutivos
            String sbFecha = objParserCampos.nextString();
            String sbHora = objParserCampos.nextString();
            objParserCampos.nextString();
            objParserCampos.nextString();
            objParserCampos.nextString(); // Vendedor
            objParserCampos.nextString();
            objParserCampos.nextString(); // Nombre empresa
            String sbDireccionEmpresa = objParserCampos.nextString();
            String sbMensajeEmpresa = objParserCampos.nextString();
            objParserCampos.nextString(); // Codigo encriptado

            sbImpresion += Utilities.centrarCadena(jsDataUser.getString(Constants.NOMBRE_EMPRESA_FULL))+"\n\n";
            sbImpresion += Utilities.centrarCadena(sbDireccionEmpresa)+"\n\n";
            sbImpresion += Utilities.centrarCadena(jsDataUser.getString(Constants.FICHO))+"\n";
            sbImpresion += Utilities.centrarCadena(jsDataUser.getString(Constants.VENDEDOR))+"\n";

            sbImpresion += objFragment.getString(R.string.print_ticket)+jsDataUser.getString(Constants.SERIE)+"-"+sbPrintConsecutivo+"\n";
            sbImpresion += objFragment.getString(R.string.print_fecha)+ sbFecha +" "+ sbHora +"\n";

            sbImpresion += sbPrintLoterias+"\n\n";

            sbImpresion += Utilities.centrarCadena(objFragment.getString(R.string.print_detalle_apuesta))+"\n";
            sbImpresion += "       "+objFragment.getString(R.string.print_titulo_numero)+"         "+objFragment.getString(R.string.print_titulo_valor)+"\n\n";

            int x = 0;

            for(int i=0; i < lstBancaItem.size(); i++) {
                Banca objItem = lstBancaItem.get(i);
                if(objItem.getCodigo_producto().equals(Loteria.COD_QUINELA)){
                    if(x == 0) {
                        sbImpresion += "       " + objFragment.getString(R.string.print_cabecera_quinela)+"\n";
                        x++;
                    }

                    sbImpresionAux = objItem.getNumero() + " $"+objItem.getValor();
                    sbImpresionAux = Utilities.centrarCadena(sbImpresionAux);
                    sbImpresion += sbImpresionAux+"\n";
                }
            }

            x=0;
            for(int i=0; i < lstBancaItem.size(); i++) {
                Banca objItem = lstBancaItem.get(i);
                if(objItem.getCodigo_producto().equals(Loteria.COD_PALE)){
                    if(x == 0) {
                        sbImpresion += "       " + objFragment.getString(R.string.print_cabecera_pale)+"\n";
                        x++;
                    }
                    sbImpresionAux = objItem.getNumero() + " $"+objItem.getValor();
                    sbImpresionAux = Utilities.centrarCadena(sbImpresionAux);
                    sbImpresion += sbImpresionAux+"\n";
                }
            }

            x=0;
            for(int i=0; i < lstBancaItem.size(); i++) {
                Banca objItem = lstBancaItem.get(i);
                if(objItem.getCodigo_producto().equals(Loteria.COD_TRIPLETA)){
                    if(x == 0) {
                        sbImpresion += "       " + objFragment.getString(R.string.print_cabecera_tripleta)+"\n";
                        x++;
                    }
                    sbImpresionAux = objItem.getNumero() + " $"+objItem.getValor();
                    sbImpresionAux = Utilities.centrarCadena(sbImpresionAux);
                    sbImpresion += sbImpresionAux+"\n";
                }
            }

            x=0;
            for(int i=0; i < lstBancaItem.size(); i++) {
                Banca objItem = lstBancaItem.get(i);
                if(objItem.getCodigo_producto().equals(Loteria.COD_SUPER_PALE)){
                    if(x == 0) {
                        sbImpresion += "       " + objFragment.getString(R.string.print_cabecera_super_pale)+"\n";
                        x++;
                    }
                    sbImpresionAux = objItem.getNumero() + " $"+objItem.getValor();
                    sbImpresionAux = Utilities.centrarCadena(sbImpresionAux);
                    sbImpresion += sbImpresionAux+"\n";
                }
            }

            sbImpresionAux = Utilities.centrarCadena("Total: $"+getTotal());
            sbImpresion += "\n"+sbImpresionAux+"\n\n";
            sbImpresion += Utilities.centrarCadena(sbMensajeEmpresa);
            /*Encriptar*/
            //String sbEncriptado = sbPrintConsecutivo;
            String sbEncriptado = jsDataUser.getString(Constants.SERIE)+Constants.SEPARADOR_GUION+sbPrintConsecutivo;
            /*TEA tea = new TEA(Constants.KEY_TEA.getBytes());
            byte[] original = sbEncriptado.getBytes();
            byte[] crypt = tea.encrypt(original);
            crypt = Base64.encode(crypt, Base64.DEFAULT);
            sbEncriptado = new String(crypt);*/
            /*Encriptar*/
            sbEncriptado = Utilities.encriptar(sbEncriptado);


            sbImpresionAux = Utilities.centrarCadena(sbEncriptado);
            sbImpresion += "\n"+sbImpresionAux;

            refreshForm();
            new ImprimirAsyncTask().execute(sbImpresion,sbEncriptado);

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
                Printer.print(params[0],params[1]);
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

    private void showPopupLoadLastBet(){
        AlertDialog.Builder builder = new AlertDialog.Builder(objFragment.context);

        // Get the layout inflater
        LayoutInflater inflater = objFragment.getLayoutInflater(null);

        View viewPopup = inflater
                .inflate(R.layout.pupup_load_bet, null);

        etSerie = (EditText) viewPopup.findViewById(R.id.etCargarSerie);
        etConsecutivo = (EditText) viewPopup.findViewById(R.id.etCargarConsecutivo);

        builder.setView(viewPopup)
                .setPositiveButton(
                        objFragment.getString(R.string.btn_consultar),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
        alert = builder.create();
        alert.show();

        Button btnEditarItem = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        btnEditarItem.setId(BUTTON_ID_LOAD);
        btnEditarItem.setOnClickListener(this);
    }

    private void loadLastBet(){
        boolean blState = true;
        String sbSerie = etSerie.getText().toString();
        String sbConsecutivo = etConsecutivo.getText().toString();

        if(Utilities.isEmpty(sbSerie)){
            etSerie.setError(objFragment.getString(R.string.msj_campo_requerido));
            blState = false;
        }

        if(Utilities.isEmpty(sbConsecutivo)){
            etConsecutivo.setError(objFragment.getString(R.string.msj_campo_requerido));
            blState = false;
        }

        if(blState){
            new ConsultarUltimaApuestaAsyncTask().execute(sbSerie,sbConsecutivo);
        }
    }

    private class ConsultarUltimaApuestaAsyncTask extends
            AsyncTask<String, Void, Object> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Utilities.showMessageProgress(activity,
                            activity.getString(R.string.load_best_last));
                }
            });
        }

        @Override
        protected Object doInBackground(String... params) {
            try {
                String sbPeticion = "";
                Connection objConexion = new Connection(objFragment.context);
                sbPeticion += Services.CARGAR_ULTIMA_APUESTA;
                sbPeticion += Constants.SEPARADOR_REGISTRO;
                sbPeticion += Constants.INICIAL_SERIE+params[0];
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
                    procesarUltimaApuesta(result.toString());
                }
            } catch (Exception e) {
                Utilities.showAlertDialog(objFragment.context, e.getMessage());
            }
        }
    }

    private void procesarUltimaApuesta(String sbRespuesta) throws AppException {

        MessageError objError = new MessageError(sbRespuesta);
        if (objError.esExitosa()) {
            if(alert != null)
                alert.cancel();
            //23|10|1|QUINELA&1453|20|2|PALE&112233|30|3|TRIPLETA
            sbRespuesta = sbRespuesta.substring(2);

            Parser objParser = new Parser(sbRespuesta, Constants.SEPARADOR_REGISTRO);

            while(objParser.hasMoreTokens()){

                Parser objParserCampo = new Parser(objParser.nextString(), Constants.SEPARADOR_CAMPO);

                String sbNumero = objParserCampo.nextString();
                String sbValor = objParserCampo.nextString();
                String sbCodigoProducto = objParserCampo.nextString();
                String sbProducto = objParserCampo.nextString();

                //int nuPosition = lstBancaItem.size();
                lstBancaItem.add(new Banca(sbNumero, sbValor, sbProducto, sbCodigoProducto));
            }
            objAdapter.notifyDataSetChanged();
        }
        else{
            Utilities.showAlertDialog(objFragment.context,objError.sbDescripcion);
        }
    }

    private void refreshForm(){
        try{
            /*for(int i=0; i<lstBancaItem.size(); i++){
                BancaTouchHelper.removeItemManually(i);
            }*/
            objAdapter.removeAll();

            if(loteriasCurrent != null){
                sbPrintLoterias = "";
                for(int i=0; i < loteriasCurrent.size(); i++){
                    Loteria objLoteria = loteriasCurrent.get(i);
                    objLoteria.setSelected(false);
                }
                objAdapterLoterias.notifyDataSetChanged();
                loteriasCurrent = null;
            }
        }catch(Exception e){
            Utilities.showAlertDialog(objFragment.context, objFragment.getString(R.string.msj_fail_refresh_form));
        }
    }
}
