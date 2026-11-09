package Reportes.Exportador;

import java.util.Map;

public interface Exportador {
    String exportar(Map<String, String> datos);
}
