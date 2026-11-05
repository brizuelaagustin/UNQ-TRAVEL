package BusquedaEnCatalogo.CriteriosComplejos;

import BusquedaEnCatalogo.Criterio;
import CatalogoTuristico.ServicioTuristico;

public class CriterioNOT extends CriterioComplejo {
    private Criterio criterio;

    public CriterioNOT(Criterio criterio){
        this.criterio = criterio;
    }

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return (!criterio.satisface(servicioTuristico));
    }
}
