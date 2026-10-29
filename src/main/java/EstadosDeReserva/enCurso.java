package EstadosDeReserva;

import Clases.Catalogo;
import Clases.Reserva;
import Excepciones.reservas.reservaEnCursoACheckIn;
import Excepciones.reservas.reservaEnCursoAConfirmada;
import Excepciones.reservas.reservaEnCursoAEnCurso;

public class enCurso implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new reservaEnCursoAConfirmada("Operacion invalida, no es posible confirmar una reserva en curso");
    }
    public void cancelarReserva(Reserva reserva){
        Cancelada nuevoEstado = new Cancelada();
        reserva.actualizarReserva(nuevoEstado);
    }
    public void checkInReserva(Reserva reserva){
        throw new reservaEnCursoACheckIn("Operacion invalida, no es posible hacer checkIn a una reserva en curso");
    }
    public void enCursoReserva(Reserva reserva){
        throw new reservaEnCursoAEnCurso("Operacion invalida, la operacion ya esta en curso");
    }
    public void finalizarReserva(Reserva reserva){
        Finalizada nuevoEstado = new Finalizada();
        reserva.actualizarReserva(nuevoEstado);
    }
    public void agregarItem(Catalogo catalogo){}
    public void quitarItem(Catalogo catalogo){}
}
