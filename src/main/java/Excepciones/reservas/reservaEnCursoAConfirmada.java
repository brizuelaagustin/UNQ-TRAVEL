package Excepciones.reservas;

public class reservaEnCursoAConfirmada extends RuntimeException{
    public reservaEnCursoAConfirmada(String mensaje){
        super(mensaje);
    }

}
