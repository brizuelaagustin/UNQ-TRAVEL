package Reportess;

public class HTML implements FormatoDeExportacionDeReporte{
    @Override
    public String visit(ReporteDeServiciosMasReservados2 reporte) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n<thead>\n<tr><th>Producto</th><th>Vendidos</th><th>Precio Promedio</th></tr>\n</thead>\n<tbody>\n");
        for (LineaDeReporte linea : reporte.getLineas()) {
            sb.append("  <tr><td>").append(linea.getNombre()).append("</td>")
                    .append("<td>").append(linea.getCantidadVendida()).append("</td>")
                    .append("<td>").append(linea.getPrecioPromedioCobrado()).append("</td></tr>\n");
        }
        sb.append("</tbody>\n</table>");
        return sb.toString();
    }
}
