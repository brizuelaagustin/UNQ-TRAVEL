import CatalogoTuristico.Catalogo;
import CatalogoTuristico.ServicioTuristico;
import Clases.Cliente;
import Clases.Reserva;
import EstadosDeReserva.*;
import Excepciones.reservas.reservaFinalizada;
import MetodoDePago.MetodoDePago;
import MetodosDeTraslado.Direccion;
import MetodosDeTraslado.MetodoDeTraslado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class EstadosDeReservaTest {

//hay que hacer mockito
    Reserva reserva;
    EstadoDeReserva estado;
    MetodoDeTraslado metodoTraslado;
    Direccion direccion;
    MetodoDePago metodoPago;
    Catalogo catalogo;
    Cliente cliente;

    EstadoDeReserva estadoPendiente;
    EstadoDeReserva estadoConfirmado;
    EstadoDeReserva estadoCheckIn;
    EstadoDeReserva estadoEnCurso;
    EstadoDeReserva estadoFinalizado;
    EstadoDeReserva estadoCancelado;

    Reserva reservaPendiente;
    Reserva reservaConfirmada;
    Reserva reservaCheckIn;
    Reserva reservaEnCurso;
    Reserva reservaFinalizada;
    Reserva reservaCancelada;

    @BeforeEach
    void setup(){
        estado=         mock(EstadoDeReserva.class);
        metodoTraslado= mock(MetodoDeTraslado.class);
        direccion=      mock(Direccion.class);
        metodoPago=     mock(MetodoDePago.class);
        catalogo=       mock(Catalogo.class);
        cliente=        mock(Cliente.class);
        reserva= new Reserva(estado, metodoTraslado, 10, 20, 5, direccion, metodoPago, catalogo, cliente);

        estadoPendiente=    new Pendiente();
        estadoConfirmado=   new Confirmada();
        estadoCheckIn=      new CheckIn();
        estadoEnCurso=      new EnCurso();
        estadoFinalizado=   new Finalizada();
        estadoCancelado=    new Cancelada();

        reservaPendiente= new Reserva(estadoPendiente, metodoTraslado, 10, 20, 5, direccion, metodoPago, catalogo, cliente);
        reservaConfirmada= new Reserva(estadoConfirmado, metodoTraslado, 10, 20, 5, direccion, metodoPago, catalogo, cliente);
        reservaCheckIn= new Reserva(estadoCheckIn, metodoTraslado, 10, 20, 5, direccion, metodoPago, catalogo, cliente);
        reservaEnCurso= new Reserva(estadoEnCurso, metodoTraslado, 10, 20, 5, direccion, metodoPago, catalogo, cliente);
        reservaFinalizada= new Reserva(estadoFinalizado, metodoTraslado, 10, 20, 5, direccion, metodoPago, catalogo, cliente);
        reservaCancelada= new Reserva(estadoCancelado, metodoTraslado, 10, 20, 5, direccion, metodoPago, catalogo, cliente);
    }




    //sintacticamente como testeo que se delega en el estado?

    @Test
    void test001_UnaReservaPendientePuedePasarAConfirmada(){
            reservaPendiente.confirmarReserva();
            assertInstanceOf(Confirmada.class, reservaPendiente.getEstadoDeReserva());
    }
    @Test
    void test002_UnaReservaConfirmadaPuedePasarACheckIn(){
            reservaConfirmada.checkInReserva();
            assertInstanceOf(CheckIn.class, reservaConfirmada.getEstadoDeReserva());
    }
    @Test
    void test003_UnaReservaEnCheckInPuedePasarAEnCurso(){
            reservaCheckIn.enCursoReserva();
            assertInstanceOf(EnCurso.class, reservaCheckIn.getEstadoDeReserva());
    }
    @Test
    void test004_UnaReservaEnCursoPuedePasarAFinalizada(){
            reservaEnCurso.finalizarReserva();
            assertInstanceOf(Finalizada.class, reservaEnCurso.getEstadoDeReserva());
    }
    @Test
    void test005_UnaReservaPendientePuedeSerCancelada(){
        reservaPendiente.cancelarReserva();
        assertInstanceOf(Cancelada.class, reservaPendiente.getEstadoDeReserva());
    }
    @Test
    void test006_UnaReservaConfirmadaPuedeSerCancelada(){
        reservaConfirmada.cancelarReserva();
        assertInstanceOf(Cancelada.class, reservaConfirmada.getEstadoDeReserva());
    }
    @Test
    void test007_UnaReservaEnCheckInPuedeSerCancelada(){
        reservaCheckIn.cancelarReserva();
        assertInstanceOf(Cancelada.class, reservaCheckIn.getEstadoDeReserva());
    }
    @Test
    void test008_UnaReservaEnCursoPuedeSerCancelada(){
        reservaEnCurso.cancelarReserva();
        assertInstanceOf(Cancelada.class, reservaEnCurso.getEstadoDeReserva());
    }
    @Test
    void test009_UnaReservaFinalizadaNoPuedeSerCanelada(){
        try {
            reservaFinalizada.cancelarReserva();
            fail("Operacion invalida, la reserva ya finalizo");
        } catch (reservaFinalizada e) {
                assertEquals(e.getMessage(), "Operacion invalida, la reserva ya finalizo");
        }
    }
}
