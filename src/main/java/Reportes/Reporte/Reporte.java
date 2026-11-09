package Reportes.Reporte;

import Reportes.Exportador.Exportador;
import Reportes.Visitable;
import Reportes.Visitor;

import java.util.List;
import java.util.Map;

public abstract class Reporte implements Visitor {

    final String generar(List<Visitable> items, Exportador exportador) {
        reiniciar();
        for (Visitable item : items) {
            item.aceptar(this);
        }
        return exportador.exportar(resultado());
    }

    public abstract void reiniciar();
    public abstract Map<String, String> resultado();
}

