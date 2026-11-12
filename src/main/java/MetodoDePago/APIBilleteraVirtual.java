package MetodoDePago;


public interface APIBilleteraVirtual {

    boolean validarDatos(double montoAPagar, long cbu, String alias);

    boolean reservarFondos(double montoAPagar, long cbu, String alias);

    boolean ejecutarTransaccion(double montoAPagar);

    String notificarResultados(String alias);
}
