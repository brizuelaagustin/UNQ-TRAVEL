package Clases;

import EstadosDeReserva.EstadoDeReserva;

import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private EstadoDeReserva estadoDeReserva;
    private EstrategiaDeTraslado estrategiaTraslado;
    private List<ServicioTuristico> servicios = new ArrayList<>();
    private int cuposDisponibles;
    private int tasaDeReserva;
    private int cantidadPasajeros;
    private Destino direccionDestino;
    private MetodoDePago metodoDePago;



//Metodos
    //como es eso del new?
//    public void confirmarReserva(){}
//    public void cancelarReserva(){}
//    public void checkInReserva(){}
//    public void enCursoReserva(){}
//    public void finalizarReserva(){}
//    public void agregarItem(Catalogo catalogo){}
//    public void quitarItem(Catalogo catalogo){}
//    public void setEstrategia(MetodoDeTraslado metodoT){}
    //no hay que hacer esto en vez de tener todos los metodos de arriba? porque lo de arriba lo maneja el state no la reserva
      public void actualizarReserva(EstadoDeReserva estado){
          this.estadoDeReserva = estado;
      }

}
