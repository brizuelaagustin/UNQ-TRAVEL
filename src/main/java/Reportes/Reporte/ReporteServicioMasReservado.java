package Reportes.Reporte;

import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Visitor;

import java.util.*;

public class ReporteServicioMasReservado implements Visitor {
    private List<ServicioTuristico> servicios = new ArrayList<>();

    public void visitarServicioIndividual(ServicioIndividual s) {
        servicios.add(s);
    }

    public void visitarPaqueteTuristico(PaqueteTuristico p) {
        servicios.add(p);
    }

    public void reiniciar() {
        servicios.clear();
    }

    protected Map<String, String> resultado() {
        List<ServicioTuristico> ordenados = new ArrayList<>(servicios);

        for (int i = 0; i < ordenados.size(); i++) {
            for (int j = i + 1; j < ordenados.size(); j++) {
                if (ordenados.get(j).getCantidadVendida() > ordenados.get(i).getCantidadVendida()) {
                    ServicioTuristico servicio = ordenados.get(i);
                    ordenados.set(i, ordenados.get(j));
                    ordenados.set(j, servicio);
                }
            }
        }

        Map<String, String> datos = new LinkedHashMap<>();
        for (ServicioTuristico s : ordenados) {
            datos.put(s.getNombre(),
                    s.getCantidadVendida() + " vendidos, precio promedio $" + s.getPrecioPromedioCobrado());
        }
        return datos;
    }
}
