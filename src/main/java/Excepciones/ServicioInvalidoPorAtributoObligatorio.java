package Excepciones;

public class ServicioInvalidoPorAtributoObligatorio extends RuntimeException {
    public ServicioInvalidoPorAtributoObligatorio(String message) {
        super(message);
    }
}
