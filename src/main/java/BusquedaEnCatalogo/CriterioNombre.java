package BusquedaEnCatalogo;

import CatalogoTuristico.ServicioTuristico;

public class CriterioNombre implements Criterio{
    private String nombreBuscado;

    public CriterioNombre(String nombreBuscado){
        this.nombreBuscado = nombreBuscado;
    }

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return servicioTuristico.getNombre().toLowerCase().contains(nombreBuscado.toLowerCase());
    }
}
