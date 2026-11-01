package EstadosDeReserva;

import main.java.Clases.Reserva;
import Excepciones.reservas.reservaCancelada;

public class Cancelada implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
    public void cancelarReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
    public void checkInReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
    public void enCursoReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
    public void finalizarReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
}
