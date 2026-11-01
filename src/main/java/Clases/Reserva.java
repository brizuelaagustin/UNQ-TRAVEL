package main.java.Clases;

import EstadosDeReserva.EstadoDeReserva;
import lombok.Getter;
import main.java.Clases.MetodosDeTraslado.Direccion;
import main.java.Clases.MetodosDeTraslado.MetodoDeTraslado;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Reserva {
    private EstadoDeReserva estadoDeReserva;
    private MetodoDeTraslado estrategiaTraslado;
    private List<ServicioTuristico> servicios = new ArrayList<>();
    private Integer cuposDisponibles;
    private Integer tasaDeReserva;
    private Integer cantidadPasajeros;
    private Direccion direccionDestino;
    private MetodoDePago metodoDePago;
    private Catalogo catalogo;

    public void confirmarReserva(){
        estadoDeReserva.confirmarReserva(this);
    }

    public void cancelarReserva(){
        estadoDeReserva.cancelarReserva(this);
    }

    public void checkInReserva(){
        estadoDeReserva.checkInReserva(this);
    }

    public void enCursoReserva(){
        estadoDeReserva.enCursoReserva(this);
    }

    public void finalizarReserva(){
        estadoDeReserva.finalizarReserva(this);
    }

//    public void agregarItem(Catalogo catalogo,ServicioTuristico servicio){
//
//    }
//    public void quitarItem(Catalogo catalogo,ServicioTuristico servicio){
//
//    }
    public void setEstrategia(MetodoDeTraslado metodoT){
        this.estrategiaTraslado = metodoT;
    }

    public float getPrecio(){
        float total = 0;
        for(ServicioTuristico s: servicios){
            total += s.getPrecioFinal();
        }
        return total;
    }


    public void actualizarReserva(EstadoDeReserva nuevoEstado){
          EstadoDeReserva estadoAnterior = estadoDeReserva;
          estadoDeReserva = nuevoEstado;
    }
//    public void agregarServicio(ServicioTuristico servicio,Integer cantidad){
//        if(!catalogo.getServicios().contains(servicio)){
//            throw new ProductoNoEncontradoException("No se puede agregar, porque no existe en el catalogo");
//        }
//        if(!catalogo.tieneStockParaReserva(servicio,cantidad)){
//            throw new NoHayStockException("No se pueden agregar, no hay stock suficiente");
//        }
//        int cantidadActual = servicios.getOrDefault(servicio, 0);
//        carritoDeProductos.put(producto, cantidadActual + cantProducto);
//    }
// ACA NO SE COMO MODELARLO, ME GENERO DUDA SI MODELAMOS CON UNA LISTA LOS SERVICIOS EN RESERVA O TAMBIEN TIENEN QUE SER UN MAP
}
