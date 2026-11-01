package main.java.Clases.MetodosDeTraslado;
import main.java.Clases.Reserva;

public class TrasladoEstandar implements MetodoDeTraslado {

    PlataformaTransporte plataformaDeTransporte;

    public TrasladoEstandar(PlataformaTransporte plataformaDeTransporte){
        this.plataformaDeTransporte = plataformaDeTransporte;
    }

    public float calcularCosto(Reserva reserva){
    return plataformaDeTransporte.estimarTraslado(reserva.getCantidadPasajeros(), reserva.getDireccionDestino());
    }

}
