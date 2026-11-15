package Reportes.Exportador;

import CatalogoTuristico.Categoria;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Reporte.ReporteOcupacion;
import Reportes.Reporte.ReporteProveedor;
import Reportes.Reporte.ReporteServicioMasReservado;

import java.util.List;
import java.util.Map;

public class ExportadorTextoPlano implements Exportador {

    @Override
    public String visitarServicioMasReservado(ReporteServicioMasReservado reporte) {
        String texto = titulo("Servicios más reservados (" + reporte.getDesde() + " a " + reporte.getHasta() + ")");
        texto += linea(List.of("Servicio", "Unidades", "Precio promedio cobrado"));
        for (ServicioTuristico item : reporte.itemsOrdenadosPorVentas()) {
            texto += linea(List.of(item.getNombre(), reporte.unidadesVendidas(item), reporte.precioPromedioCobrado(item)));
        }
        return texto;
    }

    @Override
    public String visitarOcupacion(ReporteOcupacion reporte) {
        String texto = titulo("Ocupación y cupo disponible (" + reporte.getDesde() + " a " + reporte.getHasta() + ")");
        texto += linea(List.of("Categoría", "Cupos totales", "Reservados", "Libres"));
        for (Categoria categoria : Categoria.values()) {
            texto += linea(List.of(categoria, reporte.cuposTotales(categoria),
                    reporte.cuposReservados(categoria), reporte.cuposLibres(categoria)));
        }
        return texto;
    }

    @Override
    public String visitarProveedor(ReporteProveedor reporte) {
        String texto = titulo("Exposición por proveedor");
        texto += linea(List.of("Proveedor", "Porcentaje"));
        for (String proveedor : reporte.proveedores()) {
            texto += linea(List.of(proveedor, reporte.porcentaje(proveedor) + "%"));
        }
        return texto;
    }

    private String titulo(String titulo) {
        return titulo + "\n" + "-".repeat(titulo.length()) + "\n";
    }

    private String linea(List<Object> celdas) {
        String linea = "";
        for (int i = 0; i < celdas.size(); i++) {
            if (i > 0) {
                linea += " | ";
            }
            linea += celdas.get(i);
        }
        return linea + "\n";
    }
}