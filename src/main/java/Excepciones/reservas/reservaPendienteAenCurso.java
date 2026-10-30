package Excepciones.reservas;

public class reservaPendienteAenCurso extends RuntimeException{
    public reservaPendienteAenCurso(String mensaje){
        super(mensaje);
    }

}
