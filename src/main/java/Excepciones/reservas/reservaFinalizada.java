package Excepciones.reservas;

public class reservaFinalizada extends RuntimeException{
    public reservaFinalizada(String mensaje){
        super(mensaje);
    }

}
