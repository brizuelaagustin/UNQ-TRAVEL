package BusquedaEnCatalogo.CriteriosComplejos;

import BusquedaEnCatalogo.Criterio;
import CatalogoTuristico.ServicioTuristico;

public abstract class CriterioComplejo implements Criterio {
    public abstract Boolean satisface(ServicioTuristico servicioTuristico);
}