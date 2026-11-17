import Clases.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClasesTest {
    @Test
    void test01_Cliente() {
        Cliente cliente = new Cliente(
                "micaela120", 278976543L, "micaela lopez ,23456778,970,07/27", "mica@gmail.com", 500);
        assertEquals("micaela120", cliente.getAlias());
        assertEquals(278976543L, cliente.getCbu());
        assertEquals("mica@gmail.com", cliente.getEmail());
        assertEquals("micaela lopez ,23456778,970,07/27", cliente.getDatosTarjeta());
    }
    @Test
    void test02_UnClientePuedeRecuperarTasas() {
        Cliente cliente = new Cliente("vale345", 278976543L, "valeria barsotti ,328948914,567,11/15", "vale@gmail.com", 500);
        cliente.recuperarTasas(200);
        assertEquals(700, cliente.getPlata());
    }
}
