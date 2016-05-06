package co.playtech.otrosproductosrd.help;

/**
 * Created by Playtech2 on 01/02/2016.
 */
public class Constants {
    public static final int DIAS_SEMANA = 7;
    /**
     * Nulo numerico <code>INT_NULL</code> valor <code>-999999</code>
     */
    public static final int INT_NULL = -999999;
    // 29-ago-2005 - 13:30:03
    public static final int INT_NULL_QT = -1;
    public static final String SEPARADOR_GUION = new String("-");
    public static final String SEPARADOR_SERIE_MANUAL = new String("_");
    public static final String SEPARADOR_CAMPO = new String("|");
    public static final String SEPARADOR_REGISTRO = new String("&");
    public static final String SEPARADOR_LINEA = new String("@");
    public static final String SEPARADOR_LOTERIAS = new String(",");
    public static final String SEPARADOR_VERIFICACION_DOBLE_CHANCE = new String(";");
    public static final String SEPARADOR_APUESTAS = new String("___");
    public static final String SEPARADOR_VENTA_FUERA_LINEA_MOVIL = new String("##");
    public static final String SEPARADOR_NUMERAL = new String("#");
    public static final String SEPARADOR_PORCENTAJE = new String("%");
    // valores alfanumericos para boolean
    public static final String sbTRUE = new String("T");
    public static final String sbFALSE = new String("F");
    // Modificado por Usuario el 15-ago-2006 - 14:16:06
    public static final String TRUE_ESPANOL = new String("S");
    public static final String FALSE_ESPANOL = new String("N");
    // Constante para la solicitud de la r del servidor
    public static final String CADENA_PETICION_VERSION_APLICACION = new String("version");
    public static final String VERSION_APLICACION = new String("1.0");
    public static final int TIPO_ESTADO_ORDEN = 1;
    public static final int TIPO_ESTADO_CUADRILLA = 2;
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String INICIAL_SERIE = "T";

    public static final String KEY_CRYPT = "Play Technologies Apostandole a la Tecnologia";
    //Keys SharedPrefences
    public static final String SESSION_CURRENT = "SESSION_CURRENT";//true - login | false - logout
    public static final String PASSWORD_ADMIN = "password_admin";
    public static final String PASSWORD_ADMIN_VALUE = "1234";
    public static final String IP = "IP";
    public static final String PORT = "PORT";
    public static final String PRINTER = "PRINTER";
    public static final String MAC_PRINTER = "MAC_PRINTER";
    //Keys SharedPrefences Session
    public static final String JSON_USER = "JSON_USER";
    public static final String SERIE = "SERIE";
    public static final String CONSECUTIVO_ACTUAL = "CONSECUTIVO_ACTUAL";
    public static final String CONSECUTIVO_FINAL = "CONSECUTIVO_FINAL";
    public static final String CODIGO_USUARIO = "CODIGO_USUARIO";
    public static final String HORA = "HORA";
    public static final String FICHO = "FICHO";
    public static final String VENDEDOR = "VENDEDOR";
    public static final String FECHA_LOGIN = "FECHA_LOGIN";
    public static final String TERMINAL = "TERMINAL";
    public static final String MENSAJE = "MENSAJE";
    public static final String IVA = "IVA";
    public static final String CLAVE = "CLAVE";//clave user
    public static final String NOMBRE_EMPRESA = "NOMBRE_EMPRESA";
    public static final String NOMBRE_EMPRESA_FULL = "NOMBRE_EMPRESA_FULL";
    //Keys SharedPrefences Session LOTERIAS
    public static final String LOTERIAS = "LOTERIAS";//almacena las loterias que se cargan en login
    public static final String HORA_CIERRE = "HORA_CIERRE";
    public static final String NOMBRE_LOTERIA = "NOMBRE_LOTERIA";
    ///Keys SharedPrefences Session Recarga
    public static final String PROVEEDORES_RECARGA = "PROVEEDORES_RECARGA";
}
