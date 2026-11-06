package Reportes;

import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Exportadores.Exportador;

import java.util.*;

public class ReporteProveedor implements Visitor{
    private List<ServicioIndividual> servicios = new ArrayList<>();


    public void visitarServicioIndividual(ServicioIndividual servicioIndividual){
    servicios.add(servicioIndividual);
    }

    public void visitarPaqueteTuristico(PaqueteTuristico paqueteTuristico){}

    public String generar(List<ServicioTuristico> catalogo, Exportador exportador){
        servicios.clear();
        for (ServicioTuristico s : catalogo) {
            s.aceptar(this);
        }
        Map<String, Integer> cantidadPorProveedor = new LinkedHashMap<>();
        for (ServicioIndividual s : servicios) {
            cantidadPorProveedor.merge(s.getProveedor(), 1, Integer::sum);
        }

        List<List<String>> filas = new ArrayList<>();
        for (Map.Entry<String, Integer> e : cantidadPorProveedor.entrySet()) {
            double porcentaje = e.getValue() * 100.0 / servicios.size();
            filas.add(List.of(e.getKey(), String.format(Locale.US, "%.1f%%", porcentaje)));
        }
        return exportador.exportar("Exposición por proveedor",
                List.of("Proveedor", "Porcentaje"), filas);
    }
}
