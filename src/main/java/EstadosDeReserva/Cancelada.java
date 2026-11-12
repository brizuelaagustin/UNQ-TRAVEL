package EstadosDeReserva;

import CatalogoTuristico.ServicioTuristico;
import Excepciones.reservas.OperacionInvalidaException;
import Clases.Reserva;
import Excepciones.reservas.reservaCancelada;
import NotificacionesDeReserva.Fidelizacion;
import NotificacionesDeReserva.NotificadorDeEmail;

public class Cancelada implements EstadoDeReserva {
    public void confirmarReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
    public void cancelarReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
    public void checkInReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
    public void enCursoReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
    }
    public void finalizarReserva(Reserva reserva){
        throw new reservaCancelada("Operacion invalida, la reserva fue cancelada");
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
    public void notificarMail(Reserva reserva, NotificadorDeEmail notificadorDeEmail) { //notificador de email no actua sobre este estado

    }

    @Override
    public void notificarFidelizacion(Reserva reserva, Fidelizacion fidelizacion) {
        fidelizacion.getMailSender().enviarMail(
                reserva.obtenerMailCliente(),
                "¡Queremos que vuelvas!",
                "Lamentamos la cancelación. Te regalamos un cupón del 5% de descuento en tu próxima compra.",
                "CUPON9333"
        );
    }

    @Override
    public void generarFactura(Reserva reserva) {
    }
}
