package CatalogoTest;

import Clases.Catalogo;
import Clases.ServicioIndividual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static Clases.Categoria.ALOJAMIENTO;
import static Clases.Categoria.EXCURSIÓN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatalogoTest {
    ServicioIndividual servicio1;
    ServicioIndividual servicio2;
    ServicioIndividual servicio3;
    Catalogo catalogo;

    @BeforeEach
    void setUp(){
        servicio1 = new ServicioIndividual(123,"Noche De Hotel Central","noche para una persona",
                "proveedor",0.1f,2000f,ALOJAMIENTO);
        servicio2 = new ServicioIndividual(124,"Excursion museo","excursion guiada",
                "proveedor",0.0f,2200f,EXCURSIÓN);
        servicio3 = new ServicioIndividual(125,"alquiler de auto","auto alta gama",
                "proveedor",0.2f,2500f,EXCURSIÓN);
        catalogo = new Catalogo();
    }
    @Test
    void test001_servicioIndividualConDescuentoRetornaValoresCorrectos(){
        assertEquals(123,servicio1.getId());
        assertEquals("Noche De Hotel Central",servicio1.getNombre());
        assertEquals("noche para una persona",servicio1.getDescripcion());
        assertEquals("proveedor",servicio1.getProveedor());
        assertEquals(2000f,servicio1.getPrecioBase());
        assertEquals(ALOJAMIENTO,servicio1.getCategoria());
        assertEquals(0.1f,servicio1.getDescuento());
        assertEquals(1800f,servicio1.getPrecioFinal());

    }
    @Test
    void test002_servicioIndividualSinDescuentoRetornaValoresCorrectos(){
        assertEquals(124,servicio2.getId());
        assertEquals("Excursion museo",servicio2.getNombre());
        assertEquals("excursion guiada",servicio2.getDescripcion());
        assertEquals("proveedor",servicio2.getProveedor());
        assertEquals(2200f,servicio2.getPrecioBase());
        assertEquals(EXCURSIÓN,servicio2.getCategoria());
        assertEquals(0.0f,servicio2.getDescuento());
        assertEquals(2200f,servicio2.getPrecioFinal());

    }
}
