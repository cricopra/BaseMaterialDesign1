package co.playtech.otrosproductosrd.help;

/**
 * Created by Playtech2 on 01/02/2016.
 */
public class AppException extends  Exception{

    private int nuCodeError;
    private String sbParamsError;
    //private String message;
    private boolean isSendProccess;

    private AppException(int nuNewCodeError, String sbNewParams){
        nuCodeError = nuNewCodeError;
        sbParamsError = sbNewParams;
    }

    public static AppException getException(int nuCodeError, String sbParamsError){
        return new AppException( nuCodeError, sbParamsError );
    }

    public int getCodeError(){
        return nuCodeError;
    }

    public String getMessage() {
        return sbParamsError;
    }

    public boolean isSendProccess() {
        return isSendProccess;
    }

    public static AppException getException(Exception e){
        if ( (e.getClass().getName()).equals(AppException.class.getName()) ){
            return (AppException)e;
        }
        e.printStackTrace();
        System.out.println(e.getMessage());
        return new AppException( 10, e.toString() );
    }
}
