package Clases;

import lombok.Getter;

@Getter
public class Cliente {
    private final String alias;
    private final long cbu;
    private String datosTarjeta;
    private String email;

    public Cliente(String alias, long cbu, String datosTarjeta, String email) {
        this.alias = alias;
        this.cbu = cbu;
        this.datosTarjeta = datosTarjeta;
        this.email = email;
    }
}
