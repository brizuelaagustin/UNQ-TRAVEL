package EstadosDeReserva;

import Clases.Catalogo;
import Clases.Reserva;
import Excepciones.reservas.reservaFinalizada;

public class Finalizada implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new reservaFinalizada("Operacion invalida, la reserva ya finalizo");
    }
    public void cancelarReserva(Reserva reserva){
        throw new reservaFinalizada("Operacion invalida, la reserva ya finalizo");
    }
    public void checkInReserva(Reserva reserva){
        throw new reservaFinalizada("Operacion invalida, la reserva ya finalizo");
    }
    public void enCursoReserva(Reserva reserva){
        throw new reservaFinalizada("Operacion invalida, la reserva ya finalizo");
    }
    public void finalizarReserva(Reserva reserva){
        throw new reservaFinalizada("Operacion invalida, la reserva ya finalizo");
    }
    public void agregarItem(Catalogo catalogo){}
    public void quitarItem(Catalogo catalogo){}
}
