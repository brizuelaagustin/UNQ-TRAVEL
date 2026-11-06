package Reportes;

import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Exportadores.Exportador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ReporteServicioMasReservado implements Visitor{
    private List<ServicioTuristico> servicios = new ArrayList<>();


    public void visitarServicioIndividual(ServicioIndividual servicio) {
        servicios.add(servicio);
    }


    public void visitarPaqueteTuristico(PaqueteTuristico paquete) {
        servicios.add(paquete);
    }

    public String generar(List<ServicioTuristico> catalogo, Exportador exportador) {
        servicios.clear();
        for (ServicioTuristico item : catalogo) {
            item.aceptar(this);
        }
        Collections.sort(servicios, (a, b) -> b.getCantidadVendida() - a.getCantidadVendida());

        List<List<String>> filas = new ArrayList<>();
        for (ServicioTuristico item : servicios) {
            filas.add(List.of(item.getNombre(),
                    String.valueOf(item.getCantidadVendida()),
                    String.format(Locale.US, "%.2f", item.getPrecioPromedioCobrado())));
        }
        return exportador.exportar("Servicios más reservados",
                List.of("Servicio", "Unidades", "Precio promedio cobrado"), filas);
    }
}
