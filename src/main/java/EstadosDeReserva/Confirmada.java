package EstadosDeReserva;

import Clases.Catalogo;
import Clases.Reserva;
import Excepciones.reservas.reservaConfirmadaAConfirmada;
import Excepciones.reservas.reservaConfirmadaAFinalizada;
import Excepciones.reservas.reservaConfirmadaAenCurso;

public class Confirmada implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new reservaConfirmadaAConfirmada("Operacion invalida, la reserva ya esta confirmada");
    }
    public void cancelarReserva(Reserva reserva){
        Cancelada nuevoEstado = new Cancelada();
        reserva.actualizarReserva(nuevoEstado);
    }
    public void checkInReserva(Reserva reserva){
        CheckIn nuevoEstado = new CheckIn();
        reserva.actualizarReserva(nuevoEstado);
    }
    public void enCursoReserva(Reserva reserva){
        throw new reservaConfirmadaAenCurso("Operacion invalida, no es posible poner en curso una reserva confirmada");
    }
    public void finalizarReserva(Reserva reserva){
        throw new reservaConfirmadaAFinalizada("Operacion invalida, no es posible finalizar una reserva confirmada");
    }
    public void agregarItem(Catalogo catalogo){}
    public void quitarItem(Catalogo catalogo){}
}
