package EstadosDeReserva;

import CatalogoTuristico.ServicioTuristico;
import Clases.Reserva;
import Excepciones.reservas.OperacionInvalidaException;
import NotificacionesDeReserva.NotificadorDeEmail;

public class Pendiente implements EstadoDeReserva {

    public void confirmarReserva(Reserva reserva) {
        reserva.actualizarReserva(new Confirmada());
        reserva.removerCupos();
    }

    public void cancelarReserva(Reserva reserva) {
        reserva.actualizarReserva(new Cancelada());
    }

    public void checkInReserva(Reserva reserva) {
        throw new OperacionInvalidaException("Operacion invalida, no es posible hacer checkin sobre una reserva pendiente");
    }

    public void enCursoReserva(Reserva reserva) {
        throw new OperacionInvalidaException("Operacion invalida, no es posible hacer en curso sobre una reserva pendiente");
    }

    public void finalizarReserva(Reserva reserva) {
        throw new OperacionInvalidaException("Operacion invalida, no es posible finalizar una reserva pendiente");
    }

    @Override
    public void agregarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad) {
        reserva.incluirServicio(servicio,cantidad);
    }

    @Override
    public void quitarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad) {
        reserva.removerServicio(servicio,cantidad);
    }

    @Override
    public void notificarMail(Reserva reserva, NotificadorDeEmail notificadorDeEmail) {
        //notificador de email no actua sobre este estado
    }

}
