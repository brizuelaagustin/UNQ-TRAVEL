import CatalogoTuristico.*;
import Clases.Cliente;
import Clases.Reserva;
import EstadosDeReserva.*;
import Excepciones.reservas.*;
import MetodoDePago.MetodoDePago;
import MetodosDeTraslado.Direccion;
import MetodosDeTraslado.MetodoDeTraslado;
import MetodosDeTraslado.PuntoDeEncuentro;
import NotificacionesDeReserva.NotificadorDeReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static CatalogoTuristico.Categoria.ALOJAMIENTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    LocalDate fechaInicio;
    LocalDate fechaFin;
    LocalDate fechaInicio1;
    LocalDate fechaFin1;
    ServicioTuristico servicio1;
    ServicioTuristico servicio2;
    CronogramaServicio cronogramaServicio;
    PaqueteTuristico paqueteTuristico;

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
        metodoTraslado = new PuntoDeEncuentro();
        direccion = mock(Direccion.class);
        metodoPago = mock(MetodoDePago.class);
        catalogo = mock(Catalogo.class);
        cliente = mock(Cliente.class);
        notificador = mock(NotificadorDeReserva.class);
        servicio = mock(ServicioTuristico.class);
        reserva = new Reserva(estado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador, fechaInicio, fechaFin, true);
        fechaInicio = mock(LocalDate.class);
        fechaFin = mock(LocalDate.class);

        estadoPendiente = new Pendiente();
        estadoConfirmado = new Confirmada();
        estadoCheckIn = new CheckIn();
        estadoEnCurso = new EnCurso();
        estadoFinalizado = new Finalizada();
        estadoCancelado = new Cancelada();

        reservaPendiente = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador, fechaInicio, fechaFin, true);
        reservaConfirmada = new Reserva(estadoConfirmado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador, fechaInicio, fechaFin, true);
        reservaCheckIn = new Reserva(estadoCheckIn, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador, fechaInicio, fechaFin, true);
        reservaEnCurso = new Reserva(estadoEnCurso, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador, fechaInicio, fechaFin, true);
        reservaFinalizada = new Reserva(estadoFinalizado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador, fechaInicio, fechaFin, true);
        reservaCancelada = new Reserva(estadoCancelado, metodoTraslado, 10, direccion, metodoPago, catalogo, cliente, notificador, fechaInicio, fechaFin, true);

        fechaInicio1 = LocalDate.of(2020, 10, 1);
        fechaFin1 = LocalDate.of(2020, 10, 11);
        cronogramaServicio = new CronogramaServicio();
        servicio1 = new ServicioIndividual(123, "Noche De Hotel Central", "habitacion para una persona",
                "proveedor", 0.1f, 2000f, ALOJAMIENTO, cronogramaServicio);
        servicio2 = new ServicioIndividual(145, "Noche De Plaza Central", "muchos arboles",
                "proveedor2", 0.4f, 1000f, ALOJAMIENTO, cronogramaServicio);
        paqueteTuristico = new PaqueteTuristico("epico paquete", "descripcion", Map.of(servicio1, 2, servicio2, 3), 0.2f, cronogramaServicio);
    }


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
    void test020_UnaReservaPendienteNoPuedeEstarEnCUrso() {
        try {
            reservaPendiente.enCursoReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible hacer en curso sobre una reserva pendiente");
        }
    }

    @Test
    void test021_UnaReservaPendienteNoPuedeSerFinalizada() {
        try {
            reservaPendiente.finalizarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible finalizar una reserva pendiente");
        }
    }

    @Test
    void test022_UnaReservaConfirmadaNoPuedeSerConfirmada() {
        try {
            reservaConfirmada.confirmarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya esta confirmada");
        }
    }

    @Test
    void test023_UnaReservaConfirmadaNoPuedeEstarEnCurso() {
        try {
            reservaConfirmada.enCursoReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible poner en curso una reserva confirmada");
        }
    }

    @Test
    void test024_UnaReservaConfirmadaNoPuedeSerFinalizada() {
        try {
            reservaConfirmada.finalizarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible finalizar una reserva confirmada");
        }
    }

    @Test
    void test025_UnaReservaEnCheckInNoPuedeSerConfirmada() {
        try {
            reservaCheckIn.confirmarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible confirmar una reserva en checkin");
        }
    }

    @Test
    void test026_UnaReservaEnCheckInNoPuedePasarACheckIn() {
        try {
            reservaCheckIn.checkInReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, la reserva ya esta en checkIn");
        }
    }

    @Test
    void test027_UnaReservaEnCheckInNoPuedeSerFinalizada() {
        try {
            reservaCheckIn.finalizarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible finalizar una reserva en checkIn");
        }
    }

    @Test
    void test028_UnaReservaEnCursoNoPuedeSerConfirmada() {
        try {
            reservaEnCurso.confirmarReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible confirmar una reserva en curso");
        }
    }

    @Test
    void test029_UnaReservaEnCursoNoPuedePasarACheckIn() {
        try {
            reservaEnCurso.checkInReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, no es posible hacer checkIn a una reserva en curso");
        }
    }

    @Test
    void test030_UnaReservaEnCursoNoPuedePasarAEnCurso() {
        try {
            reservaEnCurso.enCursoReserva();
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "Operacion invalida, la operacion ya esta en curso");
        }
    }

    @Test
    void test031_UnaReservaCanceladaNoPuedeAgregarUnServicio() {
        try {
            reservaCancelada.agregarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden agregar servicios en este estado");
        }
    }

    @Test
    void test032_UnaReservaCanceladaNoPuedeQuitarUnServicio() {
        try {
            reservaCancelada.quitarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden quitar servicios en este estado");
        }
    }

    @Test
    void test033_UnaReservaFinalizadaNoPuedeAgregarUnServicio() {
        try {
            reservaFinalizada.agregarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden agregar servicios en este estado");
        }
    }

    @Test
    void test034_UnaReservaFinalizadaNoPuedeQuitarUnServicio() {
        try {
            reservaFinalizada.quitarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden quitar servicios en este estado");
        }
    }

    @Test
    void test035_UnaReservaConfirmadaNoPuedeAgregarUnServicio() {
        try {
            reservaConfirmada.agregarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden agregar servicios en este estado");
        }
    }

    @Test
    void test036_UnaReservaConfirmadaNoPuedeQuitarUnServicio() {
        try {
            reservaConfirmada.quitarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden quitar servicios en este estado");
        }
    }

    @Test
    void test037_UnaReservaEnCheckInNoPuedeAgregarUnServicio() {
        try {
            reservaCheckIn.agregarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden agregar servicios en este estado");
        }
    }

    @Test
    void test038_UnaReservaEnCheckInNoPuedeQuitarUnServicio() {
        try {
            reservaCheckIn.quitarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden quitar servicios en este estado");
        }
    }

    @Test
    void test039_UnaReservaEnCursoNoPuedeAgregarUnServicio() {
        try {
            reservaEnCurso.agregarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden agregar servicios en este estado");
        }
    }

    @Test
    void test040_UnaReservaEnCursoNoPuedeQuitarUnServicio() {
        try {
            reservaEnCurso.quitarServicio(servicio, 1);
        } catch (OperacionInvalidaException e) {
            assertEquals(e.getMessage(), "no se pueden quitar servicios en este estado");
        }
    }

    @Test
    void test041_UnaReservaPendienteNoPuedeAgregarUnServicioSiEsteNoExisteEnElCatalogo() {
        try {
            reservaPendiente.agregarServicio(servicio, 1);
        } catch (ServicioNoEncontradoException e) {
            assertEquals(e.getMessage(), "No se puede agregar, porque no existe en el catalogo");
        }
    }

    @Test
    void test042_UnaReservaPendienteNoPuedeAgregarUnServicioSiNoHayStockEnElCatalogo() {
        Catalogo nuevoCatalogo = new Catalogo();
        Reserva reservaPendiente2 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo, cliente, notificador, fechaInicio, fechaFin, true);
        nuevoCatalogo.registrarNuevoServicio(servicio);
        try {
            reservaPendiente2.agregarServicio(servicio, 1);
        } catch (NoHayStockException e) {
            assertEquals(e.getMessage(), "No se pueden agregar, no hay stock suficiente");
        }
    }

    @Test
    void test043_UnaReservaPuedeAgregarUnServicio() {
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente3 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(servicio1);
        assertTrue(nuevoCatalogo2.tieneStockParaReserva(servicio1, 10, fechaInicio1, fechaFin1));

    }

    @Test
    void test044_UnaReservaNoPuedeAgregarUnServicioSiNoTieneCupos() {
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente4 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(servicio1);
        assertFalse(nuevoCatalogo2.tieneStockParaReserva(servicio1, 20, fechaInicio1, fechaFin1));
    }

    @Test
    void test045_UnaReservaPuedeAgregarPaquete() {
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        servicio2.agregarCupos(10, fechaInicio1, fechaFin1);
        paqueteTuristico.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente5 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(paqueteTuristico);
        assertTrue(nuevoCatalogo2.tieneStockParaReserva(paqueteTuristico, 10, fechaInicio1, fechaFin1));
    }

    @Test
    void test046_UnaReservaNoPuedeAgregarPaqueteSiNoTieneCupos() {
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        servicio2.agregarCupos(10, fechaInicio1, fechaFin1);
        paqueteTuristico.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente6 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(paqueteTuristico);
        assertFalse(nuevoCatalogo2.tieneStockParaReserva(paqueteTuristico, 40, fechaInicio1, fechaFin1));
    }
    @Test
    void test047_UnaReservaPendientePuedeQuitarUnServicioSiTieneMasParaRemover(){
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente7 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(servicio1);
        reservaPendiente7.agregarServicio(servicio1,2);
        reservaPendiente7.quitarServicio(servicio1,1);
        assertEquals(1 ,reservaPendiente7.cantidadDeServicio(servicio1));
    }

    @Test
    void test048_UnaReservaPendientePuedeQuitarUnServicioSiTieneLaMismaCantidadQueRemueve(){
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente7 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(servicio1);
        reservaPendiente7.agregarServicio(servicio1,1);
        reservaPendiente7.quitarServicio(servicio1,1);
        assertEquals(0 ,reservaPendiente7.cantidadDeServicio(servicio1));
    }

    @Test
    void test049_UnaReservaNoPuedeRemoverUnServicioQueNoTiene(){
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente7 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(servicio1);
        try {
            reservaPendiente7.quitarServicio(servicio1,1);
        } catch (ServicioNoEncontradoException e) {
            assertEquals(e.getMessage(), "No se puede remover un servicio que no esta en la reserva");
        }
    }

    @Test
    void test050_UnaReservaNoPuedeRemoverUnaCantidadMayorDeServiciosQueLaQueTiene(){
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente7 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(servicio1);
        reservaPendiente7.agregarServicio(servicio1,1);
        try {
            reservaPendiente7.quitarServicio(servicio1,2);
        } catch (NoHayStockException e) {
            assertEquals(e.getMessage(), "No hay esa cantidad de servicios suficientes para remover");
        }
    }

    @Test
    void test051_ElPrecioDeUnaReservaDependeDeSusServicios(){
        servicio1.agregarCupos(10, fechaInicio1, fechaFin1);
        servicio2.agregarCupos(10, fechaInicio1, fechaFin1);
        paqueteTuristico.agregarCupos(10, fechaInicio1, fechaFin1);
        Catalogo nuevoCatalogo2 = new Catalogo();
        Reserva reservaPendiente8 = new Reserva(estadoPendiente, metodoTraslado, 10, direccion, metodoPago, nuevoCatalogo2, cliente, notificador, fechaInicio1, fechaFin1, true);
        nuevoCatalogo2.registrarNuevoServicio(paqueteTuristico);
        reservaPendiente8.agregarServicio(paqueteTuristico, 1);
        assertEquals(4320, reservaPendiente8.getPrecio());
    }
}
