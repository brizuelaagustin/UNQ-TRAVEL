package Reportes.Reporte;

import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Visitor;

import java.time.LocalDate;
import java.util.*;

public class ReporteServicioMasReservado extends Reporte {
    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
    private List<ServicioTuristico> servicios;

    public ReporteServicioMasReservado(LocalDate inicio, LocalDate fin) {
        this.fechaInicial = inicio;
        this.fechaFinal = fin;
        this.servicios = new ArrayList<>();
    }

    public void visitarServicioIndividual(ServicioIndividual s) {
        servicios.add(s);
    }

    public void visitarPaqueteTuristico(PaqueteTuristico p) {
        servicios.add(p);
    }

    public void reiniciar() {
        servicios.clear();
    }

    public Map<String, String> resultado() {
        List<ServicioTuristico> ordenados = new ArrayList<>(servicios);

        for (int i = 0; i < ordenados.size(); i++) {
            for (int j = i + 1; j < ordenados.size(); j++) {
                if (ordenados.get(j).cantidadVendidaEnPeriodo(this.fechaInicial, this.fechaFinal) > ordenados.get(i).cantidadVendidaEnPeriodo(this.fechaInicial, this.fechaFinal)) {
                    ServicioTuristico servicio = ordenados.get(i);
                    ordenados.set(i, ordenados.get(j));
                    ordenados.set(j, servicio);
                }
            }
        }

        Map<String, String> datos = new LinkedHashMap<>();
        for (ServicioTuristico s : ordenados) {
            datos.put(s.getNombre(),
                    s.cantidadVendidaEnPeriodo(this.fechaInicial, this.fechaFinal) + " vendidos, precio promedio $" + s.PrecioPromedioCobradoEnPeriodo(this.fechaInicial, this.fechaFinal));
        }
        return datos;
    }
}
