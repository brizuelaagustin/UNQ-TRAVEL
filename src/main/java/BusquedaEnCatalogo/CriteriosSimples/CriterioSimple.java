package BusquedaEnCatalogo.CriteriosSimples;

import BusquedaEnCatalogo.Criterio;
import CatalogoTuristico.ServicioTuristico;

public abstract class CriterioSimple implements Criterio {

    public abstract Boolean satisface(ServicioTuristico servicioTuristico);
}
