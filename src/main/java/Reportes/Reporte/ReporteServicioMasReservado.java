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



    private final List<LineaDeReporte> lineas;

    public ReporteProductosMasVendidos(Tienda tienda, LocalDate fechaInicio, LocalDate fechaFin) {
        Map<String, Integer> cantidades = new HashMap<>();
        Map<String, Double> recaudacionTotal = new HashMap<>();

        List<Pedido> pedidosDelPeriodo = tienda.historialdePedidosDeTodasLasSucursales().stream()
                .filter(pedido -> !pedido.getFecha().isBefore(fechaInicio) && !pedido.getFecha().isAfter(fechaFin))
                .collect(Collectors.toList());

        for (Pedido pedido : pedidosDelPeriodo) {
            pedido.getCarritoDeProductos().forEach((producto, cantidad) -> {
                String nombre = producto.getNombre();
                cantidades.put(nombre, cantidades.getOrDefault(nombre, 0) + cantidad);

                double totalCobrado = producto.getPrecioFinal() * cantidad;
                recaudacionTotal.put(nombre, recaudacionTotal.getOrDefault(nombre, 0.0) + totalCobrado);
            });
        }

        lineas = cantidades.entrySet().stream()
                .map(entry -> {
                    String nombre = entry.getKey();
                    int cantidad = entry.getValue();
                    double promedio = recaudacionTotal.get(nombre) / cantidad;
                    return new LineaDeReporte(nombre, cantidad, promedio);
                })
                .sorted((l1, l2) -> Integer.compare(l2.getCantidadVendida(), l1.getCantidadVendida()))
                .collect(Collectors.toList());
    }

    public List<LineaDeReporte> getLineas() {
        return lineas;
    }

    public String exportar(FormatoDeExportacionDeReporte visitor) {
        return visitor.visit(this);
    }
}

