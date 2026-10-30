package Excepciones.reservas;

public class reservaPendienteACheckIn extends RuntimeException{
    public reservaPendienteACheckIn(String mensaje){
        super(mensaje);
    }

}
