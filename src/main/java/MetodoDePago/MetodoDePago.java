package MetodoDePago;

import Clases.Cliente;

public abstract class MetodoDePago {

    public String procesarPago(double montoAPagar, Cliente cliente) {
        if(!validarDatos (montoAPagar, cliente)) {
        return "No se pudieron validar los datos";
    }
        if(!reservarFondos (montoAPagar, cliente)) {
            return "No se pudieron reservar los fondos";
        }
        if(!ejecutarTransaccion (montoAPagar, cliente)) {
        return "No se pudo ejecutar la transacción";
    }
        return notificarResultados(cliente);
    }
    public abstract boolean validarDatos(double montoAPagar, Cliente cliente);
    public abstract boolean reservarFondos(double montoAPagar, Cliente cliente);
    public abstract boolean ejecutarTransaccion(double montoAPagar, Cliente cliente);
    public abstract String notificarResultados(Cliente cliente);
}
