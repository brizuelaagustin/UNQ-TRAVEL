package Reportes.Exportadores;

import java.util.List;

public class ExportadorHTML implements Exportador {

    public String exportar(String titulo, List<String> encabezados, List<List<String>> filas) {

        String resultado = "<html>\n";
        resultado += "  <body>\n";
        resultado += "    <h1>" + titulo + "</h1>\n";
        resultado += "    <table>\n";

        resultado += "      <tr>\n";
        for (String encabezado : encabezados) {
            resultado += "        <th>" + encabezado + "</th>\n";
        }
        resultado += "      </tr>\n";

        for (List<String> fila : filas) {
            resultado += "      <tr>\n";
            for (String celda : fila) {
                resultado += "        <td>" + celda + "</td>\n";
            }
            resultado += "      </tr>\n";
        }

        resultado += "    </table>\n";
        resultado += "  </body>\n";
        resultado += "</html>";

        return resultado;
    }
}
