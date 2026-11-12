package NotificacionesDeReserva;

import Clases.Reserva;
import EstadosDeReserva.EstadoDeReserva;
import lombok.Getter;

@Getter
public class Fidelizacion implements Subsistema{
    private MailSender mailSender;

    public Fidelizacion(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void actualizarEstado(Reserva reserva, EstadoDeReserva estadoNuevo, EstadoDeReserva estadoAnterior) {
       estadoNuevo.notificarFidelizacion(reserva,this);
    }
}
