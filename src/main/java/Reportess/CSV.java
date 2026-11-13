package Reportess;

public class CSV implements FormatoDeExportacionDeReporte{
    @Override
    public String visit(ReporteDeServiciosMasReservados2 reporte) {
        StringBuilder sb = new StringBuilder();
        sb.append("Producto,Cantidad Vendida,Precio Promedio\n");
        for (LineaDeReporte linea : reporte.getLineas()) {
            sb.append(linea.getNombre()).append(",")
                    .append(linea.getCantidadVendida()).append(",")
                    .append(linea.getPrecioPromedioCobrado()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String visit(ReporteOcupacion2 reporte){
        StringBuilder sb = new StringBuilder();
        sb.append("Producto,Cantidad Vendida,Precio Promedio\n");
        for (LineaDeReporte linea : reporte.getLineas()) {
            sb.append(linea.getNombre()).append(",")
                    .append(linea.getCantidadVendida()).append(",")
                    .append(linea.getPrecioPromedioCobrado()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String visit(ReporteProveedor2 reporte){

}
