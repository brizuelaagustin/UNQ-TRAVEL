package Reportes.Reporte;

import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;
import CatalogoTuristico.ServicioTuristico;

import java.util.*;

public class ReporteProveedor extends Reporte {

    private Map<String, Integer> cantidadPorProveedor = new HashMap<>();

    public void visitarServicioIndividual(ServicioIndividual s) {
        String proveedor = s.getProveedor();

        if (!cantidadPorProveedor.containsKey(proveedor)) {
            cantidadPorProveedor.put(proveedor, 0);
        }

        int cantidadActual = cantidadPorProveedor.get(proveedor);
        cantidadPorProveedor.put(proveedor, cantidadActual + 1);
    }


    public void visitarPaqueteTuristico(PaqueteTuristico p) {
        for (ServicioTuristico s : p.getPaquetes().keySet()) {
            s.aceptar(this);
        }
    }

    public void reiniciar() {
        cantidadPorProveedor.clear();
    }

    public Map<String, String> resultado() {
        int total = 0;
        for (int cantidad : cantidadPorProveedor.values()) {
            total += cantidad;
        }
        Map<String, String> datos = new HashMap<>();
        for (Map.Entry<String, Integer> e : cantidadPorProveedor.entrySet()) {
            datos.put(e.getKey(), (e.getValue() * 100 / total) + "%");
        }
        return datos;
    }
}