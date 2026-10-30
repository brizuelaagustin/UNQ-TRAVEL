package Excepciones.reservas;

public class reservaCheckInAConfirmada extends RuntimeException{
    public reservaCheckInAConfirmada(String mensaje){
        super(mensaje);
    }

}
