package Clases;

import lombok.Getter;

@Getter
public class Atributo {
    private String nombre;
    private boolean obligatorio;
    private Object valor;

    public Atributo(String nombre, boolean obligatrio, Object valor) {
        this.nombre = nombre;
        this.valor = valor;
        this.obligatorio = obligatrio;
    }
    public boolean esValido(){
        return valor !=null;

    }
}
