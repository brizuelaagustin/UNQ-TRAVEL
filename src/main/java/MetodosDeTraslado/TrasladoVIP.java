package MetodosDeTraslado;
import Clases.Reserva;


public class TrasladoVIP implements MetodoDeTraslado{
    private float porcentajeFijo;
    private float cargoBase;

    public TrasladoVIP(float porcentajeFijo, float cargoBase){
        this.porcentajeFijo = porcentajeFijo;
        this.cargoBase = cargoBase;
    }

    public float calcularCosto(Reserva reserva){
        float precioDeLaReserva = reserva.getPrecio();
        return (precioDeLaReserva * porcentajeFijo) + cargoBase;
    }

    @Override
    public Boolean estaDisponible(Reserva reserva) {
        return true;
    }
}
