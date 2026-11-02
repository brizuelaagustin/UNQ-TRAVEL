package CatalogoTuristico;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
public class Catalogo {
    private Map<ServicioTuristico,Integer> serviciosDisponibles = new HashMap<>();
    private List<ServicioTuristico> servicios = new ArrayList<>();

    public void agregarServicioDisponible(ServicioTuristico servicio,Integer cantidad){
        serviciosDisponibles.put(servicio,cantidad);
    }
    public void quitarServicioDisponible(Map<ServicioTuristico,Integer>servicios){
        for(ServicioTuristico c: servicios.keySet()){
            serviciosDisponibles.put(c, serviciosDisponibles.get(c)-serviciosDisponibles.get(c));
        }
    }
    public boolean tieneStockParaReserva(ServicioTuristico servicio,Integer cantidad){
        return serviciosDisponibles.getOrDefault(servicio,0)>= cantidad;

    }
}
