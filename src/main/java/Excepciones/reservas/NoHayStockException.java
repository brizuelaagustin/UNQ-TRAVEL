package Excepciones.reservas;

public class NoHayStockException extends RuntimeException {
    public NoHayStockException(String message) {
        super(message);
    }
}
