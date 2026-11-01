package main.java.Clases.CatalogoTuristico;

import lombok.Getter;

@Getter
public abstract class ServicioTuristico {
    public String nombre;
    public String descripcion;

    public abstract float getPrecioBase();
    public abstract float getPrecioFinal();

}
