package co.playtech.otrosproductosrd.help;

/**
 * Created by Playtech2 on 01/02/2016.
 */
public class MessageError {

    public int nuCodigoError;
    public String sbDescripcion;

    private Parser objParserRegistros;

    public MessageError(String sbMensaje)
            throws AppException{
        try {
            this.objParserRegistros = new Parser(sbMensaje, Constants.SEPARADOR_REGISTRO);
            this.nuCodigoError = this.objParserRegistros.nextInt();

            if (!this.esExitosa()){
                this.sbDescripcion = this.objParserRegistros.nextString();
            }

        } catch (Exception e) {
            AppException.getException(e);
        }
    }

    public boolean esExitosa(){
        return (this.nuCodigoError == 0);
    }

    public static String validateTypeError(Object objError) throws AppException{
        try{
            //Log.d("objeto error", objError.toString());
            String sbResult = "";

            if(objError instanceof AppException){
                AppException error = (AppException)objError;
                sbResult = error.getMessage();
                System.out.println("resultap->"+sbResult);
            }

            if(objError instanceof Exception){
                Exception error = (Exception)objError;
                sbResult = error.getMessage();
                System.out.println("resultexc->"+sbResult);
            }
            return sbResult;
        }catch(Exception e){
            throw AppException.getException(e);
        }

    }
}
