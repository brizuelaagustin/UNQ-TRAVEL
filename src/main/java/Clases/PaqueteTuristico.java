package Clases;

import java.util.HashMap;
import java.util.Map;

public class PaqueteTuristico extends  ServicioTuristico {
    private Map<ServicioTuristico,Integer> paquetes = new HashMap<>();

    public void agregarPaquetes(ServicioTuristico servicioTuristico,int cantidad){


    }
    public void quitarPaquetes(ServicioTuristico servicioTuristico, int cantidad){

    }
    public float getPrecioBase(){
        float totalBase = 0;
        for (Map.Entry<ServicioTuristico, Integer> entry : paquetes.entrySet()) {
            totalBase += entry.getKey().getPrecioBase() * entry.getValue();
        }
        return totalBase;
    }
}
