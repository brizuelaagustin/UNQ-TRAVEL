package MetodoDePago;

import Clases.Cliente;

public class BilleteraVirtual extends MetodoDePago{
    private final APIBilleteraVirtual apiBilleteraVirtual;

    public BilleteraVirtual(APIBilleteraVirtual apiBilleteraVirtual) {
        this.apiBilleteraVirtual = apiBilleteraVirtual;
    }

    @Override
    public boolean validarDatos(double montoAPagar, Cliente cliente) {
       return apiBilleteraVirtual.validarDatos(montoAPagar, cliente.getCbu(), cliente.getAlias());
    }

    @Override
    public boolean reservarFondos(double montoAPagar, Cliente cliente){
        return apiBilleteraVirtual.reservarFondos(montoAPagar, cliente.getCbu(), cliente.getAlias());
    }

    @Override
    public boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
       return apiBilleteraVirtual.ejecutarTransaccion(montoAPagar);
    }

    @Override
    public String notificarResultados(Cliente cliente) {
       return apiBilleteraVirtual.notificarResultados(cliente.getAlias());
    }
}
