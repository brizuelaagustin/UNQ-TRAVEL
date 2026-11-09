package Reportes.Exportador;

import java.util.Map;

public class ExportadorTextoPlano implements Exportador {
    public String exportar(Map<String, String> datos) {
        String resultado = "";
        for (Map.Entry<String, String> e : datos.entrySet()) {
            resultado += e.getKey() + ": " + e.getValue() + "\n";
        }
        return resultado;
    }
}