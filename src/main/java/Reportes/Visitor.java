package Reportes;

import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;

public interface Visitor {

    void visitarServicioIndividual(ServicioIndividual servicioIndividual);
    void visitarPaqueteTuristico(PaqueteTuristico paqueteTuristico);
}
