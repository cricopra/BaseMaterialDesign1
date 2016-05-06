package co.playtech.otrosproductosrd.help;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.activities.MainActivity;
import co.playtech.otrosproductosrd.activities.SplashActivity;

/**
 * Created by Egonzalias on 27/01/2016.
 */
public class Utilities {

    private static ProgressDialog pdLoading;
    private static final int LONGITUD_PAPEL = 32;


    public static boolean isEmpty(String data){

        if(data == null || data.equals(""))
            return true;
        return false;
    }

    public static void showAlertDialog(Context context, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .
                        // setIcon(R.drawable.play18).
                                setTitle(context.getString(R.string.app_name))
                .setCancelable(false)
				/*
				 * .setNegativeButton("Cancelar", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface dialog, int id) { dialog.cancel(); }
				 * })
				 */
                .setPositiveButton("Continuar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // metodo que se debe implementar
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showMessageProgress(Context context, String sbMessage) {
        pdLoading = new ProgressDialog(context);
        // pdLoading.setIcon(R.drawable.play18);
        pdLoading.setTitle(context.getString(R.string.app_name));
        pdLoading.setMessage(sbMessage);
        pdLoading.setIndeterminate(false);
        pdLoading.setCancelable(false);
        pdLoading.show();
    }

    public static void dismiss() {
        if (pdLoading != null)
            pdLoading.dismiss();
    }

    /**
     * @param sbFechaParametro
     * @return  0  fecha son iguales
     *          1  sbFechaParametro es mayor
     *          -1 sbFechaParametro es menor
     * @throws ParseException
     */
    public static int compararFechas(String sbFechaParametro)
            throws ParseException {
        int reult;
        Calendar fechaActual = Calendar.getInstance();

        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj = curFormater.parse(sbFechaParametro);
        Calendar fecha2 = Calendar.getInstance();
        fecha2.setTime(dateObj);
        System.out.println("ACTUAL:::" + fechaActual.getTime());
        System.out.println("SERVER:::" + fecha2.getTime());
        reult = fecha2.compareTo(fechaActual);
        return reult;
    }

    //Sahil Muthoo - StackOverFlow
    public static String centrarCadena(String s) {
        /*if (pad == null)
            throw new NullPointerException("pad cannot be null");
        if (pad.length() <= 0)
            throw new IllegalArgumentException("pad cannot be empty");*/
        if (s == null || LONGITUD_PAPEL <= s.length())
            return s;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (LONGITUD_PAPEL - s.length()) / 2; i++) {
            sb.append(" ");
        }
        sb.append(s);
        while (sb.length() < LONGITUD_PAPEL) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public static String getDataUserPreferences(Context context) {
        try{
            if(context != null){
                SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(context);
                return objShared.getString(Constants.JSON_USER, "");
            }
        }catch (Exception e){
            throw e;
        }
        return null;
    }

    public static void setDataUserPreferences(Context context, JSONObject jsonData){
        try{
            SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = objShared.edit();
            edit.putString(Constants.JSON_USER, jsonData.toString());
            edit.commit();
        }catch (Exception e){
            throw e;
        }
    }

    public static void clearAllPreferencesSession(Context context){
        SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = objShared.edit();
        edit.putString(Constants.JSON_USER, "");
        edit.putBoolean(Constants.SESSION_CURRENT, false);
        edit.commit();
        System.out.println(objShared.getAll());
    }

    public static String getVersion(Context context){
        String sbVersion = "";
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            sbVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sbVersion;
    }

    public static String encriptar(String texto) {

        String secretKey = Constants.KEY_CRYPT; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            /*String data = "";
            for(int i=0; i<texto.length();i++){
                data += texto.charAt(i)+"%";
            }*/

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encode(buf, Base64.DEFAULT);
            base64EncryptedString = new String(base64Bytes);

            base64EncryptedString = base64EncryptedString.replace("O","-");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static String desencriptar(String textoEncriptado) throws Exception {

        String secretKey = Constants.KEY_CRYPT; //llave para encriptar datos
        String base64EncryptedString = "";

        try {
            byte[] message = Base64.decode(textoEncriptado.getBytes("utf-8"), Base64.DEFAULT);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");

            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);

            byte[] plainText = decipher.doFinal(message);

            base64EncryptedString = new String(plainText, "UTF-8");

            base64EncryptedString = base64EncryptedString.replace("%","");

        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new java.util.Date());
    }
}
