package co.playtech.otrosproductosrd.objects;

/**
 * Created by Egonzalias on 29/01/2016.
 */
public class Loteria {

    //Constantes
    public static final String COD_QUINELA = "1";
    public static final String COD_PALE = "2";
    public static final String COD_TRIPLETA = "3";
    public static final String COD_SUPER_PALE = "4";


    private String sbCodigo;
    private String sbNombre;
    private boolean isSelected;
    private String sbHoraCierre;

    public Loteria(){

    }
    public Loteria(String sbNombre){
        this.sbNombre = sbNombre;
    }

    public String getCodigo() {
        return sbCodigo;
    }

    public void setCodigo(String sbCodigo) {
        this.sbCodigo = sbCodigo;
    }

    public String getNombre() {
        return sbNombre;
    }

    public void setNombre(String sbNombre) {
        this.sbNombre = sbNombre;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getHoraCierre() {
        return sbHoraCierre;
    }

    public void setHoraCierre(String sbHoraCierre) {
        this.sbHoraCierre = sbHoraCierre;
    }
}
