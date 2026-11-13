package Reportes.Reporte;

import CatalogoTuristico.Catalogo;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Exportador.Exportador;
import Reportes.Visitable;
import Reportes.Visitor;

import java.util.List;
import java.util.Map;

 public abstract class Reporte implements Visitor {

    final String generar(Catalogo catalogo, Exportador exportador) {
        reiniciar();
        for (ServicioTuristico s : catalogo.getServicios()) {
            s.aceptar(this);
        }
        return exportador.exportar(resultado());
    }

    public abstract void reiniciar();
    public abstract Map<String, String> resultado();
}

