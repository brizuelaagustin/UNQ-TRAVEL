import CatalogoTuristico.Catalogo;
import Clases.Cliente;
import Clases.Reserva;
import EstadosDeReserva.EstadoDeReserva;
import EstadosDeReserva.Pendiente;
import MetodoDePago.MetodoDePago;
import MetodosDeTraslado.Direccion;
import MetodosDeTraslado.MetodoDeTraslado;
import MetodosDeTraslado.TrasladoEstandar;
import NotificacionesDeReserva.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificacionesDeReservaTest {
    private Fidelizacion fidelizacion;
    private GeneradorDeFactura generadorDeFactura;
    private NotificadorDeEmail notificadorDeEmail;
    private EstadoDeReserva estadoDeReserva;
    private NotificadorDeReserva notificadorDeReserva;

    private MailSender mockMailSender;
    private Cliente cliente1;
    private Reserva reserva;
    private MetodoDeTraslado trasladoEstandar;
    private Direccion direccion;
    private  MetodoDePago transferenciaBancaria;
    private  Catalogo mockCatalogo;

    @BeforeEach
    void setUo(){
        fidelizacion = new Fidelizacion(mockMailSender);
        generadorDeFactura = new GeneradorDeFactura();
        notificadorDeEmail = new NotificadorDeEmail(mockMailSender);
        estadoDeReserva = new Pendiente();
        notificadorDeReserva =new NotificadorDeReserva();

        mockMailSender = mock(MailSender.class);
        cliente1 = mock(Cliente.class);
        when(cliente1.getEmail()).thenReturn("juan@gmail.com");

        trasladoEstandar = mock(TrasladoEstandar.class);
        direccion = mock(Direccion.class);
        transferenciaBancaria = mock(MetodoDePago.class);
        mockCatalogo = mock(Catalogo.class);

        reserva = new Reserva(estadoDeReserva,trasladoEstandar,1, direccion,transferenciaBancaria,mockCatalogo,cliente1
                                ,notificadorDeReserva);

        reserva.getNotificadorDeReserva().registrarObservador(notificadorDeEmail);
        reserva.getNotificadorDeReserva().registrarObservador(generadorDeFactura);
        reserva.getNotificadorDeReserva().registrarObservador(fidelizacion);
    }


}
