package Atributos;

import lombok.Getter;

@Getter
public abstract class Atributo {
    private String nombre;
    private boolean obligatorio;
    private Object valor;

    public Atributo(String nombre, boolean obligatorio, Object valor) {
        this.nombre = nombre;
        this.valor = valor;//hacer validacion y si falla no se crea
        this.obligatorio = obligatorio;
    }
    public boolean esValido(){
        if(valor == null){
            return !obligatorio;
        }
        return tipoCorrecto(valor);

    }
    //en el getter tambien ver como se devuelve
    //en el setter haces la validacion y el constructor llama al setter
    protected abstract boolean tipoCorrecto(Object valor);
}
