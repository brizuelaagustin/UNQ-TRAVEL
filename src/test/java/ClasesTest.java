import Clases.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClasesTest {
    @Test
    void test01_Cliente() {
        Cliente cliente = new Cliente(
                "micaela120", 278976543L, "micaela lopez ,23456778,970,07/27", "mica@gmail.com");
        assertEquals("micaela120", cliente.getAlias());
        assertEquals(278976543L, cliente.getCbu());
        assertEquals("mica@gmail.com", cliente.getEmail());
        assertEquals("micaela lopez ,23456778,970,07/27", cliente.getDatosTarjeta());
    }
}
