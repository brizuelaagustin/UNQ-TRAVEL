package Atributos;

public class AtributoNumerico extends Atributo{
    public AtributoNumerico(String nombre, boolean obligatorio, Object valor) {
        super(nombre,obligatorio,valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) {
        return valor Integer.valueOf(valor) Number; //algo que dijo el profe
    }
}
