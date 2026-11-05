package CatalogoTuristico;

import Excepciones.reservas.NoHayStockException;
import Excepciones.reservas.ServicioNoEncontradoException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
public class Catalogo {
    private Map<ServicioTuristico,Integer> serviciosDisponibles = new HashMap<>();//esto no
    private List<ServicioTuristico> servicios = new ArrayList<>();


    public void registrarNuevoServicio (ServicioTuristico servicio){
        if(servicios.contains(servicio)){
          throw new ServicioNoEncontradoException("Este servicio ya esta en el catalogo");
        } else {
            servicios.add(servicio);
        }
    }

    public void agregarServicioDisponible(ServicioTuristico servicio,Integer cantidad){
       if(!servicios.contains(servicio)){
           throw new ServicioNoEncontradoException("El servicio no se encuentra en el catalogo");
       }else {
           int cantidadServicioAnterior = serviciosDisponibles.getOrDefault(servicio,0);
           serviciosDisponibles.put(servicio, cantidad + cantidadServicioAnterior);
       }
    }
    public void quitarServicioDisponible(ServicioTuristico servicio, Integer cantidad){
        if(!servicios.contains(servicio) || !serviciosDisponibles.containsKey(servicio)){
            throw new ServicioNoEncontradoException("El servicio no se encuentra en el catalogo");
        } else if (serviciosDisponibles.get(servicio) < cantidad) {
            throw new NoHayStockException("La cantidad no coincide con el stock disponible en el catalogo");
        } else {
            Integer cantidadAnterior = serviciosDisponibles.get(servicio);
            serviciosDisponibles.put(servicio, cantidadAnterior-cantidad);
        }
    }
    public boolean tieneStockParaReserva(ServicioTuristico servicio,Integer cantidad){
        if (!servicios.contains(servicio) || !serviciosDisponibles.containsKey(servicio)){
            throw new ServicioNoEncontradoException("El servicio no se encuentra en el catalogo");
        } else {
            return serviciosDisponibles.getOrDefault(servicio, 0) >= cantidad;
        }
    }
}
