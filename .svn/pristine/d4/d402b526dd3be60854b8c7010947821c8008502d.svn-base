package co.playtech.otrosproductosrd.help;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import java.io.OutputStream;
import java.lang.reflect.Method;

import co.playtech.otrosproductosrd.activities.MainActivity;

/**
 * Created by Playtech2 on 02/02/2016.
 */
public class Printer {

    public static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    public static String sbMacPrinter = "";

    @SuppressWarnings("resource")
    public static Object print(String sbTexto, String sbDataCodeQR) throws Exception {
        BluetoothAdapter btadapter;
        BluetoothSocket sock = null;
        OutputStream objOut = null;
        try {
            SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);
            sbMacPrinter = objShared.getString(Constants.MAC_PRINTER, "");

            //sbMacPrinter = "00:1F:B7:04:F7:04";
            if (sbMacPrinter != null && !sbMacPrinter.equals("")) {

                Log.d("PlayTechBluetooth", "Inicio de Impresora Bluetooth");
                String sbMacImpresora = "";

                try {
                    Looper.prepare();
                } catch (Exception e) {
                    Log.d("SN", "LooperError:" + e.getMessage());
                }

                // sbMacImpresora = sbMacPrinter == null ||
                // sbMacPrinter.equals("") ? "00:02:0A:02:22:15" : sbMacPrinter;
                sbMacImpresora = sbMacPrinter;
                // sbMacImpresora = "00:02:0A:02:46:CD";
                // objAdmin = new AdministracionHandler(administracionActivity)

                Log.d("PlaytechBluetooth",
                        ":::: ENTRO A IMPRESION BLUETOOTH ::::");
                Log.d("PlaytechBluetooth", "MAC IMPRESORA:: " + sbMacImpresora);
                Log.d("PlaytechBluetooth",
                        "Longitud Cadena:: " + sbTexto.length());
                Log.d("PlaytechBluetooth", "Cadena:: " + sbTexto);

                btadapter = BluetoothAdapter.getDefaultAdapter();
                if (btadapter.isDiscovering()) {
                    Log.d("PlaytechBluetooth", "Despues de isDiscovering");
                    btadapter.cancelDiscovery();
                }

                BluetoothDevice device = btadapter
                        .getRemoteDevice(sbMacImpresora);

                // Check bounded device
                boolean state = false;
                if (device != null && device.getBondState() == 12)
                    state = true;
                else
                    state = false;
                if (!state) {
                    throw AppException.getException(40,
                            "No se encuentra el dispositivo con Mac:"
                                    + sbMacImpresora);
                    // throw new
                    // Exception("No se encuentra el dispositivo con Mac:" +
                    // sbMacImpresora);
                }

                try {
                    Method m = device.getClass().getMethod(
                            "createInsecureRfcommSocket",
                            new Class[] { int.class });
                    sock = (BluetoothSocket) m.invoke(device,
                            Integer.valueOf(1));
                    sock.connect();
                } catch (Exception e) {
                    try {
                        sock = device.createRfcommSocketToServiceRecord(UUID.fromString(SPP_UUID));
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e2) {
                            e.printStackTrace();
                        }
                        sock.connect();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                objOut = sock.getOutputStream();

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*Bitmap bitmapOrg = BitmapFactory.decodeResource(AutenticacionActivity.instance.getResources(), R.drawable.gane_header);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitmapOrg.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte [] ba = bao.toByteArray();
                String sbBase64 = Base64.encodeToString(ba, Base64.DEFAULT);
                objOut.write(BluetoothUtilPrinter.getImage(sbBase64, 370));*/

                sbTexto = "\n\n"+sbTexto;
                objOut.write(sbTexto.getBytes());

                if(!Utilities.isEmpty(sbDataCodeQR)){
                    objOut.write(BluetoothUtilPrinter.getDataQRCode(sbDataCodeQR, 5));
                }
                objOut.write(0x0A);

                Thread.sleep(1000);

                Log.d("PlaytechBluetooth", "Impresion Satisfactoria");
                return null;
            }

        } catch (AppException e) {
            throw e;
        } catch (IOException e) {
            Log.d("PlaytechBluetooth", "Error IO: " + e.getMessage());
            // e.printStackTrace();
            throw new Exception("No se encuentra el dispositivo Bluetooth");
        } catch (Exception ex) {
            Log.d("PlaytechBluetooth", "Error: " + ex.getMessage());
            // ex.printStackTrace();
            throw ex;
        } finally {
            Log.d("PlaytechBluetooth", "Cerrando Conexiones...");
            try {
                if (sock != null) {
                    sock.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (objOut != null) {
                    objOut.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
