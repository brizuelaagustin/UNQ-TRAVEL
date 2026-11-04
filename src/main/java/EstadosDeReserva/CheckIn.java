package EstadosDeReserva;

import CatalogoTuristico.ServicioTuristico;
import Excepciones.reservas.OperacionInvalidaException;
import Clases.Reserva;
import NotificacionesDeReserva.NotificadorDeEmail;

public class CheckIn implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, no es posible confirmar una reserva en checkin");
    }
    public void cancelarReserva(Reserva reserva){
        reserva.actualizarReserva(new Cancelada());
    }
    public void checkInReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, la reserva ya esta en checkIn");
    }
    public void enCursoReserva(Reserva reserva){
        reserva.actualizarReserva(new EnCurso());
    }
    public void finalizarReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, no es posible finalizar una reserva en checkIn");
    }

    @Override
    public void agregarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad) {
        throw new RuntimeException("no se pueden agregar servicios en este estado");
    }

    @Override
    public void quitarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad) {
        throw new RuntimeException("no se pueden quitar servicios en este estado");
    }

    @Override
    public void notificarMail(Reserva reserva, NotificadorDeEmail notificadorDeEmail) {
        //notificador de email no actua sobre este estado
    }
}
