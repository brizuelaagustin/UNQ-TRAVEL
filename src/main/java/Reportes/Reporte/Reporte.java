package Reportes.Reporte;

import CatalogoTuristico.Catalogo;
import CatalogoTuristico.ServicioTuristico;
import Reportes.Exportador.Exportador;

import java.util.List;

public interface Reporte {
        String aceptar(Exportador exportador);
    }

