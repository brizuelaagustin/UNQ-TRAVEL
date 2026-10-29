package CatalogoTest;

import Atributos.Atributo;
import Clases.Catalogo;
import Clases.PaqueteTuristico;
import Clases.ServicioIndividual;
import Clases.ServicioTuristico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogoTest {
    ServicioIndividual servicio1; // 2000, desc 10% (final 1800)
    ServicioIndividual servicio2; // 2200, sin descuento
    ServicioIndividual servicio3; // 2500, desc 20% (final 2000)
    ServicioIndividual servicio4; // 1000, sin descuento
    ServicioIndividual servicio5;
    Catalogo catalogo;
    Atributo atributo1;

    @BeforeEach
    void setUp() {
        servicio1 = new ServicioIndividual(123, "Noche De Hotel Central", "habitacion para una persona",
                "proveedor", 0.1f, 2000f, ALOJAMIENTO);
        servicio2 = new ServicioIndividual(124, "Excursion museo", "excursion guiada",
                "proveedor", 0.0f, 2200f, EXCURSIÓN);
        servicio3 = new ServicioIndividual(125, "alquiler de auto", "auto alta gama",
                "proveedor", 0.2f, 2500f, EXCURSIÓN);
        servicio4 = new ServicioIndividual(126, "traslado aeropueRto", "traslado larga distancia",
                "proveedor", 0.0f, 1000f, ALQUILERAUTO);
        servicio5 = new ServicioIndividual(null,null,"suite para 2 personas","proveedor",
                0.0f,1000f,ALOJAMIENTO);
        catalogo = new Catalogo();
        atributo1 = new Atributo("capacidadMaxima",true,10);

    }
    // TEST PRODUCTOS INDIVIDUALES

    @Test
    void test001_servicioIndividualConDescuentoRetornaValoresCorrectos() {
        assertEquals(123, servicio1.getId());
        assertEquals("Noche De Hotel Central", servicio1.getNombre());
        assertEquals("habitacion para una persona", servicio1.getDescripcion());
        assertEquals("proveedor", servicio1.getProveedor());
        assertEquals(2000f, servicio1.getPrecioBase());
        assertEquals(ALOJAMIENTO, servicio1.getCategoria());
        assertEquals(0.1f, servicio1.getDescuento());
        assertEquals(1800f, servicio1.getPrecioFinal());

    }

    @Test
    void test002_servicioIndividualSinDescuentoRetornaValoresCorrectos() {
        assertEquals(124, servicio2.getId());
        assertEquals("Excursion museo", servicio2.getNombre());
        assertEquals("excursion guiada", servicio2.getDescripcion());
        assertEquals("proveedor", servicio2.getProveedor());
        assertEquals(2200f, servicio2.getPrecioBase());
        assertEquals(EXCURSIÓN, servicio2.getCategoria());
        assertEquals(0.0f, servicio2.getDescuento());
        assertEquals(2200f, servicio2.getPrecioFinal());

    }
    // TEST PAQUETES SIMPLES

    @Test
    void test003_PaqueteConDescuentoConDosServiciosSinDescuento() {
        ServicioTuristico paquete1 = new PaqueteTuristico("Paquete Historia",
                "Excursion museo y translado aeoropuerto", Map.of(servicio2, 1, servicio4, 1), 0.1f);
        assertEquals(3200, paquete1.getPrecioBase());
        assertEquals(2880, paquete1.getPrecioFinal());
    }

    @Test
    void test004_PaqueteConDescuentoConDosServiciosConDescuento() {
        ServicioTuristico paquete2 = new PaqueteTuristico(
                "Noche De Hotel Central", "habitacion para 2 personas", Map.of(servicio1, 2), 0.2f);
        assertEquals(3600, paquete2.getPrecioBase());
        assertEquals(2880, paquete2.getPrecioFinal());
    }

    @Test
    void test005_paqueteConDescuentoConServicioSinDescuentoYServicioConDescuento() {
        ServicioTuristico paquete3 = new PaqueteTuristico("Hotel y museo",
                "noche de hotel y excursion", Map.of(servicio2, 1, servicio1, 1), 0.1f);
        assertEquals(4000f, paquete3.getPrecioBase());
        assertEquals(3600f, paquete3.getPrecioFinal());
    }
    // TESTS DE PAQUETES ANIDADOS (paquetes que contienen otros paquetes)


    @Test
    void test006_paqueteConDescuentoQueContieneOtroPaqueteConDescuento() {
        ServicioTuristico paquete4 = new PaqueteTuristico("Paquete Historia",
                "museo y traslado", Map.of(servicio2, 1, servicio4, 1), 0.1f);
        ServicioTuristico paquete5 = new PaqueteTuristico("Historia y hotel",
                "paquete historia y noche de hotel", Map.of(paquete4, 1, servicio1, 1), 0.2f);
        assertEquals(3744f, paquete5.getPrecioFinal());
        assertEquals(4680f, paquete5.getPrecioBase());
    }

    @Test
    void test007_paqueteConDescuentoQueContieneOtroPaqueteSinDescuento() {
        ServicioTuristico paquete6 = new PaqueteTuristico("Paquete Historia",
                "museo y traslado", Map.of(servicio2, 1, servicio4, 1), 0.0f);   // 3200
        ServicioTuristico paquete7 = new PaqueteTuristico("Historia y hotel",
                "paquete historia y noche de hotel", Map.of(paquete6, 1, servicio1, 1), 0.1f);
        assertEquals(5000f, paquete7.getPrecioBase());
        assertEquals(4500f, paquete7.getPrecioFinal());
    }

    // TEST ATRIBUTOS EXTRA
    @Test
    void test008_seAgreganAtributosExtraAUnServicioIndividualYRetornaValoresCorrectos(){
        servicio1.agregarAtributoExtra(atributo1);
        assertEquals("capacidadMaxima",atributo1.getNombre());
        assertTrue(atributo1.isObligatorio());
    }
    @Test
    void test009seValidaUnServicioQueNoTieneAtributosExtra(){
        assertTrue(servicio2.getNombre() != null);
        assertTrue(servicio2.getId() != null);
    }

    @Test
    void test010seIntentaValidarUnServicioQueNoTieneAtributosExtra(){
        assertThrows(RuntimeException.class, () -> { servicio5.validarAtributos();});
    }
//   @Test
//    void test010seValidaUnServicioQueTieneAtributosExtra(){
//      servicio4.agregarAtributoExtra(Atributo,true,30);
//       servicio4.validarAtributos();
//   }

}