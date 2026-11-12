package MetodoDePago;

import Clases.Cliente;

public class TarjetaDeCredito extends MetodoDePago {

    private final APITarjetaDeCredito apiTarjetaDeCredito;

    public TarjetaDeCredito(APITarjetaDeCredito apiTarjetaDeCredito) {
        this.apiTarjetaDeCredito = apiTarjetaDeCredito;
    }

    @Override
    public boolean validarDatos(double montoAPagar, Cliente cliente) {
        return apiTarjetaDeCredito.validarDatos(cliente.getDatosTarjeta());
    }

    @Override
    public boolean reservarFondos(double montoAPagar, Cliente cliente){
        return apiTarjetaDeCredito.reservarFondos(montoAPagar, cliente.getDatosTarjeta());
    }

    @Override
    public boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return apiTarjetaDeCredito.ejecutarTransaccion(montoAPagar, cliente.getDatosTarjeta());
    }

    @Override
    public String notificarResultados(Cliente cliente) {
        return "Pago exitoso para: " + cliente + "con la tarjeta: " + cliente.getDatosTarjeta();
    }
}