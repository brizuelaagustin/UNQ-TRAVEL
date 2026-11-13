package Reportes.Exportador;

import Reportes.Reporte.ReporteOcupacion;
import Reportes.Reporte.ReporteProveedor;
import Reportes.Reporte.ReporteServicioMasReservado;

import java.util.List;
import java.util.Map;

public interface Exportador {
    String visitarServicioMasReservado(ReporteServicioMasReservado reporte);
    String visitarOcupacion(ReporteOcupacion reporte);
    String visitarProveedor(ReporteProveedor reporte);
}