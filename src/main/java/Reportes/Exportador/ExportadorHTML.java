package Reportes.Exportador;

import java.util.Map;

public class ExportadorHTML implements Exportador {

    public String exportar(Map<String, String> datos) {
        String resultado = "<ul>\n";
        for (Map.Entry<String, String> e : datos.entrySet()) {
            resultado += "<li>" + e.getKey() + ": " + e.getValue() + "</li>\n";
        }
        return resultado + "</ul>";
    }
}