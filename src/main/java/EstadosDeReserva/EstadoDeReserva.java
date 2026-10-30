package EstadosDeReserva;

import Clases.Catalogo;
import Clases.Reserva;

public interface EstadoDeReserva {

    void confirmarReserva(Reserva reserva);
    void cancelarReserva(Reserva reserva);
    void checkInReserva(Reserva reserva);
    void enCursoReserva(Reserva reserva);
    void finalizarReserva(Reserva reserva);
}