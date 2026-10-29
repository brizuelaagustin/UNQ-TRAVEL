package Excepciones.reservas;

public class reservaPendienteAFinalizada extends RuntimeException{
    public reservaPendienteAFinalizada(String mensaje){
        super(mensaje);
    }

}
