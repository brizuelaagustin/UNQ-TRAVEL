package Atributos;

public class AtributoNumerico extends Atributo{
    public AtributoNumerico(String nombre, boolean obligatorio, Object valor) {
        super(nombre,obligatorio,valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) {
        return valor instanceof Number;
    }
}
