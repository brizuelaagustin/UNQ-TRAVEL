package Excepciones.reservas;

public class reservaConfirmadaAFinalizada extends RuntimeException{
    public reservaConfirmadaAFinalizada(String mensaje){
        super(mensaje);
    }

}
