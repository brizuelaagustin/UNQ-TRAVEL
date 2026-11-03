import CatalogoTuristico.Catalogo;
import CatalogoTuristico.ServicioTuristico;
import Clases.Cliente;
import Clases.Reserva;
import EstadosDeReserva.*;
import MetodoDePago.MetodoDePago;
import MetodosDeTraslado.Direccion;
import MetodosDeTraslado.MetodoDeTraslado;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

public class EstadosDeReservaTest {

//hay que hacer mockito
    Reserva reserva;
    EstadoDeReserva estado;
    MetodoDeTraslado metodoTraslado;
    Direccion direccion;
    MetodoDePago metodoPago;
    Catalogo catalogo;
    Cliente cliente;

    EstadoDeReserva estadoPendiente;
    EstadoDeReserva estadoConfirmado;
    EstadoDeReserva estadoCheckIn;
    EstadoDeReserva estadoEnCurso;
    EstadoDeReserva estadoFinalizado;
    EstadoDeReserva estadoCancelado;

    @BeforeEach
    void setup(){
        estado=         mock(EstadoDeReserva.class);
        metodoTraslado= mock(MetodoDeTraslado.class);
        direccion=      mock(Direccion.class);
        metodoPago=     mock(MetodoDePago.class);
        catalogo=       mock(Catalogo.class);
        cliente=        mock(Cliente.class);
        reserva= new Reserva(estado, metodoTraslado, 10, 20, 5, direccion, metodoPago, catalogo, cliente);

        estadoPendiente=    new Pendiente();
        estadoConfirmado=   new Confirmada();
        estadoCheckIn=      new CheckIn();
        estadoEnCurso=      new EnCurso();
        estadoFinalizado=   new Finalizada();
        estadoCancelado=    new Cancelada();
    }




    //sintacticamente como testeo que se delega en el estado?


}
