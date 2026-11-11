package NotificacionesDeReserva;

import Clases.Reserva;
import EstadosDeReserva.EstadoDeReserva;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class NotificadorDeReserva {
    private final Set<Subsistema> observadores = new LinkedHashSet<>();

    public void registrarObservador(Subsistema observer){
        observadores.add(observer);
    }

    public void darDeBajaAObservador(Subsistema observer){
        observadores.remove(observer);
    }

    public void notificarCambio(Reserva reserva, EstadoDeReserva estadoNuevo, EstadoDeReserva estadoAnterior){
        for(Subsistema o : observadores){
            o.actualizarEstado(reserva,estadoNuevo,estadoAnterior);
        }
    }
}
