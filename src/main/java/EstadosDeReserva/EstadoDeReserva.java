package EstadosDeReserva;
import Clases.Reserva;

import CatalogoTuristico.ServicioTuristico;
import NotificacionesDeReserva.Fidelizacion;
import NotificacionesDeReserva.GeneradorDeFactura;
import NotificacionesDeReserva.NotificadorDeEmail;

public interface EstadoDeReserva {
    void confirmarReserva(Reserva reserva);
    void cancelarReserva(Reserva reserva);
    void checkInReserva(Reserva reserva);
    void enCursoReserva(Reserva reserva);
    void finalizarReserva(Reserva reserva);
    void agregarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad);
    void quitarServicio(Reserva reserva, ServicioTuristico servicio, Integer cantidad);
    void notificarMail(Reserva reserva, NotificadorDeEmail notificadorDeEmail);
    void notificarFidelizacion(Reserva reserva, Fidelizacion fidelizacion);
    void generarFactura(Reserva reserva);
}