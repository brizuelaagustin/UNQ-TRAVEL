package Excepciones.reservas;

public class reservaEnCursoACheckIn extends RuntimeException{
    public reservaEnCursoACheckIn(String mensaje){
        super(mensaje);
    }

}
