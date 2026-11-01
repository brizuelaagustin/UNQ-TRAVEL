package EstadosDeReserva;

import main.java.Clases.Reserva;
import Excepciones.reservas.reservaConfirmadaAConfirmada;
import Excepciones.reservas.reservaConfirmadaAFinalizada;
import Excepciones.reservas.reservaConfirmadaAenCurso;

public class Confirmada implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new reservaConfirmadaAConfirmada("Operacion invalida, la reserva ya esta confirmada");
    }
    public void cancelarReserva(Reserva reserva){
        reserva.actualizarReserva(new Cancelada());
    }
    public void checkInReserva(Reserva reserva){
        reserva.actualizarReserva(new CheckIn());
    }
    public void enCursoReserva(Reserva reserva){
        throw new reservaConfirmadaAenCurso("Operacion invalida, no es posible poner en curso una reserva confirmada");
    }
    public void finalizarReserva(Reserva reserva){
        throw new reservaConfirmadaAFinalizada("Operacion invalida, no es posible finalizar una reserva confirmada");
    }
}
