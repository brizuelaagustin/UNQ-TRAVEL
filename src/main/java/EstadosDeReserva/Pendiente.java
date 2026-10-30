package EstadosDeReserva;

import Clases.Catalogo;
import Clases.Reserva;
import Excepciones.reservas.reservaPendienteACheckIn;
import Excepciones.reservas.reservaPendienteAFinalizada;
import Excepciones.reservas.reservaPendienteAenCurso;

public class Pendiente implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        Confirmada nuevoEstado = new Confirmada();
        reserva.actualizarReserva(nuevoEstado);
    }
    public void cancelarReserva(Reserva reserva){
        Cancelada nuevoEstado = new Cancelada();
        reserva.actualizarReserva(nuevoEstado);
    }
    public void checkInReserva(Reserva reserva){
        throw new reservaPendienteACheckIn("Operacion invalida, no es posible hacer checkin sobre una reserva pendiente");
    }
    public void enCursoReserva(Reserva reserva){
        throw new reservaPendienteAenCurso("Operacion invalida, no es posible hacer en curso sobre una reserva pendiente");
    }
    public void finalizarReserva(Reserva reserva){
        throw new reservaPendienteAFinalizada("Operacion invalida, no es posible finalizar una reserva pendiente");
    }
    public void agregarItem(Catalogo catalogo){}
    public void quitarItem(Catalogo catalogo){}
}
