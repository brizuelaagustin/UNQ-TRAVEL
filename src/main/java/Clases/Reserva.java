package Clases;

import CatalogoTuristico.ServicioTuristico;
import Excepciones.reservas.NoHayStockException;
import Excepciones.reservas.ServicioNoEncontradoException;
import MetodoDePago.MetodoDePago;
import EstadosDeReserva.EstadoDeReserva;
import NotificacionesDeReserva.NotificadorDeEmail;
import NotificacionesDeReserva.NotificadorDeReserva;
import lombok.Getter;
import MetodosDeTraslado.Direccion;
import MetodosDeTraslado.MetodoDeTraslado;

import CatalogoTuristico.Catalogo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Reserva {
    private EstadoDeReserva estadoDeReserva;
    private MetodoDeTraslado estrategiaTraslado;
    private Map<ServicioTuristico,Integer> servicios;
    private Integer cantidadPasajeros;
    private Direccion direccionDestino;
    private MetodoDePago metodoDePago;
    private Catalogo catalogo;
    private Cliente cliente;
    private NotificadorDeReserva notificadorDeReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    //Constructor
    public Reserva(EstadoDeReserva reserva, MetodoDeTraslado traslado, Integer cantPasajeros,
                   Direccion direccion, MetodoDePago metodoPago, Catalogo catalogo, Cliente cliente, NotificadorDeReserva notificadorDeReserva){
        this.estadoDeReserva=reserva;
        this.estrategiaTraslado=traslado;
        this.servicios=new HashMap<>();
        this.cantidadPasajeros=cantPasajeros;
        this.direccionDestino=direccion;
        this.metodoDePago=metodoPago;
        this.catalogo=catalogo;
        this.cliente=cliente;
        this.notificadorDeReserva = notificadorDeReserva;
    }

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

    public void setEstrategia(MetodoDeTraslado metodoT){
        this.estrategiaTraslado = metodoT;
    }

    public float getPrecio(){
        float total = 0;
        for(Map.Entry<ServicioTuristico,Integer> s: servicios.entrySet()){
            total += s.getKey().getPrecioFinal() * s.getValue();
        }
        return total;
    }

    public void actualizarReserva(EstadoDeReserva nuevoEstado){
        estadoDeReserva = nuevoEstado;
    }

    public void agregarServicio(ServicioTuristico servicio,Integer cantidad){
        estadoDeReserva.agregarServicio(this,servicio,cantidad);
    }

    public void incluirServicio(ServicioTuristico servicio, Integer cantidad){
        if(!(catalogo.getServicios().contains(servicio))){
            throw new ServicioNoEncontradoException("No se puede agregar, porque no existe en el catalogo");
        }
        if(!catalogo.tieneStockParaReserva(servicio,cantidad)) {
            throw new NoHayStockException("No se pueden agregar, no hay stock suficiente");
        }
        int cantidadActual = catalogo.getServiciosDisponibles().getOrDefault(servicio, 0);
        servicios.put(servicio,cantidadActual + cantidad);
    }
    public void quitarServicio(ServicioTuristico servicio , Integer cantidad){
        estadoDeReserva.quitarServicio(this, servicio, cantidad);
    }

    public void removerServicio(ServicioTuristico servicio, Integer cantidad){
        if(! servicios.containsKey(servicio)){
            throw  new ServicioNoEncontradoException("No se puede remover un servicio que no esta en la reserva");
        }
        int cantidadActual = servicios.getOrDefault(servicio,0);
        if(cantidadActual > cantidad){
            servicios.put(servicio, cantidadActual - cantidad);
        }else{
            throw new NoHayStockException("No hay esa cantidad de servicios suficientes para remover");
        }
    }
}
