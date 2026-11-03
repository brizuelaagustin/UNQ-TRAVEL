package EstadosDeReserva;

import CatalogoTuristico.ServicioTuristico;
import Clases.Reserva;
import Excepciones.reservas.reservaPendienteACheckIn;
import Excepciones.reservas.reservaPendienteAFinalizada;
import Excepciones.reservas.reservaPendienteAenCurso;

public class Pendiente implements EstadoDeReserva {

    public void confirmarReserva(Reserva reserva) {
        reserva.actualizarReserva(new Confirmada());
    }

    public void cancelarReserva(Reserva reserva) {
        reserva.actualizarReserva(new Cancelada());
    }

    public void checkInReserva(Reserva reserva) {
        throw new reservaPendienteACheckIn("Operacion invalida, no es posible hacer checkin sobre una reserva pendiente");
    }

    public void enCursoReserva(Reserva reserva) {
        throw new reservaPendienteAenCurso("Operacion invalida, no es posible hacer en curso sobre una reserva pendiente");
    }

    public void finalizarReserva(Reserva reserva) {
        throw new reservaPendienteAFinalizada("Operacion invalida, no es posible finalizar una reserva pendiente");
    }

    @Override
    public void agregarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad) {
        reserva.incluirServicio(servicio,cantidad);
    }

    @Override
    public void quitarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad) {
        reserva.removerServicio(servicio,cantidad);
    }

}
