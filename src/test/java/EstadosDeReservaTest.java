import CatalogoTuristico.Catalogo;
import CatalogoTuristico.ServicioTuristico;
import Clases.Cliente;
import Clases.Reserva;
import EstadosDeReserva.*;
import Excepciones.reservas.reservaFinalizada;
import Excepciones.reservas.reservaCancelada;
import Excepciones.reservas.OperacionInvalidaException;
import MetodoDePago.MetodoDePago;
import MetodosDeTraslado.Direccion;
import MetodosDeTraslado.MetodoDeTraslado;
import NotificacionesDeReserva.NotificadorDeReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class EstadosDeReservaTest {

    Reserva reserva;
    EstadoDeReserva estado;
    MetodoDeTraslado metodoTraslado;
    Direccion direccion;
    MetodoDePago metodoPago;
    Catalogo catalogo;
    Cliente cliente;
    NotificadorDeReserva notificador;
    ServicioTuristico servicio;

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
    void setup() {
        estado = mock(EstadoDeReserva.class);
        metodoTraslado = mock(MetodoDeTraslado.class);
        direccion = mock(Direccion.class);
        metodoPago = mock(MetodoDePago.class);
        catalogo = mock(Catalogo.class);
        cliente = mock(Cliente.class);
        notificador = mock(NotificadorDeReserva.class);
        servicio = mock(ServicioTuristico.class);
        reserva = new Reserva(estado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador);

        estadoPendiente = new Pendiente();
        estadoConfirmado = new Confirmada();
        estadoCheckIn = new CheckIn();
        estadoEnCurso = new EnCurso();
        estadoFinalizado = new Finalizada();
        estadoCancelado = new Cancelada();

        reservaPendiente = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador);
        reservaConfirmada = new Reserva(estadoConfirmado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador);
        reservaCheckIn = new Reserva(estadoCheckIn, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador);
        reservaEnCurso = new Reserva(estadoEnCurso, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador);
        reservaFinalizada = new Reserva(estadoFinalizado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador);
        reservaCancelada = new Reserva(estadoCancelado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador);
    }


    //sintacticamente como testeo que se delega en el estado?

    @Test
    void test001_UnaReservaPendientePuedePasarAConfirmada() {
        reservaPendiente.confirmarReserva();
        assertInstanceOf(Confirmada.class, reservaPendiente.getEstadoDeReserva());
    }

    @Test
    void test002_UnaReservaConfirmadaPuedePasarACheckIn() {
        reservaConfirmada.checkInReserva();
        assertInstanceOf(CheckIn.class, reservaConfirmada.getEstadoDeReserva());
    }

    @Test
    void test003_UnaReservaEnCheckInPuedePasarAEnCurso() {
        reservaCheckIn.enCursoReserva();
        assertInstanceOf(EnCurso.class, reservaCheckIn.getEstadoDeReserva());
    }

    @Test
    void test004_UnaReservaEnCursoPuedePasarAFinalizada() {
        reservaEnCurso.finalizarReserva();
        assertInstanceOf(Finalizada.class, reservaEnCurso.getEstadoDeReserva());
    }

    @Test
    void test005_UnaReservaPendientePuedeSerCancelada() {
        reservaPendiente.cancelarReserva();
        assertInstanceOf(Cancelada.class, reservaPendiente.getEstadoDeReserva());
    }

    @Test
    void test006_UnaReservaConfirmadaPuedeSerCancelada() {
        reservaConfirmada.cancelarReserva();
        assertInstanceOf(Cancelada.class, reservaConfirmada.getEstadoDeReserva());
    }

    @Test
    void test007_UnaReservaEnCheckInPuedeSerCancelada() {
        reservaCheckIn.cancelarReserva();
        assertInstanceOf(Cancelada.class, reservaCheckIn.getEstadoDeReserva());
    }

    @Test
    void test008_UnaReservaEnCursoPuedeSerCancelada() {
        reservaEnCurso.cancelarReserva();
        assertInstanceOf(Cancelada.class, reservaEnCurso.getEstadoDeReserva());
    }

    @Test
    void test009_UnaReservaFinalizadaNoPuedeSerCancelada() {
        try {
            reservaFinalizada.cancelarReserva();
        } catch (reservaFinalizada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya finalizo");
        }
    }

    @Test
    void test010_UnaReservaFinalizadaNoPuedeSerConfirmada() {
        try {
            reservaFinalizada.confirmarReserva();
        } catch (reservaFinalizada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya finalizo");
        }
    }

    @Test
    void test011_UnaReservaFinalizadaNoPuedePasarACheckIn() {
        try {
            reservaFinalizada.checkInReserva();
        } catch (reservaFinalizada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya finalizo");
        }
    }

    @Test
    void test012_UnaReservaFinalizadaNoPuedeEstarEnCurso() {
        try {
            reservaFinalizada.enCursoReserva();
        } catch (reservaFinalizada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya finalizo");
        }
    }

    @Test
    void test013_UnaReservaFinalizadaNoPuedeSerFinalizada() {
        try {
            reservaFinalizada.finalizarReserva();
        } catch (reservaFinalizada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya finalizo");
        }
    }

    @Test
    void test014_UnaReservaCanceladaNoPuedeSerConfirmada() {
        try {
            reservaCancelada.confirmarReserva();
        } catch (reservaCancelada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva fue cancelada");
        }
    }

    @Test
    void test015_UnaReservaCanceladaNoPuedeSerCancelada() {
        try {
            reservaCancelada.cancelarReserva();
        } catch (reservaCancelada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva fue cancelada");
        }
    }

    @Test
    void test016_UnaReservaCanceladaNoPuedePasarACheckIn() {
        try {
            reservaCancelada.checkInReserva();
        } catch (reservaCancelada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva fue cancelada");
        }
    }

    @Test
    void test017_UnaReservaCanceladaNoPuedeEstarEnCurso() {
        try {
            reservaCancelada.enCursoReserva();
        } catch (reservaCancelada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva fue cancelada");
        }
    }

    @Test
    void test018_UnaReservaCanceladaNoPuedeSerFinalizada() {
        try {
            reservaCancelada.finalizarReserva();
        } catch (reservaCancelada e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva fue cancelada");
        }
    }

    @Test
    void test019_UnaReservaPendienteNoPuedePasarACheckIn() {
        try {
            reservaPendiente.checkInReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible hacer checkin sobre una reserva pendiente");
        }
    }
    @Test
    void test020_UnaReservaPendienteNoPuedeEstarEnCUrso(){
        try {
            reservaPendiente.enCursoReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible hacer en curso sobre una reserva pendiente");
        }
    }
    @Test
    void test021_UnaReservaPendienteNoPuedeSerFinalizada(){
        try {
            reservaPendiente.finalizarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible finalizar una reserva pendiente");
        }
    }
    @Test
    void test022_UnaReservaConfirmadaNoPuedeSerConfirmada(){
        try {
            reservaConfirmada.confirmarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya esta confirmada");
        }
    }
    @Test
    void test023_UnaReservaConfirmadaNoPuedeEstarEnCurso(){
        try {
            reservaConfirmada.enCursoReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible poner en curso una reserva confirmada");
        }
    }
    @Test
    void test024_UnaReservaConfirmadaNoPuedeSerFinalizada(){
        try {
            reservaConfirmada.finalizarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible finalizar una reserva confirmada");
        }
    }
    @Test
    void test025_UnaReservaEnCheckInNoPuedeSerConfirmada(){
        try {
            reservaCheckIn.confirmarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible confirmar una reserva en checkin");
        }
    }
    @Test
    void test026_UnaReservaEnCheckInNoPuedePasarACheckIn(){
        try {
            reservaCheckIn.checkInReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya esta en checkIn");
        }
    }
    @Test
    void test027_UnaReservaEnCheckInNoPuedeSerFinalizada(){
        try {
            reservaCheckIn.finalizarReserva();
        } catch (OperacionInvalidaException e){
            assertEquals(e.getMessage(), "Operacion invalida, no es posible finalizar una reserva en checkIn");
        }
    }
    @Test
    void test028_UnaReservaEnCursoNoPuedeSerConfirmada(){
        try {
            reservaEnCurso.confirmarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible confirmar una reserva en curso");
        }
    }
    @Test
    void test029_UnaReservaEnCursoNoPuedePasarACheckIn(){
        try {
            reservaEnCurso.checkInReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible hacer checkIn a una reserva en curso");
        }
    }
    @Test
    void test030_UnaReservaEnCursoNoPuedePasarAEnCurso(){
        try {
            reservaEnCurso.enCursoReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, la operacion ya esta en curso");
        }
    }
    @Test
    void test031_UnaReservaCanceladaNoPuedeAgregarUnServicio(){
        try {
            reservaCancelada.agregarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden agregar servicios en este estado");
        }
    }
    @Test
    void test032_UnaReservaCanceladaNoPuedeQuitarUnServicio(){
        try {
            reservaCancelada.quitarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden quitar servicios en este estado");
        }
    }
    @Test
    void test033_UnaReservaFinalizadaNoPuedeAgregarUnServicio(){
        try {
            reservaFinalizada.agregarServicio(servicio,1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden agregar servicios en este estado");
        }
    }
    @Test
    void test034_UnaReservaFinalizadaNoPuedeQuitarUnServicio(){
        try {
            reservaFinalizada.quitarServicio(servicio,1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden quitar servicios en este estado");
        }
    }
    @Test
    void test035_UnaReservaConfirmadaNoPuedeAgregarUnServicio(){
        try {
            reservaConfirmada.agregarServicio(servicio,1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden agregar servicios en este estado");
        }
    }
    @Test
    void test036_UnaReservaConfirmadaNoPuedeQuitarUnServicio(){
        try {
            reservaConfirmada.quitarServicio(servicio,1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden quitar servicios en este estado");
        }
    }
    @Test
    void test037_UnaReservaEnCheckInNoPuedeAgregarUnServicio(){
        try {
            reservaCheckIn.agregarServicio(servicio,1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden agregar servicios en este estado");
        }
    }
    @Test
    void test038_UnaReservaEnCheckInNoPuedeQuitarUnServicio(){
        try {
            reservaCheckIn.quitarServicio(servicio,1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden quitar servicios en este estado");
        }
    }
    @Test
    void test039_UnaReservaEnCursoNoPuedeAgregarUnServicio(){
        try {
            reservaEnCurso.agregarServicio(servicio,1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden agregar servicios en este estado");
        }
    }
    @Test
    void test040_UnaReservaEnCursoNoPuedeQuitarUnServicio(){
        try {
            reservaEnCurso.quitarServicio(servicio,1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(),"no se pueden quitar servicios en este estado");
        }
    }
}
