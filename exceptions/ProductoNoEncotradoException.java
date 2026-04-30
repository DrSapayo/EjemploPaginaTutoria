package exceptions;

public class ProductoNoEncotradoException extends Exception {
    //creamos una excepcion personalizada
    public ProductoNoEncotradoException(String mensaje){
        super(mensaje);
    }

}
