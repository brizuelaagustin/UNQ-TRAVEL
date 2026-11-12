import CatalogoTuristico.Catalogo;
import Clases.Cliente;
import Clases.Reserva;
import EstadosDeReserva.*;
import MetodoDePago.MetodoDePago;
import MetodosDeTraslado.Direccion;
import MetodosDeTraslado.MetodoDeTraslado;
import MetodosDeTraslado.PuntoDeEncuentro;
import MetodosDeTraslado.TrasladoEstandar;
import NotificacionesDeReserva.*;
import org.junit.jupiter.api.BeforeEach;
import MetodoDePago.TransferenciaBancaria;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificacionesDeReservaTest {
    private Fidelizacion fidelizacion;
    private GeneradorDeFactura generadorDeFactura;
    private NotificadorDeEmail notificadorDeEmail;
    private EstadoDeReserva estadoDeReserva;
    private NotificadorDeReserva notificadorDeReserva;

    private MailSender mockMailSender;
    private Cliente cliente1;
    private Reserva reserva;
    private MetodoDeTraslado metodoTraslado;
    private Direccion direccion;
    private MetodoDePago transferenciaBancaria;
    private Catalogo mockCatalogo;
    private LocalDate mockFechaInicio;
    private LocalDate mockFechaFin;

    @BeforeEach
    void setUo(){
        mockMailSender = mock(MailSender.class);
        fidelizacion = new Fidelizacion(mockMailSender);
        generadorDeFactura = new GeneradorDeFactura();
        notificadorDeEmail = new NotificadorDeEmail(mockMailSender);
        estadoDeReserva = new Pendiente();
        notificadorDeReserva =new NotificadorDeReserva();

        cliente1 = mock(Cliente.class);
        when(cliente1.getEmail()).thenReturn("juan@gmail.com");

        metodoTraslado = new PuntoDeEncuentro();
        direccion = mock(Direccion.class);
        transferenciaBancaria = mock(TransferenciaBancaria.class);
        mockCatalogo = mock(Catalogo.class);
        mockFechaInicio = mock(LocalDate.class);
        mockFechaFin = mock(LocalDate.class);

        reserva = new Reserva(estadoDeReserva,metodoTraslado,1, direccion,transferenciaBancaria,mockCatalogo,cliente1
                                ,notificadorDeReserva, mockFechaInicio, mockFechaFin, true);

        reserva.getNotificadorDeReserva().registrarObservador(notificadorDeEmail);
        reserva.getNotificadorDeReserva().registrarObservador(generadorDeFactura);
        reserva.getNotificadorDeReserva().registrarObservador(fidelizacion);
    }

    //TEST DE NOTIFICADOR MAIL
    @Test
    void test001_notificadorDeMailPasarAEstadoConfirmado(){
        EstadoDeReserva estadoConfirmado = new Confirmada();
        reserva.actualizarReserva(estadoConfirmado);
        verify(mockMailSender, times(1)).enviarMail(
                eq("juan@gmail.com"),
                contains("Actualización de tu reserva"),
                contains("Tu reserva pasó a estado Confirmada"),
                isNull()
        );
    }

    @Test
    void test002_notificadorDeMailPasarAEstadoEnCurso(){
        EstadoDeReserva estadoEnCurso = new EnCurso();
        reserva.actualizarReserva(estadoEnCurso);
        verify(mockMailSender, times(1)).enviarMail(
                eq("juan@gmail.com"),
                contains("Actualización de tu reserva"),
                contains("Tu reserva pasó a estado EnCurso"),
                isNull()
        );
    }
    @Test
    void test003_notificadorDeMailPasarAEstadoFinalizada(){
        EstadoDeReserva estadoFinalizada = new Finalizada();
        reserva.actualizarReserva(estadoFinalizada);
        verify(mockMailSender, times(1)).enviarMail(
                eq("juan@gmail.com"),
                contains("Actualización de tu reserva"),
                contains("Tu reserva pasó a estado Finalizada"),
                isNull()
        );
    }

    @Test
    void test04_notificadorEmailNoActuaEnEstadoPendiente() {
        verify(mockMailSender, never()).enviarMail(
                anyString(),
                eq("Actualización de tu reserva"),
                anyString(),
                any()
        );
    }

    @Test
    void test05_notificadorEmailNoActuaEnEstadoCheckIn() {
        EstadoDeReserva estadochekIn = new CheckIn();
        reserva.actualizarReserva(estadochekIn);
        verify(mockMailSender, never()).enviarMail(
                anyString(),
                eq("Actualización de tu reserva"),
                anyString(),
                any()
        );
    }

    // TEST GENERADOR DE FACTURA

    @Test
    void test006_generadorDeFacturaCreaComprobanteCuandoAlcanzaEstadoFinalizada(){
        EstadoDeReserva estadoFinalizada = new Finalizada();
        reserva.actualizarReserva(estadoFinalizada);
    }

    @Test
    void test007_generadorDeFacturaNoCreaComprobanteCuandoElEstadoNoEsFinalizada(){
        EstadoDeReserva estadoConfirmada = new Confirmada();
        reserva.actualizarReserva(estadoConfirmada);
    }

    //TEST FIDELIZACIÓN
    @Test
    void test008_fidelizacionEnviaCuponDeDescuentoAlCancelarUnaReserva(){
        EstadoDeReserva estadoCancelada = new Cancelada();
        reserva.actualizarReserva(estadoCancelada);

        verify(mockMailSender, times(1)).enviarMail(
                eq("juan@gmail.com"),
                contains("¡Queremos que vuelvas!"),
                contains("5% de descuento"),
                eq("CUPON9333")
        );
    }

    @Test
    void test009_fidelizacionNoEnviaCuponDeDescuentoSiNoSeCancelaUnaReserva(){
        EstadoDeReserva estadoCheckIn = new CheckIn();
        reserva.actualizarReserva(estadoCheckIn);

        verify(mockMailSender, never()).enviarMail(
                anyString(),
                anyString(),
                anyString(),
                eq("CUPON9333")
        );
    }
    @Test
    void test010_sePuedenRegistrarObservadoresEnUnaReserva(){
        assertEquals(3,notificadorDeReserva.getObservadores().size());
    }
    @Test
    void test011_sePuedenDarDeBajaObservadoresEnUnaReserva(){
        reserva.getNotificadorDeReserva().darDeBajaAObservador(notificadorDeEmail);
        reserva.getNotificadorDeReserva().darDeBajaAObservador(generadorDeFactura);
        reserva.getNotificadorDeReserva().darDeBajaAObservador(fidelizacion);
        assertEquals(0,notificadorDeReserva.getObservadores().size());
    }
    @Test
    void test12_unObservadorRemovidoYaNoRecibeNotificaciones() {
        reserva.getNotificadorDeReserva().darDeBajaAObservador(notificadorDeEmail);

        EstadoDeReserva estadoConfirmado = new Confirmada();
        reserva.actualizarReserva(estadoConfirmado);

        verify(mockMailSender, never()).enviarMail(
                anyString(),
                contains("Actualización de tu reserva"),
                anyString(),
                any()
        );
    }

}
