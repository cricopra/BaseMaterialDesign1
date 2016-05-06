package co.playtech.otrosproductosrd.help;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Egonzalias on 01/02/2016.
 */
public class Connection {

    private OutputStream out = null;
    private InputStream in = null;
    private DataInputStream dis = null;
    private Socket objSocket;
    //private SessionManager objSession;
    private Context objContext;
    private String sbIp;
    private String sbPuerto;
    private String sbTimeout;
    private int NOTIFY_INTERVAL = 1000;

    public Connection(Context context) {
        try {
            objContext = context;
            SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(context);
            sbIp = objShared.getString(Constants.IP, "");
            sbPuerto = objShared.getString(Constants.PORT, "");
        } catch (Exception ex) {
        }
    }

    @SuppressWarnings("deprecation")
    public String sendTransaction(String sbPeticion) throws AppException{
        try{
            String sbRespuesta = "";

            //objSocket = new Socket(sbIp, Integer.parseInt(sbPuerto));
            objSocket = new Socket();

            if(Utilities.isEmpty(sbIp) || Utilities.isEmpty(sbPuerto)){
                throw AppException.getException(10, "Por favor configure ip y puertos correctamente");
            }

            //int nuTimeOut = Integer.parseInt(sbTimeout) * NOTIFY_INTERVAL;
            objSocket.connect(new InetSocketAddress(sbIp, Integer.parseInt(sbPuerto)), 30000);
            //objSocket.connect(new InetSocketAddress("201.216.26.127", 55954), 30000);
            //TimeOut para el tiempo de lectura
            //objSocket.setSoTimeout(nuTimeOut);

            out = objSocket.getOutputStream();
            sbPeticion = sbPeticion+"\n";
            out.write(sbPeticion.getBytes());
            out.flush();
            in = objSocket.getInputStream();
            dis = new DataInputStream(in);
            sbRespuesta = dis.readLine();

            out.close();
            in.close();
            dis.close();
            if (objSocket != null)
                objSocket.close();
            return sbRespuesta;
        }
        catch(UnknownHostException ex){
            //throw AppException.getException(10, ex.getMessage());
            throw AppException.getException(10, "Problema con la Ip y/o Puerto guardados");
        }
        catch(IOException ex){
            throw AppException.getException(20, "No Se Pudo Realizar la Conexion");
        }
    }

}
