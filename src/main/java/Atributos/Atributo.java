package Atributos;

import lombok.Getter;

@Getter
public abstract class Atributo {
    private String nombre;
    private boolean obligatorio;
    private Object valor;

    public Atributo(String nombre, boolean obligatorio, Object valor) {
        this.nombre = nombre;
        this.valor = valor;
        this.obligatorio = obligatorio;
    }
    public boolean esValido(){
        if(valor == null){
            return !obligatorio;
        }
        return tipoCorrecto(valor);

    }
}
