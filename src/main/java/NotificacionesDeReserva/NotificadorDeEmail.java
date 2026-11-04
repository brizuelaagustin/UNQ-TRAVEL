package NotificacionesDeReserva;

import Clases.Reserva;
import EstadosDeReserva.EstadoDeReserva;
import lombok.Getter;

@Getter
public class NotificadorDeEmail implements  Subsistema{
    private final MailSender mailSender;

    public NotificadorDeEmail(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void actualizarEstado(Reserva reserva, EstadoDeReserva estadoNuevo, EstadoDeReserva estadoAnterior) {
        estadoAnterior.notificarMail(reserva,this);
    }
}
