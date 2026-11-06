package Reportes;

import CatalogoTuristico.Categoria;
import CatalogoTuristico.PaqueteTuristico;
import CatalogoTuristico.ServicioIndividual;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Exportadores.Exportador;

import java.util.ArrayList;
import java.util.List;

public class ReporteOcupacion implements Visitor{
    private List<ServicioIndividual> servicios= new ArrayList<>();

    public void visitarServicioIndividual(ServicioIndividual servicioIndividual){
        servicios.add(servicioIndividual);
    }

    public void visitarPaqueteTuristico(PaqueteTuristico paqueteTuristico){

    }

    public String generar(List<ServicioTuristico> catalogo, Exportador exportador){
        servicios.clear();
        for (ServicioTuristico item : catalogo) {
            item.aceptar(this);
        }

        List<List<String>> filas = new ArrayList<>();
        for (Categoria categoria : Categoria.values()) {
            int total = 0;
            int libres = 0;
            for (ServicioIndividual s : servicios) {
                if (s.perteneceACategoria(categoria)) {
                    total += s.getCupoTotal();
                    libres += s.getCupoDisponible();
                }
            }
            filas.add(List.of(categoria.name(), String.valueOf(total),
                    String.valueOf(total - libres), String.valueOf(libres)));
        }
        return exportador.exportar("Ocupación y cupo disponible",
                List.of("Categoría", "Cupos totales", "Reservados", "Libres"), filas);
    }
}
