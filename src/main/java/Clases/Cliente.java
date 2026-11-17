package Clases;

import lombok.Getter;

@Getter
public class Cliente {
    private final String alias;
    private final long cbu;
    private String datosTarjeta;
    private String email;
    private float plata;

    public Cliente(String alias, long cbu, String datosTarjeta, String email, float plata) {
        this.alias = alias;
        this.cbu = cbu;
        this.datosTarjeta = datosTarjeta;
        this.email = email;
        this.plata = plata;
    }

    public void recuperarTasas(float tasa) {
        plata = plata + tasa;
    }
}
