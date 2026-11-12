package EstadosDeReserva;

import CatalogoTuristico.ServicioTuristico;
import Excepciones.reservas.OperacionInvalidaException;
import Clases.Reserva;
import NotificacionesDeReserva.Fidelizacion;
import NotificacionesDeReserva.NotificadorDeEmail;

public class EnCurso implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, no es posible confirmar una reserva en curso");
    }
    public void cancelarReserva(Reserva reserva){
        reserva.actualizarReserva(new Cancelada());
    }
    public void checkInReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, no es posible hacer checkIn a una reserva en curso");
    }
    public void enCursoReserva(Reserva reserva){
        throw new OperacionInvalidaException("Operacion invalida, la operacion ya esta en curso");
    }
    public void finalizarReserva(Reserva reserva){
        reserva.actualizarReserva(new Finalizada());
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
        notificadorDeEmail.getMailSender().enviarMail(reserva.obtenerMailCliente(),"Actualización de tu reserva",
                "Tu reserva pasó a estado EnCurso", null);
    }

    @Override
    public void notificarFidelizacion(Reserva reserva, Fidelizacion fidelizacion) {
    }

    @Override
    public void generarFactura(Reserva reserva) {
    }
}
