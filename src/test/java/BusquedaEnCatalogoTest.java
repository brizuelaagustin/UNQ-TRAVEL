import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
    ServicioTuristico museo;   // EXCURSION, $4.000, sin cupo
    ServicioTuristico vuelo;   // VUELO, $80.000, con cupo
    ServicioTuristico pack;    // hotel + museo, 10% de descuento, no disponible (museo sin cupo)
    CronogramaServicio cronogramaServicio;

    @BeforeEach
    void setUp() {
        hotel = new ServicioIndividual(1, "Hotel Central", "Hotel en el centro", "Operador A",
                0f, 15000f, Categoria.ALOJAMIENTO, cronogramaServicio);
        museo = new ServicioIndividual(2, "Excursion Guiada Museo", "Visita guiada", "Operador B",
                0f, 4000f, Categoria.EXCURSION, cronogramaServicio);
        vuelo = new ServicioIndividual(3, "Vuelo Buenos Aires - Roma", "Vuelo directo", "Operador C",
                0f, 80000f, Categoria.VUELO, cronogramaServicio);

        Map<ServicioTuristico, Integer> itemsPack = new HashMap<>();
        itemsPack.put(hotel, 1);
        itemsPack.put(museo, 1);
        pack = new PaqueteTuristico("Pack Escapada Urbana", "Hotel + museo", itemsPack, 0.1f, cronogramaServicio);

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
        cronogramaServicio = mock(CronogramaServicio.class);
    }
    // Criterios Simples

    //  Criterio por nombre

    @Test
    void testCriterioPorNombreTiene1Servicio() {
        assertEquals(buscador.buscar(criterioPorNombre).size(), 1);
    }

    @Test
    void testCriterioPorNombreContieneAlHotel() {
        assertTrue(buscador.buscar(criterioPorNombre).contains(hotel));
    }

    @Test
    void testCriterioPorNombreNoContieneAlMuseo() {
        assertFalse(buscador.buscar(criterioPorNombre).contains(museo));
    }

    @Test
    void testCriterioPorNombreNoDistingueMayusculas() {
        Criterio mayusculas = new CriterioNombre("HOTEL");
        assertTrue(buscador.buscar(mayusculas).contains(hotel));
    }

    //  Criterio por precio máximo

    @Test
    void testCriterioPorPrecioMaximoTiene2Servicios() {
        assertEquals(buscador.buscar(criterioPorPrecioMaximo).size(), 2);
    }

    @Test
    void testCriterioPorPrecioMaximoContieneAlHotelQueEstaEnElLimite() {
        assertTrue(buscador.buscar(criterioPorPrecioMaximo).contains(hotel));
    }

    @Test
    void testCriterioPorPrecioMaximoContieneAlMuseo() {
        assertTrue(buscador.buscar(criterioPorPrecioMaximo).contains(museo));
    }

    @Test
    void testCriterioPorPrecioMaximoNoContieneAlPack() {
        assertFalse(buscador.buscar(criterioPorPrecioMaximo).contains(pack));
    }

    @Test
    void testCriterioPorPrecioMaximo2EsVacio() {
        criterioPorPrecioMaximo2 = new CriterioPrecioMaximo(3000f);
        assertTrue(buscador.buscar(criterioPorPrecioMaximo2).isEmpty());
    }

    //  Criterio por categoría

    @Test
    void testCriterioPorCategoriaTiene2Servicios() {
        assertEquals(buscador.buscar(criterioPorCategoria).size(), 2);
    }

    @Test
    void testCriterioPorCategoriaContieneAlHotelYAlPack() {
        assertTrue(buscador.buscar(criterioPorCategoria).contains(hotel));
        assertTrue(buscador.buscar(criterioPorCategoria).contains(pack));
    }

    @Test
    void testCriterioPorCategoriaNoContieneAlMuseo() {
        assertFalse(buscador.buscar(criterioPorCategoria).contains(museo));
    }

    @Test
    void testCriterioPorCategoriaFuncionaConPaquetesAnidados() {
        Map<ServicioTuristico, Integer> itemsKit = new HashMap<>();
        itemsKit.put(pack, 1);
        itemsKit.put(vuelo, 1);
        ServicioTuristico kit = new PaqueteTuristico("Kit Gran Tour", "Pack + vuelo", itemsKit, 0.05f, cronogramaServicio);
        BusquedaEnCatalogo otroBuscador = new BusquedaEnCatalogo(List.of(kit));

        assertTrue(otroBuscador.buscar(criterioPorCategoria).contains(kit));
    }

    //  Criterio por disponibilidad

    @Test
    void testCriterioPorDisponibilidadTiene2Servicios() {
        assertEquals(buscador.buscar(criterioPorDisponibilidad).size(), 2);
    }

    @Test
    void testCriterioPorDisponibilidadContieneAlHotelYAlVuelo() {
        assertTrue(buscador.buscar(criterioPorDisponibilidad).contains(hotel));
        assertTrue(buscador.buscar(criterioPorDisponibilidad).contains(vuelo));
    }

    @Test
    void testCriterioPorDisponibilidadNoContieneAlMuseo() {
        assertFalse(buscador.buscar(criterioPorDisponibilidad).contains(museo));
    }

    @Test
    void testCriterioPorDisponibilidadNoContieneAlPackPorqueElMuseoNoTieneCupo() {
        assertFalse(buscador.buscar(criterioPorDisponibilidad).contains(pack));
    }
    // Criterios Complejos

    //  AND

    @Test
    void testCriterioANDTiene1Servicio() {
        assertEquals(buscador.buscar(criterioAND).size(), 1);
    }

    @Test
    void testCriterioANDContieneAlHotel() {
        assertTrue(buscador.buscar(criterioAND).contains(hotel));
    }

    @Test
    void testCriterioANDNoContieneAlPack() {
        assertFalse(buscador.buscar(criterioAND).contains(pack));
    }

    //  NOT

    @Test
    void testCriterioNOTTiene2Servicios() {
        assertEquals(buscador.buscar(criterioNOT).size(), 2);
    }

    @Test
    void testCriterioNOTContieneAlHotelYAlVuelo() {
        assertTrue(buscador.buscar(criterioNOT).contains(hotel));
        assertTrue(buscador.buscar(criterioNOT).contains(vuelo));
    }

    @Test
    void testCriterioNOTNoContieneAlMuseoNiAlPack() {
        assertFalse(buscador.buscar(criterioNOT).contains(museo));
        assertFalse(buscador.buscar(criterioNOT).contains(pack));
    }

    //  OR

    @Test
    void testCriterioORTiene2Servicios() {
        assertEquals(buscador.buscar(criterioOR).size(), 2);
    }

    @Test
    void testCriterioORContieneAlHotelYAlVuelo() {
        assertTrue(buscador.buscar(criterioOR).contains(hotel));
        assertTrue(buscador.buscar(criterioOR).contains(vuelo));
    }

    @Test
    void testCriterioORNoContieneAlMuseo() {
        assertFalse(buscador.buscar(criterioOR).contains(museo));
    }

    //  Criterios complejos mezclados

    @Test
    void testCriterioANDConOtrosCriteriosComplejos() {
        // NOT (precio <= 15000) AND (ALOJAMIENTO OR EXCURSION)
        criterioANDcompuesto = new CriterioAND(
                new CriterioNOT(criterioPorPrecioMaximo),
                new CriterioOR(criterioPorCategoria, criterioPorCategoriaExcursion));
        assertEquals(buscador.buscar(criterioANDcompuesto).size(), 1);
        assertTrue(buscador.buscar(criterioANDcompuesto).contains(pack));
        assertFalse(buscador.buscar(criterioANDcompuesto).contains(hotel));
    }

    @Test
    void testCriterioNOTDeNOTEquivaleAlCriterioOriginal() {
        Criterio dobleNot = new CriterioNOT(new CriterioNOT(criterioPorCategoria));
        assertEquals(buscador.buscar(dobleNot).size(), 2);
        assertTrue(buscador.buscar(dobleNot).contains(hotel));
        assertTrue(buscador.buscar(dobleNot).contains(pack));
    }
}
