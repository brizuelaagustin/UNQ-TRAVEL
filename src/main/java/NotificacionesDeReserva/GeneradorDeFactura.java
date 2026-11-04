package NotificacionesDeReserva;

import Clases.Reserva;
import EstadosDeReserva.EstadoDeReserva;

public class GeneradorDeFactura implements  Subsistema{

    @Override
    public void actualizarEstado(Reserva reserva, EstadoDeReserva estadoNuevo, EstadoDeReserva estadoAnterior) {
        String nombreDelEstado = estadoNuevo.getClass().getSimpleName();

        if(nombreDelEstado.contains("Finalizada")){
            System.out.println("Generando comprobante fiscal y voucher para la reserva por un total de" + reserva.getPrecio());
        }

    }
}
