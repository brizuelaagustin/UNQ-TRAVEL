package Reportes.Exportadores;

import java.util.List;

public class ExportadorTextoPlano implements Exportador {

    public String exportar(String titulo, List<String> encabezados, List<List<String>> filas) {
        return "s";
    }
}
