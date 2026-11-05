package BusquedaEnCatalogo;

import CatalogoTuristico.ServicioTuristico;

public interface Criterio {
    public Boolean satisface(ServicioTuristico servicioTuristico);
}
