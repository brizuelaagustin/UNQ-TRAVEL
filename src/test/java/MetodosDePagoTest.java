import Clases.Cliente;
import MetodoDePago.*;
import MetodosDeTraslado.Direccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MetodosDePagoTest {

    Cliente cliente;
    String datosTarjeta;
    double montoAPagar;

    TarjetaDeCredito tarjetaDeCredito;
    APITarjetaDeCredito apiTarjetaDeCredito;

    BilleteraVirtual billeteraVirtual;
    APIBilleteraVirtual apiBilleteraVirtual;

    TransferenciaBancaria transferenciaBancaria;
    APITransferenciaBancaria apiTransferenciaBancaria;


    @BeforeEach
    void setUp() {

        cliente = mock(Cliente.class);
        datosTarjeta = "3294 2938 2077";
        montoAPagar = 45000;
        when(cliente.getCbu()).thenReturn(123456789L);
        when(cliente.getAlias()).thenReturn("V");
        when(cliente.getDatosTarjeta()).thenReturn(datosTarjeta);

        apiTarjetaDeCredito = mock(APITarjetaDeCredito.class);
        tarjetaDeCredito = new TarjetaDeCredito(apiTarjetaDeCredito);

        when(apiTarjetaDeCredito.validarDatos(datosTarjeta)).thenReturn(true);
        when(apiTarjetaDeCredito.reservarFondos(montoAPagar, datosTarjeta)).thenReturn(true);
        when(apiTarjetaDeCredito.ejecutarTransaccion(montoAPagar, datosTarjeta)).thenReturn(true);

        apiBilleteraVirtual = mock(APIBilleteraVirtual.class);
        billeteraVirtual = new BilleteraVirtual(apiBilleteraVirtual);

        when(apiBilleteraVirtual.validarDatos(montoAPagar, cliente.getCbu(), cliente.getAlias())).thenReturn(true);
        when(apiBilleteraVirtual.reservarFondos(montoAPagar, cliente.getCbu(), cliente.getAlias())).thenReturn(true);
        when(apiBilleteraVirtual.ejecutarTransaccion(montoAPagar)).thenReturn(true);
        when(apiBilleteraVirtual.notificarResultados(cliente.getAlias())).thenReturn("Pago realizado");

        apiTransferenciaBancaria = mock(APITransferenciaBancaria.class);
        transferenciaBancaria = new TransferenciaBancaria(apiTransferenciaBancaria);

        when(apiTransferenciaBancaria.validarDatos(cliente.getCbu(), cliente.getAlias())).thenReturn(true);
        when(apiTransferenciaBancaria.ejecutarTransaccion(montoAPagar, cliente.getCbu(), cliente.getAlias())).thenReturn(true);
    }

    @Test
    void test001_UnaTarjetaDeCreditoPuedeRealizarUnPago(){
       String resultado = tarjetaDeCredito.procesarPago(montoAPagar, cliente);
       assertEquals(("Pago exitoso para: " + cliente + "con la tarjeta: " + cliente.getDatosTarjeta()), resultado);

        InOrder inOrder = inOrder(apiTarjetaDeCredito);
        inOrder.verify(apiTarjetaDeCredito).validarDatos(datosTarjeta);
        inOrder.verify(apiTarjetaDeCredito).reservarFondos(montoAPagar, datosTarjeta);
        inOrder.verify(apiTarjetaDeCredito).ejecutarTransaccion(montoAPagar, datosTarjeta);
        verifyNoMoreInteractions(apiTarjetaDeCredito);
    }

    @Test
    void test002_UnaBilleteraVirtualPuedeRealizarUnPago(){
        String resultado = billeteraVirtual.procesarPago(montoAPagar, cliente);
        assertEquals("Pago realizado", resultado);

        InOrder inOrder = inOrder(apiBilleteraVirtual);
        inOrder.verify(apiBilleteraVirtual).validarDatos(montoAPagar, cliente.getCbu(), cliente.getAlias());
        inOrder.verify(apiBilleteraVirtual).reservarFondos(montoAPagar, cliente.getCbu(), cliente.getAlias());
        inOrder.verify(apiBilleteraVirtual).ejecutarTransaccion(montoAPagar);
        inOrder.verify(apiBilleteraVirtual).notificarResultados(cliente.getAlias());
        verifyNoMoreInteractions(apiBilleteraVirtual);
    }

    @Test
    void test003_UnaTransferenciaBancaraPuedeRealizarUnPago(){
        String resultado = transferenciaBancaria.procesarPago(montoAPagar, cliente);
        assertEquals(("Transferencia exitosa para el cliente: " + cliente + "con CBU: " + cliente.getCbu()), resultado);

        InOrder inOrder = inOrder(apiTransferenciaBancaria);
        inOrder.verify(apiTransferenciaBancaria).validarDatos(cliente.getCbu(), cliente.getAlias());
        inOrder.verify(apiTransferenciaBancaria).ejecutarTransaccion(montoAPagar, cliente.getCbu(), cliente.getAlias());
        verifyNoMoreInteractions(apiTransferenciaBancaria);
    }

    @Test
    void test004_UnMetodoDePagoPuedeNoValidadLosDatos(){
        when(apiTarjetaDeCredito.validarDatos(datosTarjeta)).thenReturn(false);
        String resultado = tarjetaDeCredito.procesarPago(montoAPagar, cliente);
        assertEquals("No se pudieron validar los datos", resultado);
    }

    @Test
    void test005_UnMetodoDePagoPuedeNoTenerFondos(){
        when(apiTarjetaDeCredito.reservarFondos(montoAPagar, datosTarjeta)).thenReturn(false);
        String resultado = tarjetaDeCredito.procesarPago(montoAPagar, cliente);
        assertEquals("No se pudieron reservar los fondos", resultado);
    }

    @Test
    void test006_UnMetodoDePagoPuedeNoEjecutarLaTransaccion(){
        when(apiTarjetaDeCredito.ejecutarTransaccion(montoAPagar, datosTarjeta)).thenReturn(false);
        String resultado = tarjetaDeCredito.procesarPago(montoAPagar, cliente);
        assertEquals("No se pudo ejecutar la transacción", resultado);
    }

    @Test
    void test007_ElClienteGuardaTodosSusDatos() {
        Cliente cliente1 = new Cliente("fernando.001", 5001230639L, "cvv 717", "fernando10@gmail.com");

        assertEquals("fernando.001", cliente1.getAlias());
        assertEquals(5001230639L, cliente1.getCbu());
        assertEquals("cvv 717", cliente1.getDatosTarjeta());
        assertEquals("fernando10@gmail.com", cliente1.getEmail());
    }
}
