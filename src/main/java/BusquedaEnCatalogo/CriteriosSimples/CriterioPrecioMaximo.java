package BusquedaEnCatalogo.CriteriosSimples;

import CatalogoTuristico.ServicioTuristico;

public class CriterioPrecioMaximo extends CriterioSimple {
    private float precioMaximo;

    public CriterioPrecioMaximo(float precioMaximo){
        this.precioMaximo = precioMaximo;
    }

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return servicioTuristico.getPrecioFinal() <= this.precioMaximo;
    }
}
