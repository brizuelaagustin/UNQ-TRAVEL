package Reportes.Reporte;

import CatalogoTuristico.*;
import Clases.Reserva;
import Reportes.Exportador.Exportador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReporteOcupacion implements Reporte {
    private final Catalogo catalogo;
    private final LocalDate desde;
    private final LocalDate hasta;

    public ReporteOcupacion(Catalogo catalogo, LocalDate desde, LocalDate hasta) {
        this.catalogo = catalogo;
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public String aceptar(Exportador exportador) {
        return exportador.visitarOcupacion(this);
    }

    public LocalDate getDesde() { return desde; }

    public LocalDate getHasta() { return hasta; }

    // Lo que todavía se puede reservar (el cronograma guarda los cupos que quedan por día).
    public int cuposLibres(Categoria categoria) {
        int libres = 0;
        for (ServicioTuristico item : catalogo.getServicios()) {
            if (item.perteneceACategoria(categoria)) {
                for (LocalDate dia : dias()) {
                    libres += item.getCronogramaServicio().getCuposPorDia().getOrDefault(dia, 0);
                }
            }
        }
        return libres;
    }

    // Lo que ya se reservó: unidades de cada reserva, por cada día del período que la reserva cubre.
    public int cuposReservados(Categoria categoria) {
        int reservados = 0;
        for (ServicioTuristico item : catalogo.getServicios()) {
            if (item.perteneceACategoria(categoria)) {
                for (LocalDate dia : dias()) {
                    for (Reserva reserva : catalogo.getReservasHistorial()) {
                        if (cubre(reserva, dia)) {
                            reservados += reserva.cantidadDeServicio(item);
                        }
                    }
                }
            }
        }
        return reservados;
    }

    public int cuposTotales(Categoria categoria) {
        return cuposLibres(categoria) + cuposReservados(categoria);
    }

    private boolean cubre(Reserva reserva, LocalDate dia) {
        return !dia.isBefore(reserva.getFechaInicio()) && !dia.isAfter(reserva.getFechaFin());
    }

    private List<LocalDate> dias() {
        List<LocalDate> dias = new ArrayList<>();
        for (LocalDate dia = desde; !dia.isAfter(hasta); dia = dia.plusDays(1)) {
            dias.add(dia);
        }
        return dias;
    }
}