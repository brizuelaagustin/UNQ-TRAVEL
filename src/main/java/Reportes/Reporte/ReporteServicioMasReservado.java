package Reportes.Reporte;

import CatalogoTuristico.Catalogo;
import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;
import CatalogoTuristico.ServicioTuristico;
import Clases.Reserva;
import Reportes.Exportador.Exportador;

import java.time.LocalDate;
import java.util.*;

public class ReporteServicioMasReservado implements Reporte {
    private final Catalogo catalogo;
    private final LocalDate desde;
    private final LocalDate hasta;

    public ReporteServicioMasReservado(Catalogo catalogo, LocalDate desde, LocalDate hasta) {
        this.catalogo = catalogo;
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public String aceptar(Exportador exportador) {
        return exportador.visitarServicioMasReservado(this);
    }

    public LocalDate getDesde() {
        return desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    // Servicios y paquetes del catálogo, del más vendido al menos vendido.
    public List<ServicioTuristico> itemsOrdenadosPorVentas() {
        List<ServicioTuristico> items = new ArrayList<>(catalogo.getServicios());
        items.sort(Comparator.comparingInt(this::unidadesVendidas).reversed());
        return items;
    }

    // Una reserva pertenece al período según su fecha de inicio.
    public int unidadesVendidas(ServicioTuristico item) {
        int total = 0;
        for (Reserva reserva : catalogo.getReservasHistorial()) {
            if (enPeriodo(reserva.getFechaInicio())) {
                total += reserva.cantidadDeServicio(item);
            }
        }
        return total;
    }

    // No se guarda el precio de cada venta, así que lo cobrado por unidad es el
    // precio final vigente (ya incluye el descuento del servicio o del paquete).
    public float precioPromedioCobrado(ServicioTuristico item) {
        return item.getPrecioFinal();
    }

    private boolean enPeriodo(LocalDate fecha) {
        return !fecha.isBefore(desde) && !fecha.isAfter(hasta);
    }
}

