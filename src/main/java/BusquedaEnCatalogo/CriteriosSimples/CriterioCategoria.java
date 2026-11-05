package BusquedaEnCatalogo.CriteriosSimples;

import CatalogoTuristico.Categoria;
import CatalogoTuristico.ServicioTuristico;

public class CriterioCategoria extends CriterioSimple {
    private Categoria categoriaBuscada;

    public CriterioCategoria(Categoria categoriaBuscada){
        this.categoriaBuscada = categoriaBuscada;
    }

    @Override
    public Boolean satisface(ServicioTuristico servicioTuristico) {
        return servicioTuristico.perteneceACategoria(this.categoriaBuscada);
    }
}
