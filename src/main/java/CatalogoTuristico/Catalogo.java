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

    public void agregarServicioDisponible(ServicioTuristico servicio, Integer cantidadCupos, LocalDate fechaDeDisponibilidad){
       if(!servicios.contains(servicio)){
           throw new ServicioNoEncontradoException("El servicio no existe en el catalogo");
       }else {
           Integer cantAnterior = servicio.getCronogramaServicio().getCuposPorDia().get(fechaDeDisponibilidad);
           servicio.getCronogramaServicio().getCuposPorDia().put(fechaDeDisponibilidad, cantAnterior  + cantidadCupos);
       }
    }
    public void quitarServicioDisponible(ServicioTuristico servicio, Integer cantidadCupos,LocalDate fechaDeDisponibilidad){
        if(!servicios.contains(servicio)){
            throw new ServicioNoEncontradoException("El servicio no existe en el catalogo");
        } else if (servicio.getCronogramaServicio().getCuposPorDia().get(fechaDeDisponibilidad) < cantidadCupos) {
            throw new NoHayStockException("No hay suficiente cupo para quitar");
        } else {
            Integer cantAnterior = servicio.getCronogramaServicio().getCuposPorDia().get(fechaDeDisponibilidad);
            servicio.getCronogramaServicio().getCuposPorDia().put(fechaDeDisponibilidad, cantAnterior  - cantidadCupos);
        }
    }
    public boolean tieneStockParaReserva(ServicioTuristico servicio,Integer cantidadCupos,LocalDate fechaReserva){
        if (!servicios.contains(servicio)){
            throw new ServicioNoEncontradoException("El servicio no existe en el catalogo");
        } else {
            return servicio.getCronogramaServicio().getCuposPorDia().get(fechaReserva) >= cantidadCupos;
        }
    }
}
