package CatalogoTuristico;

import Excepciones.reservas.NoHayStockException;
import Excepciones.reservas.ServicioNoEncontradoException;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Getter
public class Catalogo {
    private List<ServicioTuristico> servicios = new ArrayList<>();

    public void registrarNuevoServicio (ServicioTuristico servicio){
        if(servicios.contains(servicio)){
          throw new ServicioNoEncontradoException("Este servicio ya existe en el catalogo");
        } else {
            servicios.add(servicio);
        }
    }

    public void quitarServicio(ServicioTuristico servicio){
        if(!servicios.contains(servicio)){
            throw new ServicioNoEncontradoException("El servicio no existe en el catalogo");
        } else {
           servicios.remove(servicio);
        }
    }
    public boolean tieneStockParaReserva(ServicioTuristico servicio,Integer cantidadCupos,LocalDate fechaInicio, LocalDate fechaFin){
        if (!servicios.contains(servicio)){
            throw new ServicioNoEncontradoException("El servicio no existe en el catalogo");
        } else {
            return servicio.hayCuposEn(fechaInicio, fechaFin, cantidadCupos);
        }
    }

    public boolean tieneServicio(ServicioTuristico servicio) {
        return servicios.contains(servicio);
    }
}
