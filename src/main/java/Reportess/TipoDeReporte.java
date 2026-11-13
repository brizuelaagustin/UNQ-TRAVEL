package Reportess;

public interface TipoDeReporte {
    List<LineaDeReporte> getLineas();
    String exportar(FormatoDeExportacionDeReporte formato);
}
