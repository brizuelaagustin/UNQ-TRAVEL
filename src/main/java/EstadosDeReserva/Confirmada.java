package EstadosDeReserva;

import CatalogoTuristico.ServicioTuristico;
import Excepciones.reservas.OperacionInvalidaException;
import Clases.Reserva;
import NotificacionesDeReserva.NotificadorDeEmail;

public class Confirmada implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, la reserva ya esta confirmada");
    }
    public void cancelarReserva(Reserva reserva){
        reserva.actualizarReserva(new Cancelada());
    }
    public void checkInReserva(Reserva reserva){
        reserva.actualizarReserva(new CheckIn());
    }
    public void enCursoReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, no es posible poner en curso una reserva confirmada");
    }
    public void finalizarReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, no es posible finalizar una reserva confirmada");
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
        notificadorDeEmail.getMailSender().enviarMail(reserva.getCliente().getEmail(),"Actualizacion de reserva",
                "reserva pasa a " + this, null);
    }
}
