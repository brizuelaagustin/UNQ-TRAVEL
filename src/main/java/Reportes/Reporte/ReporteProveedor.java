package Reportes.Reporte;

import CatalogoTuristico.Catalogo;
import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Exportador.Exportador;

import java.util.*;

public class ReporteProveedor implements Reporte {

    private final Catalogo catalogo;

    public ReporteProveedor(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    @Override
    public String aceptar(Exportador exportador) {
        return exportador.visitarProveedor(this);
    }

    public List<String> proveedores() {
        return new ArrayList<>(serviciosPorProveedor().keySet());
    }

    // Porcentaje del catálogo que depende del proveedor, con un decimal.
    public double porcentaje(String proveedor) {
        Map<String, Integer> cantidades = serviciosPorProveedor();
        int total = 0;
        for (int cantidad : cantidades.values()) {
            total += cantidad;
        }
        if (total == 0) {
            return 0;
        }
        double porcentaje = cantidades.getOrDefault(proveedor, 0) * 100.0 / total;
        return Math.round(porcentaje * 10) / 10.0;
    }

    private Map<String, Integer> serviciosPorProveedor() {
        Map<String, Integer> cantidades = new LinkedHashMap<>();
        for (ServicioTuristico item : catalogo.getServicios()) {
            if (item instanceof ServicioIndividual) {   // el paquete no tiene proveedor propio
                String proveedor = ((ServicioIndividual) item).getProveedor();
                cantidades.put(proveedor, cantidades.getOrDefault(proveedor, 0) + 1);
            }
        }
        return cantidades;
    }
}