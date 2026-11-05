package BusquedaEnCatalogo.CriteriosSimples;

import CatalogoTuristico.ServicioTuristico;

public class CriterioNombre extends CriterioSimple {
    private String nombreBuscado;

    public CriterioNombre(String nombreBuscado){
        this.nombreBuscado = nombreBuscado;
    }

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return servicioTuristico.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase());
    }
}
