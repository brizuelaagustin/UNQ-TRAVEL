import CatalogoTuristico.Catalogo;
import Clases.Cliente;
import EstadosDeReserva.EstadoDeReserva;
import Excepciones.reservas.MetodoDeTrasladoNoDisponibleException;
import MetodoDePago.MetodoDePago;
import NotificacionesDeReserva.NotificadorDeReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import Clases.Reserva;
import MetodosDeTraslado.*;

import java.time.LocalDate;

public class MetodosDeTrasladoTest {

    Reserva reserva1;
    Direccion direccionDestino1;
    PlataformaTransporte plataformaTransporte;
    TrasladoEstandar trasladoEstandar;
    TrasladoVIP trasladoVIP;
    PuntoDeEncuentro puntoDeEncuentro;

    EstadoDeReserva estado;
    MetodoDeTraslado metodoTraslado;
    Direccion direccion;
    MetodoDePago metodoPago;
    Catalogo catalogo;
    Cliente cliente;
    NotificadorDeReserva notificador;
    LocalDate fechaInicio;
    LocalDate fechaFin;

    @BeforeEach
    void setUp() {

        direccionDestino1 = mock(Direccion.class);
        reserva1 = mock(Reserva.class);

        when(reserva1.getCantidadPasajeros()).thenReturn(4);
        when(reserva1.getDireccionDestino()).thenReturn(direccionDestino1);
        when(reserva1.getPrecio()).thenReturn(10000f); //El método calcularCosto retornara 2500f

        plataformaTransporte = mock(PlataformaTransporte.class);
        when(plataformaTransporte.estimarTraslado(4, direccionDestino1)).thenReturn(2500f);


        trasladoEstandar = new TrasladoEstandar(plataformaTransporte);
        trasladoVIP = new TrasladoVIP(0.15f, 1000f);
        puntoDeEncuentro = new PuntoDeEncuentro();

        estado = mock(EstadoDeReserva.class);
        metodoTraslado = new PuntoDeEncuentro();
        direccion = mock(Direccion.class);
        metodoPago = mock(MetodoDePago.class);
        catalogo = mock(Catalogo.class);
        cliente = mock(Cliente.class);
        notificador = mock(NotificadorDeReserva.class);
        fechaInicio = mock(LocalDate.class);
        fechaFin = mock(LocalDate.class);
    }

    // TESTS DE TRASLADO ESTANDAR
    @Test
    void test001_trasladoEstandarDelegaElCalculoALaPlataformaYRetornaValorCorrecto() {

        float costoCalculado = trasladoEstandar.calcularCosto(reserva1);
        assertEquals(2500f, costoCalculado);
    }

    @Test
    void test002_trasladoEstandarSiempreEstaDisponible() {
        assertTrue(trasladoEstandar.estaDisponible(reserva1));
    }

    // TESTS DE TRASLADO VIP
    @Test
    void test003_trasladoVipCalculaCostoEnBaseAlPrecioTotalDeLaReserva() {

        float costoCalculado = trasladoVIP.calcularCosto(reserva1);
        assertEquals(2500f, costoCalculado);
    }

    @Test
    void test004_trasladoVipSiempreEstaDisponible() {
        assertTrue(trasladoVIP.estaDisponible(reserva1));
    }


    // TESTS DE PUNTO DE ENCUENTRO
    @Test
    void test005_trasladoPuntoDeEncuentroSiempreRetornaCostoCero() {
        assertEquals(0.0f, puntoDeEncuentro.calcularCosto(reserva1));
    }

    @Test
    void test006_UnaReservaSinCuposDeGuiaNoPuedeAgregarUnPuntoDeEncuentro(){
        try {
            new Reserva(estado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador, fechaInicio, fechaFin, false);
        } catch (MetodoDeTrasladoNoDisponibleException e) {
            assertEquals(e.getMessage(), "El método de traslado elegido no tiene disponibilidad");
        }
    }

    @Test
    void test007_laDireccionGuardaTodosSusDatos() {
        Direccion direccion = new Direccion("Av. Mitre 500", "Quilmes", "Buenos Aires", "1878");

        assertEquals("Av. Mitre 500", direccion.getCalle());
        assertEquals("Quilmes", direccion.getCiudad());
        assertEquals("Buenos Aires", direccion.getProvincia());
        assertEquals("1878", direccion.getCodigoPostal());
    }
}
