package BusquedaEnCatalogo.CriteriosComplejos;

import BusquedaEnCatalogo.Criterio;
import CatalogoTuristico.ServicioTuristico;

public class CriterioOR extends CriterioComplejo {
    private Criterio primerCriterio;
    private Criterio segundoCriterio;

    public CriterioOR(Criterio primerCriterio, Criterio segundoCriterio){
        this.primerCriterio = primerCriterio;
        this.segundoCriterio = segundoCriterio;
    }

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return (primerCriterio.satisface(servicioTuristico) || segundoCriterio.satisface(servicioTuristico));
    }
}