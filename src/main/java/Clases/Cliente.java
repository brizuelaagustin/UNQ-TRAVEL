package Clases;

public class Cliente {
    private final String alias;
    private final long cbu;
    private String datosTarjeta;

    public Cliente(String alias, long cbu, String datosTarjeta) {
        this.alias = alias;
        this.cbu = cbu;
        this.datosTarjeta = datosTarjeta;
    }
}
