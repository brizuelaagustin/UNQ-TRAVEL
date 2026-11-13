package Reportes.Exportador;

import CatalogoTuristico.Categoria;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Reporte.ReporteOcupacion;
import Reportes.Reporte.ReporteProveedor;
import Reportes.Reporte.ReporteServicioMasReservado;

import java.util.List;
import java.util.Map;

public class ExportadorCSV implements Exportador {

    @Override
    public String visitarServicioMasReservado(ReporteServicioMasReservado reporte) {
        String csv = linea("Servicio", "Unidades", "Precio promedio cobrado");
        for (ServicioTuristico item : reporte.itemsOrdenadosPorVentas()) {
            csv += linea(item.getNombre(), reporte.unidadesVendidas(item), reporte.precioPromedioCobrado(item));
        }
        return csv;
    }

    @Override
    public String visitarOcupacion(ReporteOcupacion reporte) {
        String csv = linea("Categoría", "Cupos totales", "Reservados", "Libres");
        for (Categoria categoria : Categoria.values()) {
            csv += linea(categoria, reporte.cuposTotales(categoria),
                    reporte.cuposReservados(categoria), reporte.cuposLibres(categoria));
        }
        return csv;
    }

    @Override
    public String visitarProveedor(ReporteProveedor reporte) {
        String csv = linea("Proveedor", "Porcentaje");
        for (String proveedor : reporte.proveedores()) {
            csv += linea(proveedor, reporte.porcentaje(proveedor) + "%");
        }
        return csv;
    }

    private String linea(Object... celdas) {
        String linea = "";
        for (int i = 0; i < celdas.length; i++) {
            if (i > 0) {
                linea += ",";
            }
            linea += escapar(String.valueOf(celdas[i]));
        }
        return linea + "\n";
    }

    private String escapar(String texto) {
        if (texto.contains(",") || texto.contains("\"") || texto.contains("\n")) {
            return "\"" + texto.replace("\"", "\"\"") + "\"";
        }
        return texto;
    }
}