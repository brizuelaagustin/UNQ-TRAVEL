package Reportes.Exportador;

import CatalogoTuristico.Categoria;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Reporte.ReporteOcupacion;
import Reportes.Reporte.ReporteProveedor;
import Reportes.Reporte.ReporteServicioMasReservado;

import java.util.List;
import java.util.Map;

public class ExportadorHTML implements Exportador {

    @Override
    public String visitarServicioMasReservado(ReporteServicioMasReservado reporte) {
        String html = abrir("Servicios más reservados (" + reporte.getDesde() + " a " + reporte.getHasta() + ")");
        html += fila("th", List.of("Servicio", "Unidades", "Precio promedio cobrado"));
        for (ServicioTuristico item : reporte.itemsOrdenadosPorVentas()) {
            html += fila("td", List.of(item.getNombre(), reporte.unidadesVendidas(item), reporte.precioPromedioCobrado(item)));
        }
        return html + cerrar();
    }

    @Override
    public String visitarOcupacion(ReporteOcupacion reporte) {
        String html = abrir("Ocupación y cupo disponible (" + reporte.getDesde() + " a " + reporte.getHasta() + ")");
        html += fila("th", List.of("Categoría", "Cupos totales", "Reservados", "Libres"));
        for (Categoria categoria : Categoria.values()) {
            html += fila("td", List.of(categoria, reporte.cuposTotales(categoria),
                    reporte.cuposReservados(categoria), reporte.cuposLibres(categoria)));
        }
        return html + cerrar();
    }

    @Override
    public String visitarProveedor(ReporteProveedor reporte) {
        String html = abrir("Exposición por proveedor");
        html += fila("th", List.of("Proveedor", "Porcentaje"));
        for (String proveedor : reporte.proveedores()) {
            html += fila("td", List.of(proveedor, reporte.porcentaje(proveedor) + "%"));
        }
        return html + cerrar();
    }

    private String abrir(String titulo) {
        return "<html>\n"
                + "  <head>\n"
                + "    <meta charset=\"UTF-8\">\n"
                + "  </head>\n"
                + "  <body>\n"
                + "    <h1>" + escapar(titulo) + "</h1>\n"
                + "    <table border=\"1\">\n";
    }

    private String fila(String etiqueta, List<Object> celdas) {
        String fila = "      <tr>\n";
        for (Object celda : celdas) {
            fila += "        <" + etiqueta + ">" + escapar(String.valueOf(celda)) + "</" + etiqueta + ">\n";
        }
        return fila + "      </tr>\n";
    }

    private String cerrar() {
        return "    </table>\n"
                + "  </body>\n"
                + "</html>";
    }

    private String escapar(String texto) {
        return texto.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}