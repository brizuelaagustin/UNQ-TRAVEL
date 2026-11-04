package NotificacionesDeReserva;

import Clases.Reserva;
import EstadosDeReserva.EstadoDeReserva;

public class Fidelizacion implements Subsistema{
    private MailSender mailSender;

    public Fidelizacion(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void actualizarEstado(Reserva reserva, EstadoDeReserva estadoNuevo, EstadoDeReserva estadoAnterior) {
        String nombreEstado = estadoNuevo.getClass().getSimpleName();

        if (nombreEstado.contains("Cancelado")) {
            mailSender.enviarMail(
                    reserva.getCliente().getEmail(),
                    "¡Queremos que vuelvas!",
                    "Lamentamos la cancelación. Te regalamos un cupón del 5% de descuento en tu próxima compra.",
                    "CUPON9333"
            );
        }
    }
}
