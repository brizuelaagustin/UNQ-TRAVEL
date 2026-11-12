import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BusquedaEnCatalogo.*;
import BusquedaEnCatalogo.CriteriosSimples.*;
import BusquedaEnCatalogo.CriteriosComplejos.*;
import CatalogoTuristico.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BusquedaEnCatalogoTest {

    BusquedaEnCatalogo buscador;
    List<ServicioTuristico> servicios;
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
        servicios = new ArrayList<>();
        servicios.add(hotel);
        servicios.add(museo);
        servicios.add(vuelo);
        servicios.add(pack);

        criterioPorNombre = new CriterioNombre("hotel");
        criterioPorPrecioMaximo = new CriterioPrecioMaximo(15000f);
        criterioPorCategoria = new CriterioCategoria(Categoria.ALOJAMIENTO);
        criterioPorCategoriaExcursion = new CriterioCategoria(Categoria.EXCURSION);
        criterioPorDisponibilidad = new CriterioDisponibilidad();
        criterioAND = new CriterioAND(criterioPorCategoria, criterioPorDisponibilidad);
        criterioNOT = new CriterioNOT(criterioPorCategoriaExcursion);
        criterioOR = new CriterioOR(criterioPorNombre, new CriterioCategoria(Categoria.VUELO));
        buscador = new BusquedaEnCatalogo(servicios);
    }
    // Criterios Simples

    //  Criterio por nombre

    @Test
    void test002_CriterioPorNombreTiene1Servicio() {
        assertEquals(buscador.buscar(criterioPorNombre).size(), 1);
    }

    @Test
    void test0003_CriterioPorNombreContieneAlHotel() {
        assertTrue(buscador.buscar(criterioPorNombre).contains(hotel));
    }

    @Test
    void test004_CriterioPorNombreNoContieneAlMuseo() {
        assertFalse(buscador.buscar(criterioPorNombre).contains(museo));
    }

    @Test
    void test005_CriterioPorNombreNoDistingueMayusculas() {
        Criterio mayusculas = new CriterioNombre("HOTEL");
        assertTrue(buscador.buscar(mayusculas).contains(hotel));
    }

    //  Criterio por precio máximo

    @Test
    void test006_CriterioPorPrecioMaximoTiene2Servicios() {
        assertEquals(buscador.buscar(criterioPorPrecioMaximo).size(), 2);
    }

    @Test
    void test007_CriterioPorPrecioMaximoContieneAlHotelQueEstaEnElLimite() {
        assertTrue(buscador.buscar(criterioPorPrecioMaximo).contains(hotel));
    }

    @Test
    void test008_CriterioPorPrecioMaximoContieneAlMuseo() {
        assertTrue(buscador.buscar(criterioPorPrecioMaximo).contains(museo));
    }

    @Test
    void test009_CriterioPorPrecioMaximoNoContieneAlPack() {
        assertFalse(buscador.buscar(criterioPorPrecioMaximo).contains(pack));
    }

    @Test
    void test010_CriterioPorPrecioMaximo2EsVacio() {
        criterioPorPrecioMaximo2 = new CriterioPrecioMaximo(3000f);
        assertTrue(buscador.buscar(criterioPorPrecioMaximo2).isEmpty());
    }

    //  Criterio por categoría

    @Test
    void test011_CriterioPorCategoriaTiene1Servicio() {
        assertEquals(1, buscador.buscar(criterioPorCategoria).size());
    }

    @Test
    void test012_CriterioPorCategoriaContieneAlHotel() {
        assertTrue(buscador.buscar(criterioPorCategoria).contains(hotel));
    }

    @Test
    void test013_CriterioPorCategoriaNoContieneAlMuseo() {
        assertFalse(buscador.buscar(criterioPorCategoria).contains(museo));
    }


    //  Criterio por disponibilidad

    @Test
    void test014_CriterioPorDisponibilidadTiene2Servicios() {
        assertEquals(3, buscador.buscar(criterioPorDisponibilidad).size());
    }

    @Test
    void test015_CriterioPorDisponibilidadContieneAlHotelYAlVuelo() {
        assertTrue(buscador.buscar(criterioPorDisponibilidad).contains(hotel));
        assertTrue(buscador.buscar(criterioPorDisponibilidad).contains(vuelo));
    }

    @Test
    void test016_CriterioPorDisponibilidadContieneAlMuseo() {
        assertTrue(buscador.buscar(criterioPorDisponibilidad).contains(museo));
    }

    @Test
    void test017_CriterioPorDisponibilidadNoContieneAlPackPorqueElMuseoNoTieneCupo() {
        assertFalse(buscador.buscar(criterioPorDisponibilidad).contains(pack));
    }


    // Criterios Complejos

    //  AND

    @Test
    void test018_CriterioANDTiene1Servicio() {
        assertEquals(1, buscador.buscar(criterioAND).size());
    }

    @Test
    void test019_CriterioANDContieneAlHotel() {
        assertTrue(buscador.buscar(criterioAND).contains(hotel));
    }

    @Test
    void test020_CriterioANDNoContieneAlPack() {
        assertFalse(buscador.buscar(criterioAND).contains(pack));
    }

    //  NOT

    @Test
    void test021_CriterioNOTTiene2Servicios() {
        assertEquals(buscador.buscar(criterioNOT).size(), 2);
    }

    @Test
    void test022_CriterioNOTContieneAlHotelYAlVuelo() {
        assertTrue(buscador.buscar(criterioNOT).contains(hotel));
        assertTrue(buscador.buscar(criterioNOT).contains(vuelo));
    }

    @Test
    void test023_CriterioNOTNoContieneAlMuseoNiAlPack() {
        assertFalse(buscador.buscar(criterioNOT).contains(museo));
        assertFalse(buscador.buscar(criterioNOT).contains(pack));
    }

    //  OR

    @Test
    void test024_CriterioORTiene2Servicios() {
        assertEquals(buscador.buscar(criterioOR).size(), 2);
    }

    @Test
    void test025_CriterioORContieneAlHotelYAlVuelo() {
        assertTrue(buscador.buscar(criterioOR).contains(hotel));
        assertTrue(buscador.buscar(criterioOR).contains(vuelo));
    }

    @Test
    void test026_CriterioORNoContieneAlMuseo() {
        assertFalse(buscador.buscar(criterioOR).contains(museo));
    }

    //  Criterios complejos mezclados

    @Test
    void test027_CriterioANDConOtrosCriteriosComplejos() {
        // NOT (precio <= 15000) AND (ALOJAMIENTO OR EXCURSION)
        criterioANDcompuesto = new CriterioAND(
                new CriterioNOT(criterioPorPrecioMaximo),
                new CriterioOR(criterioPorCategoria, criterioPorCategoriaExcursion));
        assertEquals(buscador.buscar(criterioANDcompuesto).size(), 1);
        assertTrue(buscador.buscar(criterioANDcompuesto).contains(pack));
        assertFalse(buscador.buscar(criterioANDcompuesto).contains(hotel));
    }

    @Test
    void test028_CriterioNOTDeNOTEquivaleAlCriterioOriginal() {
        Criterio dobleNot = new CriterioNOT(new CriterioNOT(criterioPorCategoria));
        assertEquals(1, buscador.buscar(dobleNot).size());
        assertTrue(buscador.buscar(dobleNot).contains(hotel));
    }
}
