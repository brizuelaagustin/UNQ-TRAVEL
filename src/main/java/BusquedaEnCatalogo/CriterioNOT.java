package BusquedaEnCatalogo;

import CatalogoTuristico.ServicioTuristico;

public class CriterioNOT implements Criterio {
    private Criterio criterio;

    public CriterioNOT(Criterio criterio){
        this.criterio = criterio;
    }

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return (!criterio.satisface(servicioTuristico));
    }
}
