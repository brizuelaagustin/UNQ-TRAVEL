package EstadosDeReserva;

import CatalogoTuristico.ServicioTuristico;
import Clases.Reserva;
import Excepciones.reservas.OperacionInvalidaException;
import Excepciones.reservas.reservaFinalizada;
import NotificacionesDeReserva.NotificadorDeEmail;

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

    @Override
    public void agregarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad) {
        throw new OperacionInvalidaException("no se pueden agregar servicios en este estado");
    }

    @Override
    public void quitarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad) {
        throw new OperacionInvalidaException("no se pueden quitar servicios en este estado");
    }

    @Override
    public void notificarMail(Reserva reserva, NotificadorDeEmail notificadorDeEmail) {
        notificadorDeEmail.getMailSender().enviarMail(reserva.getCliente().getEmail(),"Actualizacion de reserva",
                                                        "reserva pasa a " + this, null);
    }
}
