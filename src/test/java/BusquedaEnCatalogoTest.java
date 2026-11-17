import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BusquedaEnCatalogo.*;
import CatalogoTuristico.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusquedaEnCatalogoTest {

    Catalogo catalogo;
    Criterio criterioPorNombre;
    Criterio criterioPorPrecioMaximo;
    Criterio criterioPorPrecioMaximo2;
    Criterio criterioPorCategoria;
    Criterio criterioPorCategoriaExcursion;
    Criterio criterioPorDisponibilidad;
    Criterio criterioAND;
    Criterio criterioNOT;
    Criterio criterioOR;
    Criterio criterioANDcompuesto;
    ServicioTuristico hotel;   // ALOJAMIENTO, $15.000, con cupo
    ServicioTuristico hotel2;  // ALQUILERAUTO $15.000, con cupo
    ServicioTuristico museo;   // EXCURSION, $4.000, sin cupo
    ServicioTuristico vuelo;   // VUELO, $80.000, con cupo
    ServicioTuristico pack;    // hotel + museo, 10% de descuento, no disponible (museo sin cupo)
    ServicioTuristico pack2;   // hotel + pack, 10% de descuento

    @BeforeEach
    void setUp() {
        CronogramaServicio cronogramaConCupos1 = new CronogramaServicio();
        cronogramaConCupos1.getCuposPorDia().put(LocalDate.of(2026, 12, 1), 5);

        CronogramaServicio cronogramaConCupos2 = new CronogramaServicio();
        cronogramaConCupos2.getCuposPorDia().put(LocalDate.of(2026, 12, 1), 3);

        CronogramaServicio cronogramaSinCupos = new CronogramaServicio();
        catalogo = new Catalogo();

        hotel = new ServicioIndividual(1, "Hotel Central", "Hotel en el centro", "Operador A",
                0f, 15000f, Categoria.ALOJAMIENTO, cronogramaConCupos1);
        museo = new ServicioIndividual(2, "Excursion Guiada Museo", "Visita guiada", "Operador B",
                0f, 4000f, Categoria.EXCURSION, cronogramaConCupos2);
        vuelo = new ServicioIndividual(3, "Vuelo Buenos Aires - Roma", "Vuelo directo", "Operador C",
                0f, 80000f, Categoria.VUELO, cronogramaConCupos1);
        hotel2 = new ServicioIndividual(1, "Hotel Central", "Hotel en el centro", "Operador A",
                0f, 15000f, Categoria.ALQUILERAUTO, cronogramaSinCupos);

        Map<ServicioTuristico, Integer> itemsPack = new HashMap<>();
        Map<ServicioTuristico, Integer> itemsKit = new HashMap<>();
        itemsPack.put(hotel2, 1);
        itemsPack.put(museo, 1);
        itemsKit.put(pack, 1);
        itemsKit.put(hotel, 1);
        pack = new PaqueteTuristico("Pack Escapada Urbana", "Hotel + museo", itemsPack, 0.1f, cronogramaConCupos1);
        pack2 = new PaqueteTuristico("Kit Gran Tour", "Pack + vuelo", itemsKit, 0.05f, cronogramaConCupos1);
        catalogo.getServicios().add(hotel);
        catalogo.getServicios().add(museo);
        catalogo.getServicios().add(vuelo);
        catalogo.getServicios().add(pack);

        criterioPorNombre = new CriterioNombre("hotel");
        criterioPorPrecioMaximo = new CriterioPrecioMaximo(15000f);
        criterioPorCategoria = new CriterioCategoria(Categoria.ALOJAMIENTO);
        criterioPorCategoriaExcursion = new CriterioCategoria(Categoria.EXCURSION);
        criterioPorDisponibilidad = new CriterioDisponibilidad();
        criterioAND = new CriterioAND(criterioPorCategoria, criterioPorDisponibilidad);
        criterioNOT = new CriterioNOT(criterioPorCategoriaExcursion);
        criterioOR = new CriterioOR(criterioPorNombre, new CriterioCategoria(Categoria.VUELO));
    }
    // Criterios Simples

    //  Criterio por nombre

    @Test
    void test002_CriterioPorNombreTiene1Servicio() {
        assertEquals(catalogo.buscar(criterioPorNombre).size(), 1);
    }

    @Test
    void test0003_CriterioPorNombreContieneAlHotel() {
        assertTrue(catalogo.buscar(criterioPorNombre).contains(hotel));
    }

    @Test
    void test004_CriterioPorNombreNoContieneAlMuseo() {
        assertFalse(catalogo.buscar(criterioPorNombre).contains(museo));
    }

    @Test
    void test005_CriterioPorNombreNoDistingueMayusculas() {
        Criterio mayusculas = new CriterioNombre("HOTEL");
        assertTrue(catalogo.buscar(mayusculas).contains(hotel));
    }

    //  Criterio por precio máximo

    @Test
    void test006_CriterioPorPrecioMaximoTiene2Servicios() {
        assertEquals(catalogo.buscar(criterioPorPrecioMaximo).size(), 2);
    }

    @Test
    void test007_CriterioPorPrecioMaximoContieneAlHotelQueEstaEnElLimite() {
        assertTrue(catalogo.buscar(criterioPorPrecioMaximo).contains(hotel));
    }

    @Test
    void test008_CriterioPorPrecioMaximoContieneAlMuseo() {
        assertTrue(catalogo.buscar(criterioPorPrecioMaximo).contains(museo));
    }

    @Test
    void test009_CriterioPorPrecioMaximoNoContieneAlPack() {
        assertFalse(catalogo.buscar(criterioPorPrecioMaximo).contains(pack));
    }

    @Test
    void test010_CriterioPorPrecioMaximo2EsVacio() {
        criterioPorPrecioMaximo2 = new CriterioPrecioMaximo(3000f);
        assertTrue(catalogo.buscar(criterioPorPrecioMaximo2).isEmpty());
    }

    //  Criterio por categoría

    @Test
    void test011_CriterioPorCategoriaTiene1Servicio() {
        assertEquals(1, catalogo.buscar(criterioPorCategoria).size());
    }

    @Test
    void test012_CriterioPorCategoriaContieneAlHotel() {
        assertTrue(catalogo.buscar(criterioPorCategoria).contains(hotel));
    }

    @Test
    void test013_CriterioPorCategoriaNoContieneAlMuseo() {
        assertFalse(catalogo.buscar(criterioPorCategoria).contains(museo));
    }


    //  Criterio por disponibilidad

    @Test
    void test014_CriterioPorDisponibilidadTiene2Servicios() {
        assertEquals(3, catalogo.buscar(criterioPorDisponibilidad).size());
    }

    @Test
    void test015_CriterioPorDisponibilidadContieneAlHotelYAlVuelo() {
        assertTrue(catalogo.buscar(criterioPorDisponibilidad).contains(hotel));
        assertTrue(catalogo.buscar(criterioPorDisponibilidad).contains(vuelo));
    }

    @Test
    void test016_CriterioPorDisponibilidadContieneAlMuseo() {
        assertTrue(catalogo.buscar(criterioPorDisponibilidad).contains(museo));
    }

    @Test
    void test017_CriterioPorDisponibilidadNoContieneAlPackPorqueElMuseoNoTieneCupo() {
        assertFalse(catalogo.buscar(criterioPorDisponibilidad).contains(pack));
    }

    // Criterios Complejos

    //  AND

    @Test
    void test018_CriterioANDTiene1Servicio() {
        assertEquals(1, catalogo.buscar(criterioAND).size());
    }

    @Test
    void test019_CriterioANDContieneAlHotel() {
        assertTrue(catalogo.buscar(criterioAND).contains(hotel));
    }

    @Test
    void test020_CriterioANDNoContieneAlPack() {
        assertFalse(catalogo.buscar(criterioAND).contains(pack));
    }

    //  NOT

    @Test
    void test021_CriterioNOTTiene2Servicios() {
        assertEquals(catalogo.buscar(criterioNOT).size(), 2);
    }

    @Test
    void test022_CriterioNOTContieneAlHotelYAlVuelo() {
        assertTrue(catalogo.buscar(criterioNOT).contains(hotel));
        assertTrue(catalogo.buscar(criterioNOT).contains(vuelo));
    }

    @Test
    void test023_CriterioNOTNoContieneAlMuseoNiAlPack() {
        assertFalse(catalogo.buscar(criterioNOT).contains(museo));
        assertFalse(catalogo.buscar(criterioNOT).contains(pack));
    }

    //  OR

    @Test
    void test024_CriterioORTiene2Servicios() {
        assertEquals(catalogo.buscar(criterioOR).size(), 2);
    }

    @Test
    void test025_CriterioORContieneAlHotelYAlVuelo() {
        assertTrue(catalogo.buscar(criterioOR).contains(hotel));
        assertTrue(catalogo.buscar(criterioOR).contains(vuelo));
    }

    @Test
    void test026_CriterioORNoContieneAlMuseo() {
        assertFalse(catalogo.buscar(criterioOR).contains(museo));
    }

    //  Criterios complejos mezclados

    @Test
    void test027_CriterioANDConOtrosCriteriosComplejos() {
        // NOT (precio <= 15000) AND (ALOJAMIENTO OR EXCURSION)
        criterioANDcompuesto = new CriterioAND(
                new CriterioNOT(criterioPorPrecioMaximo),
                new CriterioOR(criterioPorCategoria, criterioPorCategoriaExcursion));
        assertEquals(catalogo.buscar(criterioANDcompuesto).size(), 1);
        assertTrue(catalogo.buscar(criterioANDcompuesto).contains(pack));
        assertFalse(catalogo.buscar(criterioANDcompuesto).contains(hotel));
    }

    @Test
    void test028_CriterioNOTDeNOTEquivaleAlCriterioOriginal() {
        Criterio dobleNot = new CriterioNOT(new CriterioNOT(criterioPorCategoria));
        assertEquals(1, catalogo.buscar(dobleNot).size());
        assertTrue(catalogo.buscar(dobleNot).contains(hotel));
    }
}
