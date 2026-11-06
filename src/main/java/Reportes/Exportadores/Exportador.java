package Reportes.Exportadores;

import java.util.List;

public interface Exportador {
    String exportar(String titulo, List<String> encabezados, List<List<String>> filas);
}
