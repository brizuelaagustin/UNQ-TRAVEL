package CatalogoTuristico;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@Getter
public class PaqueteTuristico extends  ServicioTuristico {
    private Map<ServicioTuristico,Integer> paquetes;
    private float descuento;

    public PaqueteTuristico(String nombre, String descripcion,
                            Map<ServicioTuristico, Integer> items, float descuento, CronogramaServicio cronogramaServicio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.paquetes = new HashMap<>(items);
        this.descuento = descuento;
        this.cronogramaServicio = cronogramaServicio;
        this.atributosExtra = new ArrayList<>();
    }

    public float getPrecioBase(){
        float totalBase = 0;
        for (Map.Entry<ServicioTuristico, Integer> entry : paquetes.entrySet()) {
            totalBase += entry.getKey().getPrecioFinal() * entry.getValue();
        }
        return totalBase ;
    }

    @Override
    public float getPrecioFinal() {
        float totalBase = 0;
        for (Map.Entry<ServicioTuristico, Integer> entry : paquetes.entrySet()) {
            totalBase += entry.getKey().getPrecioFinal() * entry.getValue();
        }
        return totalBase - (totalBase * descuento);
    }

    public Boolean perteneceACategoria(Categoria categoria){
        for (Map.Entry<ServicioTuristico, Integer> entry : paquetes.entrySet()) {
            if (entry.getKey().perteneceACategoria(categoria)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean tieneDisponibilidad() {
        for (Map.Entry<ServicioTuristico, Integer> entry : paquetes.entrySet()) {
            if (!entry.getKey().tieneDisponibilidad()) {
                return false;
            }
        }
        return true;
    }
}
