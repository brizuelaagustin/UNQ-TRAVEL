package Atributos;

public class AtributoTipoBooleano extends Atributo {
    public AtributoTipoBooleano(String nombre, boolean obligatrio, Object valor) {
        super(nombre, obligatrio, valor);
    }

    @Override
    protected boolean tipoCorrecto(Object valor) {
        return valor instanceof  Boolean;
    }
}
