package Excepciones.reservas;

public class MetodoDeTrasladoNoDisponibleException extends RuntimeException{
    public MetodoDeTrasladoNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
