package NotificacionesDeReserva;

import Clases.Reserva;
import EstadosDeReserva.EstadoDeReserva;

public class GeneradorDeFactura implements  Subsistema{

    @Override
    public void actualizarEstado(Reserva reserva, EstadoDeReserva estadoNuevo, EstadoDeReserva estadoAnterior) {
        estadoNuevo.generarFactura(reserva);
    }
}
