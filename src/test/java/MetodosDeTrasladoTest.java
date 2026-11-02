import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import Clases.Reserva;
import MetodosDeTraslado.*;

public class MetodosDeTrasladoTest {

    Reserva reserva1;
    Direccion direccionDestino1;
    PlataformaTransporte plataformaTransporte;
    TrasladoEstandar trasladoEstandar;
    TrasladoVIP trasladoVIP;
    PuntoDeEncuentro puntoDeEncuentro;

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
        puntoDeEncuentro = new PuntoDeEncuentro(3);
    }

    // TESTS DE TRASLADO ESTANDAR
    @Test
    void test001_trasladoEstandarDelegaElCalculoALaPlataformaYRetornaValorCorrecto() {

        float costoCalculado = trasladoEstandar.calcularCosto(reserva1);
        assertEquals(2500f, costoCalculado);
    }

    // TESTS DE TRASLADO VIP
    @Test
    void test002_trasladoVipCalculaCostoEnBaseAlPrecioTotalDeLaReserva() {

        float costoCalculado = trasladoVIP.calcularCosto(reserva1);
        assertEquals(2500f, costoCalculado);
    }


    // TESTS DE PUNTO DE ENCUENTRO
    @Test
    void test003_trasladoPuntoDeEncuentroSiempreRetornaCostoCero() {
        assertEquals(0.0f, puntoDeEncuentro.calcularCosto(reserva1));
    }
}