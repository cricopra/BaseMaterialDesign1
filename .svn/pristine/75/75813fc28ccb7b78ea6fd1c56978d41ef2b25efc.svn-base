package co.playtech.otrosproductosrd.handlers;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.ConfiguracionActivity;
import co.playtech.otrosproductosrd.adapters.DevicesBluetoothAdapter;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.Utilities;

/**
 * Created by Playtech2 on 08/02/2016.
 * Egonzalias
 */
public class ConfiguracionHandler implements View.OnClickListener, DevicesBluetoothAdapter.OnItemClickListener{

    private AlertDialog alertDevices;
    private ConfiguracionActivity objActivity;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayList<BluetoothDevice> objListaDispositvos;
    private boolean blState;
    private String sbMacPrinter;
    private String sbNombrePrinter;

    public ConfiguracionHandler(ConfiguracionActivity objAcitivity){
        this.objActivity = objAcitivity;
        //this.activity = objActivity.getActivity();
        loadData();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnSaveConfig:
                validateSaveConfig();
                break;

            case R.id.etMacPrinter:
                pairPrinter();
                break;
        }
    }

    private void loadData(){
        SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(objActivity.context);
        String sbIp = objShared.getString(Constants.IP, "");
        String sbPuerto = objShared.getString(Constants.PORT, "");
        sbMacPrinter = objShared.getString(Constants.MAC_PRINTER, "");
        sbNombrePrinter = objShared.getString(Constants.PRINTER, "");

        objActivity.etIp.setText(sbIp);
        objActivity.etPuerto.setText(sbPuerto);
        objActivity.etPrinter.setText(sbNombrePrinter);
    }

    private void validateSaveConfig(){
        String sbMensaje = objActivity.getString(R.string.msj_proceso_exitoso);
        blState = true;
        String sbIp = objActivity.etIp.getText().toString();
        String sbPuerto = objActivity.etPuerto.getText().toString();

        if(Utilities.isEmpty(sbIp)){
            objActivity.etIp.setError(objActivity.getString(R.string.msj_campo_requerido));
            blState = false;
        }

        if(Utilities.isEmpty(sbPuerto)){
            objActivity.etPuerto.setError(objActivity.getString(R.string.msj_campo_requerido));
            blState = false;
        }

        if(Utilities.isEmpty(sbMacPrinter)){
            sbMensaje += "\n"+objActivity.getString(R.string.msj_no_printer);
            sbMacPrinter = "";
            sbNombrePrinter = "";
        }


        if(blState){

            SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(objActivity.context);
            SharedPreferences.Editor edit = objShared.edit();
            edit.putString(Constants.IP, sbIp);
            edit.putString(Constants.PORT, sbPuerto);
            edit.putString(Constants.PRINTER, sbNombrePrinter);
            edit.putString(Constants.MAC_PRINTER, sbMacPrinter);
            edit.commit();

            Utilities.showAlertDialog(objActivity.context,sbMensaje );
        }
    }

    private void pairPrinter(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBluetoothAdapter == null){
            Utilities.showAlertDialog(objActivity.context,"Error bluetooth");
            return;
        }

        if (!mBluetoothAdapter.isEnabled())
            mBluetoothAdapter.enable();

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        objActivity.registerReceiver(mReceiver, filter);

        /*try {
            Thread.sleep(500);
        } catch (Exception e) {
        }*/
        mBluetoothAdapter.startDiscovery();
    }

    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(
                        BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    // showToast("Enabled");
                    // showEnabled();
                    // SystemApp.toast(objActivity, "Bluetooth ON");
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                objListaDispositvos = new ArrayList<BluetoothDevice>();
                Utilities.showMessageProgress(objActivity.context, objActivity.getString(R.string.load_search_devices));
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                Utilities.dismiss();
                showPopupDevices();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = (BluetoothDevice) intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                objListaDispositvos.add(device);
                Toast.makeText(objActivity.context, device.getName(), Toast.LENGTH_SHORT).show();
                // showToast("Found device " + device.getName());
            }
        }
    };

    private void showPopupDevices(){
        blState = true;
        objActivity.registerReceiver(mPairReceiver, new IntentFilter(
                BluetoothDevice.ACTION_BOND_STATE_CHANGED));
        AlertDialog.Builder builderDevices = new AlertDialog.Builder(objActivity.context);

        // Get the layout inflater
        LayoutInflater inflaterDevices = objActivity.getLayoutInflater();
        View viewPopupLoterias = inflaterDevices.inflate(R.layout.popup_devices, null);
        RecyclerView rvDevices = (RecyclerView) viewPopupLoterias.findViewById(R.id.rvDevicesBluetooth);

        final DevicesBluetoothAdapter objAdapterBluetooth = new DevicesBluetoothAdapter(objActivity.context, objListaDispositvos);
        rvDevices.setAdapter(objAdapterBluetooth);
        rvDevices.setLayoutManager(new LinearLayoutManager(objActivity.context));
        objAdapterBluetooth.setOnItemClickListener(this);

        builderDevices.setView(viewPopupLoterias);
        alertDevices = builderDevices.create();
        alertDevices.show();

    }

    public final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent
                        .getIntExtra(BluetoothDevice.EXTRA_BOND_STATE,
                                BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(
                        BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE,
                        BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED
                        && prevState == BluetoothDevice.BOND_BONDING) {
                    objActivity.etPrinter.setText(sbNombrePrinter);
                    Toast.makeText(objActivity.context, objActivity.getString(R.string.msj_device_connect), Toast.LENGTH_SHORT).show();
                } else if (state == BluetoothDevice.BOND_NONE
                        && prevState == BluetoothDevice.BOND_BONDED) {
                    objActivity.etPrinter.setText(null);
                    Toast.makeText(objActivity.context, objActivity.getString(R.string.msj_device_no_connect), Toast.LENGTH_SHORT).show();
                }
                if(alertDevices != null)
                    alertDevices.cancel();
            }
        }
    };

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond",
                    (Class[]) null);
            method.invoke(device, (Object[]) null);
            sbMacPrinter = device.getAddress();
            sbNombrePrinter = device.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond",
                    (Class[]) null);
            method.invoke(device, (Object[]) null);
            sbMacPrinter = "";
            sbNombrePrinter = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(DevicesBluetoothAdapter.ViewHolder item, int position) {
        BluetoothDevice device = objListaDispositvos.get(position);

        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            unpairDevice(device);
        } else {
            pairDevice(device);
        }
        if(alertDevices != null)
            alertDevices.cancel();
    }
}
