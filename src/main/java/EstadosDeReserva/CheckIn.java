package EstadosDeReserva;

import main.java.Clases.Reserva;
import Excepciones.reservas.reservaCheckInACheckIn;
import Excepciones.reservas.reservaCheckInAConfirmada;
import Excepciones.reservas.reservaCheckInAFinalizada;

public class CheckIn implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new reservaCheckInAConfirmada("Operacion invalida, no es posible confirmar una reserva en checkin");
    }
    public void cancelarReserva(Reserva reserva){
        reserva.actualizarReserva(new Cancelada());
    }
    public void checkInReserva(Reserva reserva){
        throw new reservaCheckInACheckIn("Operacion invalida, la reserva ya esta en checkIn");
    }
    public void enCursoReserva(Reserva reserva){
        reserva.actualizarReserva(new enCurso());
    }
    public void finalizarReserva(Reserva reserva){
        throw new reservaCheckInAFinalizada("Operacion invalida, no es posible finalizar una reserva en checkIn");
    }
}
