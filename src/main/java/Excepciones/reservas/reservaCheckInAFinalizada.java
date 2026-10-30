package Excepciones.reservas;

public class reservaCheckInAFinalizada extends RuntimeException{
    public reservaCheckInAFinalizada(String mensaje){
        super(mensaje);
    }

}
