package BusquedaEnCatalogo;

import CatalogoTuristico.ServicioTuristico;

import java.util.ArrayList;
import java.util.List;

public class BusquedaEnCatalogo {
    private List<ServicioTuristico> servicios;

    public BusquedaEnCatalogo(List<ServicioTuristico> servicios){
        this.servicios = servicios;
    }

    public List<ServicioTuristico> buscar(Criterio criterio) {
        List<ServicioTuristico> resultado = new ArrayList<>();
        for (ServicioTuristico s : servicios) {
            if (criterio.satisface(s)) {
                resultado.add(s);
            }
        }
        return resultado;
    }
}
