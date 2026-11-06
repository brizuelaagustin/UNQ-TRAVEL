package MetodosDeTraslado;
import Clases.Reserva;
public class PuntoDeEncuentro implements MetodoDeTraslado{

    public float calcularCosto(Reserva reserva){

        return 0;
    }

    @Override
    public Boolean estaDisponible(Reserva reserva){
        return  reserva.getTieneCupoDeGuias();
    }


}
