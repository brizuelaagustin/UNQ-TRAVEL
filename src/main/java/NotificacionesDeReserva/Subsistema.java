package NotificacionesDeReserva;

import Clases.Reserva;
import EstadosDeReserva.EstadoDeReserva;

public interface Subsistema {
    void actualizarEstado(Reserva reserva, EstadoDeReserva estadNuevo,EstadoDeReserva estadoAnterior);
}
