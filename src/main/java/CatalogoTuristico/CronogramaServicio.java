package CatalogoTuristico;

import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CronogramaServicio {
    Map<LocalDate,Integer> cuposPorDia = new HashMap<>();
}
