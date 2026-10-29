package Clases;

import lombok.Getter;

@Getter
public abstract class ServicioTuristico {
    public String nombre;
    public String descripcion;

    public abstract float getPrecioBase();

}
