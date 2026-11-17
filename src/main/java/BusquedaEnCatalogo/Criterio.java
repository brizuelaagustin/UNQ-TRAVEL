package BusquedaEnCatalogo;

import CatalogoTuristico.ServicioTuristico;

public interface Criterio {
     Boolean satisface(ServicioTuristico servicioTuristico);
}
