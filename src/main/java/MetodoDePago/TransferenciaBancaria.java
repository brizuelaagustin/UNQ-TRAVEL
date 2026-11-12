package MetodoDePago;

import Clases.Cliente;

public class TransferenciaBancaria extends MetodoDePago{
    private final APITransferenciaBancaria apiTransferenciaBancaria;

    public TransferenciaBancaria(APITransferenciaBancaria apiTransferenciaBancaria) {
        this.apiTransferenciaBancaria = apiTransferenciaBancaria;
    }

    @Override
    public boolean validarDatos(double montoAPagar, Cliente cliente) {
        return apiTransferenciaBancaria.validarDatos(cliente.getCbu(), cliente.getAlias());
    }

    @Override
    public boolean reservarFondos(double montoAPagar, Cliente cliente){
        return  true; //la transferencia es directa
    }

    @Override
    public boolean ejecutarTransaccion(double montoAPagar, Cliente cliente) {
        return apiTransferenciaBancaria.ejecutarTransaccion(montoAPagar, cliente.getCbu(), cliente.getAlias());
    }

    @Override
    public String notificarResultados(Cliente cliente) {
        return "Transferencia exitosa para el cliente: " + cliente + "con CBU: " + cliente.getCbu();
    }
}
