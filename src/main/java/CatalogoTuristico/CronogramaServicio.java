package CatalogoTuristico;

import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class CronogramaServicio {
    Map<LocalDate,Integer> cuposPorDia = new HashMap<>();

    public boolean hayCuposEn(LocalDate fechaInicio, LocalDate fechaFin, Integer cantidadCupos) {
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            if (!cuposPorDia.containsKey(fecha) || cuposPorDia.get(fecha) < cantidadCupos) {
                return false;
            }
        }
        return true;
    }

    public void quitarCupos(Integer cantidadDeCupos, LocalDate fechaInicio, LocalDate fechaFin) {
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            int cuposAnteriores = cuposPorDia.get(fecha);
            cuposPorDia.put(fecha, cuposAnteriores-cantidadDeCupos);
        }
    }

    public void devolverCupos(Integer cantidadDeCupos, LocalDate fechaInicio, LocalDate fechaFin) {
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            int cuposAnteriores = cuposPorDia.get(fecha);
            cuposPorDia.put(fecha, cuposAnteriores+cantidadDeCupos);
        }
    }
}