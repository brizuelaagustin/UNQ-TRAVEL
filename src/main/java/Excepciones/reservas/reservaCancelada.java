package Excepciones.reservas;

public class reservaCancelada extends RuntimeException{
    public reservaCancelada(String mensaje){
        super(mensaje);
    }

}
