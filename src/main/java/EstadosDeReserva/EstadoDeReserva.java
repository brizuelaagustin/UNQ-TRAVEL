package EstadosDeReserva;

import Clases.Catalogo;
import Clases.Reserva;

public interface EstadoDeReserva {

    public void confirmarReserva(Reserva reserva);
    public void cancelarReserva(Reserva reserva);
    public void checkInReserva(Reserva reserva);
    public void enCursoReserva(Reserva reserva);
    public void finalizarReserva(Reserva reserva);
    public void agregarItem(Catalogo catalogo);
    public void quitarItem(Catalogo catalogo);
}