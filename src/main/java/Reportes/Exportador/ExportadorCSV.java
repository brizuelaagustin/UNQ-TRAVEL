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
        String csv = linea(List.of("Servicio", "Unidades", "Precio promedio cobrado"));
        for (ServicioTuristico item : reporte.itemsOrdenadosPorVentas()) {
            csv += linea(List.of(item.getNombre(), reporte.unidadesVendidas(item), reporte.precioPromedioCobrado(item)));
        }
        return csv;
    }

    @Override
    public String visitarOcupacion(ReporteOcupacion reporte) {
        String csv = linea(List.of("Categoría", "Cupos totales", "Reservados", "Libres"));
        for (Categoria categoria : Categoria.values()) {
            csv += linea(List.of(categoria, reporte.cuposTotales(categoria),
                    reporte.cuposReservados(categoria), reporte.cuposLibres(categoria)));
        }
        return csv;
    }

    @Override
    public String visitarProveedor(ReporteProveedor reporte) {
        String csv = linea(List.of("Proveedor", "Porcentaje"));
        for (String proveedor : reporte.proveedores()) {
            csv += linea(List.of(proveedor, reporte.porcentaje(proveedor) + "%"));
        }
        return csv;
    }

    private String linea(List<Object> celdas) {
        String linea = "";
        for (int i = 0; i < celdas.size(); i++) {
            if (i > 0) linea += ",";
            linea += escapar(String.valueOf(celdas.get(i)));
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