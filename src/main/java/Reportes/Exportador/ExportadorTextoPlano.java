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
        texto += linea("Servicio", "Unidades", "Precio promedio cobrado");
        for (ServicioTuristico item : reporte.itemsOrdenadosPorVentas()) {
            texto += linea(item.getNombre(), reporte.unidadesVendidas(item), reporte.precioPromedioCobrado(item));
        }
        return texto;
    }

    @Override
    public String visitarOcupacion(ReporteOcupacion reporte) {
        String texto = titulo("Ocupación y cupo disponible (" + reporte.getDesde() + " a " + reporte.getHasta() + ")");
        texto += linea("Categoría", "Cupos totales", "Reservados", "Libres");
        for (Categoria categoria : Categoria.values()) {
            texto += linea(categoria, reporte.cuposTotales(categoria),
                    reporte.cuposReservados(categoria), reporte.cuposLibres(categoria));
        }
        return texto;
    }

    @Override
    public String visitarProveedor(ReporteProveedor reporte) {
        String texto = titulo("Exposición por proveedor");
        texto += linea("Proveedor", "Porcentaje");
        for (String proveedor : reporte.proveedores()) {
            texto += linea(proveedor, reporte.porcentaje(proveedor) + "%");
        }
        return texto;
    }

    private String titulo(String titulo) {
        return titulo + "\n" + "-".repeat(titulo.length()) + "\n";
    }

    private String linea(Object... celdas) {
        String linea = "";
        for (int i = 0; i < celdas.length; i++) {
            if (i > 0) {
                linea += " | ";
            }
            linea += celdas[i];
        }
        return linea + "\n";
    }
}