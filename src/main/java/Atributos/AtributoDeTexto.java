package Atributos;

public class AtributoDeTexto extends Atributo {
    public AtributoDeTexto(String nombre, boolean obligatrio, Object valor) {
        super(nombre, obligatrio, valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) {
            return valor instanceof String;
    }
}
