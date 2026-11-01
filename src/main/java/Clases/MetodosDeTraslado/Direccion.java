package main.java.Clases.MetodosDeTraslado;

import lombok.Getter;

@Getter
public class Direccion {
    String calle;
    String ciudad;
    String provincia;
    String codigoPostal;


    public Direccion(String calle, String ciudad, String provincia, String codigoPostal) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.provincia = provincia;
        this.codigoPostal = codigoPostal;
    }
}
