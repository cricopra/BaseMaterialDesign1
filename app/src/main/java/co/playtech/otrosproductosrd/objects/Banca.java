package co.playtech.otrosproductosrd.objects;

/**
 * Created by Egonzalias on 27/01/2016.
 */
public class Banca {

    private String numero;
    private String valor;
    private String producto;
    private String codigo_producto;

    public Banca(){
    }

    public Banca(String numero, String valor, String producto, String codigo_producto){
        this.numero = numero;
        this.valor = valor;
        this.producto = producto;
        this.codigo_producto = codigo_producto;
    }




    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
