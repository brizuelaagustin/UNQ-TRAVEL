package MetodoDePago;


public interface APITarjetaDeCredito {
    boolean validarDatos(String datosTarjeta);

    boolean reservarFondos(double montoAPagar, String datosTarjeta);

    boolean ejecutarTransaccion(double montoAPagar, String datosTarjeta);

}
