package MetodosDeTraslado;
import Clases.Reserva;

public interface MetodoDeTraslado {

     float calcularCosto(Reserva reserva);
     Boolean estaDisponible(Reserva reserva);
}
