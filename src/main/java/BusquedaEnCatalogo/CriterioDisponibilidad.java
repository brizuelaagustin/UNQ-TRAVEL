package BusquedaEnCatalogo;

import CatalogoTuristico.ServicioTuristico;

public class CriterioDisponibilidad implements Criterio {

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return servicioTuristico.tieneDisponibilidad();
    }
}
