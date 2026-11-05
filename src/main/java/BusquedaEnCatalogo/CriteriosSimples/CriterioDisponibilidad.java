package BusquedaEnCatalogo.CriteriosSimples;

import CatalogoTuristico.ServicioTuristico;

public class CriterioDisponibilidad extends CriterioSimple {

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return servicioTuristico.tieneDisponibilidad();
    }
}
