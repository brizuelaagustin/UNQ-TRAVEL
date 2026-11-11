//package Reportes.Reporte;
//
//import CatalogoTuristico.Categoria;
//import CatalogoTuristico.PaqueteTuristico;
//import CatalogoTuristico.ServicioIndividual;
//import CatalogoTuristico.ServicioTuristico;
//import Reportes.Visitor;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ReporteOcupacion extends Reporte {
//    private Map<Categoria, Integer> totalPorCategoria = new HashMap<>();
//    private Map<Categoria, Integer> reservadosPorCategoria = new HashMap<>();
//
////    public void visitarServicioIndividual(ServicioIndividual s) {
////        Categoria categoria = s.getCategoria();
////
////        if (!totalPorCategoria.containsKey(categoria)) {
////            totalPorCategoria.put(categoria, 0);
////            reservadosPorCategoria.put(categoria, 0);
////        }
////        int totalActual = totalPorCategoria.get(categoria);
////        int reservadosActual = reservadosPorCategoria.get(categoria);
////
////        totalPorCategoria.put(categoria, totalActual + s.getCupoTotal());
////        reservadosPorCategoria.put(categoria, reservadosActual + s.getCupoReservado());
////    }
//
//    public void visitarPaqueteTuristico(PaqueteTuristico p) {
//        for (ServicioTuristico s : p.getPaquetes().keySet()) {
//            s.aceptar(this);
//        }
//    }
//
//    public void reiniciar() {
//        totalPorCategoria.clear();
//        reservadosPorCategoria.clear();
//    }
//
//    public Map<String, String> resultado() {
//        Map<String, String> datos = new HashMap<>();
//        for (Categoria categoria : totalPorCategoria.keySet()) {
//            int total = totalPorCategoria.get(categoria);
//            int reservados = reservadosPorCategoria.get(categoria);
//            datos.put(categoria.toString(), reservados + " reservados, " + (total - reservados) + " libres de " + total);
//        }
//        return datos;
//    }
//}