import Clases.*;
import CatalogoTuristico.*;
import EstadosDeReserva.Pendiente;
import MetodoDePago.MetodoDePago;
import MetodosDeTraslado.*;
import NotificacionesDeReserva.NotificadorDeReserva;
import Reportes.Reporte.*;
import Reportes.Exportador.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static CatalogoTuristico.Categoria.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ReportesTest {
    private static final LocalDate DIA1 = LocalDate.of(2026, 12, 1);
    private static final LocalDate DIA2 = LocalDate.of(2026, 12, 2);
    private static final LocalDate DIA3 = LocalDate.of(2026, 12, 3);

    private Catalogo catalogo;
    private ServicioIndividual hotel;
    private ServicioIndividual excursion;
    private ServicioIndividual vuelo;
    private PaqueteTuristico pack;

    @BeforeEach
    void setUp() {
        hotel = new ServicioIndividual(1, "Hotel Central", "hotel", "Booking", 0f, 15000f, ALOJAMIENTO, cronograma(10));
        excursion = new ServicioIndividual(2, "Excursion Museo", "museo", "Booking", 0f, 4000f, EXCURSION, cronograma(5));
        vuelo = new ServicioIndividual(3, "Vuelo Roma", "vuelo", "Aerolinea", 0f, 100000f, VUELO, new CronogramaServicio());
        pack = new PaqueteTuristico("Pack Escapada", "hotel + museo",
                Map.of(hotel, 1, excursion, 1), 0.1f, cronograma(3));

        catalogo = new Catalogo();
        catalogo.registrarNuevoServicio(hotel);
        catalogo.registrarNuevoServicio(excursion);
        catalogo.registrarNuevoServicio(vuelo);
        catalogo.registrarNuevoServicio(pack);

        // 2 hoteles + 1 excursión del 1 al 2 de diciembre
        reservar(DIA1, DIA2, hotel, 2, excursion, 1);
        // 1 pack del 2 al 3 de diciembre
        reservar(DIA2, DIA3, pack, 1, null, 0);
        // 5 hoteles en enero: queda fuera del período de los reportes
        hotel.agregarCupos(5, LocalDate.of(2027, 1, 10), LocalDate.of(2027, 1, 10));
        reservar(LocalDate.of(2027, 1, 10), LocalDate.of(2027, 1, 10), hotel, 5, null, 0);
    }

    private CronogramaServicio cronograma(int cuposPorDia) {
        CronogramaServicio cronograma = new CronogramaServicio();
        cronograma.agregarCupo(cuposPorDia, DIA1, DIA3);
        return cronograma;
    }

    private void reservar(LocalDate inicio, LocalDate fin, ServicioTuristico a, int cantA,
                          ServicioTuristico b, int cantB) {
        Reserva reserva = new Reserva(new Pendiente(), new PuntoDeEncuentro(), 1, mock(Direccion.class),
                mock(MetodoDePago.class), catalogo, mock(Cliente.class), new NotificadorDeReserva(),
                inicio, fin, true);
        reserva.agregarServicio(a, cantA);
        if (b != null) {
            reserva.agregarServicio(b, cantB);
        }
        reserva.confirmarReserva();
        catalogo.registrarReserva(reserva);
    }


    @Test
    void test001_serviciosMasReservadosSeOrdenanPorUnidadesVendidasEnElPeriodo() {
        ReporteServicioMasReservado reporte = new ReporteServicioMasReservado(catalogo, DIA1, LocalDate.of(2026, 12, 31));

        assertEquals(java.util.List.of(hotel, excursion, pack, vuelo), reporte.itemsOrdenadosPorVentas());
        assertEquals(2, reporte.unidadesVendidas(hotel));   // las 5 de enero no cuentan
        assertEquals(1, reporte.unidadesVendidas(pack));
        assertEquals(0, reporte.unidadesVendidas(vuelo));
    }

    @Test
    void test002_elPrecioPromedioCobradoDelPaqueteIncluyeSuDescuento() {
        ReporteServicioMasReservado reporte = new ReporteServicioMasReservado(catalogo, DIA1, DIA3);

        assertEquals(17100f, reporte.precioPromedioCobrado(pack), 0.01);   // (15000 + 4000) * 0.9
        assertEquals(15000f, reporte.precioPromedioCobrado(hotel), 0.01);
    }

    @Test
    void test003_ocupacionDeAlojamientoSumaCuposLibresYReservadosPorDia() {
        ReporteOcupacion reporte = new ReporteOcupacion(catalogo, DIA1, DIA3);

        // Alojamiento: hotel (10 x 3 días) y el pack, que contiene un hotel (3 x 3 días)
        assertEquals(39, reporte.cuposTotales(ALOJAMIENTO));
        assertEquals(6, reporte.cuposReservados(ALOJAMIENTO));   // hotel: 2 x 2 días, pack: 1 x 2 días
        assertEquals(33, reporte.cuposLibres(ALOJAMIENTO));
    }

    @Test
    void test004_ocupacionDeCategoriaSinCuposEsCero() {
        ReporteOcupacion reporte = new ReporteOcupacion(catalogo, DIA1, DIA3);

        assertEquals(0, reporte.cuposTotales(VUELO));
        assertEquals(0, reporte.cuposTotales(ALQUILERAUTO));
    }

    @Test
    void test005_exposicionPorProveedorIgnoraLosPaquetes() {
        ReporteProveedor reporte = new ReporteProveedor(catalogo);

        assertEquals(java.util.List.of("Booking", "Aerolinea"), reporte.proveedores());
        assertEquals(66.7, reporte.porcentaje("Booking"), 0.001);
        assertEquals(33.3, reporte.porcentaje("Aerolinea"), 0.001);
    }

    @Test
    void test006_exposicionPorProveedorConCatalogoVacioDa0() {
        ReporteProveedor reporte = new ReporteProveedor(new Catalogo());

        assertTrue(reporte.proveedores().isEmpty());
        assertEquals(0, reporte.porcentaje("Booking"));
    }


    @Test
    void test007_serviciosMasReservadosEnTextoPlano() {
        Reporte reporte = new ReporteServicioMasReservado(catalogo, DIA1, DIA3);

        String esperado = "Servicios más reservados (2026-12-01 a 2026-12-03)\n"
                + "--------------------------------------------------\n"
                + "Servicio | Unidades | Precio promedio cobrado\n"
                + "Hotel Central | 2 | 15000.0\n"
                + "Excursion Museo | 1 | 4000.0\n"
                + "Pack Escapada | 1 | 17100.0\n"
                + "Vuelo Roma | 0 | 100000.0\n";
        assertEquals(esperado, reporte.aceptar(new ExportadorTextoPlano()));
    }

    @Test
    void test008_serviciosMasReservadosEnCSV() {
        Reporte reporte = new ReporteServicioMasReservado(catalogo, DIA1, DIA3);

        String esperado = "Servicio,Unidades,Precio promedio cobrado\n"
                + "Hotel Central,2,15000.0\n"
                + "Excursion Museo,1,4000.0\n"
                + "Pack Escapada,1,17100.0\n"
                + "Vuelo Roma,0,100000.0\n";
        assertEquals(esperado, reporte.aceptar(new ExportadorCSV()));
    }

    @Test
    void test009_serviciosMasReservadosEnHTML() {
        String html = new ReporteServicioMasReservado(catalogo, DIA1, DIA3).aceptar(new ExportadorHTML());

        assertTrue(html.startsWith("<html>"));
        assertTrue(html.endsWith("</html>"));
        assertTrue(html.contains("<h1>Servicios más reservados (2026-12-01 a 2026-12-03)</h1>"));
        assertTrue(html.contains("<th>Unidades</th>"));
        assertTrue(html.contains("<td>Hotel Central</td>"));
        assertTrue(html.contains("<td>17100.0</td>"));
    }

    @Test
    void test010_ocupacionEnLosTresFormatos() {
        Reporte reporte = new ReporteOcupacion(catalogo, DIA1, DIA3);

        String texto = reporte.aceptar(new ExportadorTextoPlano());
        assertTrue(texto.contains("Ocupación y cupo disponible (2026-12-01 a 2026-12-03)"));
        assertTrue(texto.contains("ALOJAMIENTO | 39 | 6 | 33\n"));
        assertTrue(texto.contains("VUELO | 0 | 0 | 0\n"));

        String csv = reporte.aceptar(new ExportadorCSV());
        assertTrue(csv.startsWith("Categoría,Cupos totales,Reservados,Libres\n"));
        assertTrue(csv.contains("ALOJAMIENTO,39,6,33\n"));

        String html = reporte.aceptar(new ExportadorHTML());
        assertTrue(html.contains("<th>Cupos totales</th>"));
        assertTrue(html.contains("<td>ALOJAMIENTO</td>"));
        assertTrue(html.contains("<td>39</td>"));
    }

    @Test
    void test011_proveedorEnLosTresFormatos() {
        Reporte reporte = new ReporteProveedor(catalogo);

        assertEquals("Exposición por proveedor\n------------------------\nProveedor | Porcentaje\n"
                + "Booking | 66.7%\nAerolinea | 33.3%\n", reporte.aceptar(new ExportadorTextoPlano()));
        assertEquals("Proveedor,Porcentaje\nBooking,66.7%\nAerolinea,33.3%\n", reporte.aceptar(new ExportadorCSV()));
        assertTrue(reporte.aceptar(new ExportadorHTML()).contains("<td>Aerolinea</td>"));
    }


    @Test
    void test012_csvEscapaComasYComillas() {
        Catalogo otro = new Catalogo();
        otro.registrarNuevoServicio(new ServicioIndividual(9, "Hotel \"Sol\", centro", "d", "P", 0f, 1f, ALOJAMIENTO,
                new CronogramaServicio()));

        String csv = new ReporteServicioMasReservado(otro, DIA1, DIA3).aceptar(new ExportadorCSV());

        assertTrue(csv.contains("\"Hotel \"\"Sol\"\", centro\",0,1.0"));
    }

    @Test
    void test013_htmlEscapaCaracteresEspeciales() {
        Catalogo otro = new Catalogo();
        otro.registrarNuevoServicio(new ServicioIndividual(9, "Bed & <Breakfast>", "d", "P", 0f, 1f, ALOJAMIENTO,
                new CronogramaServicio()));

        String html = new ReporteServicioMasReservado(otro, DIA1, DIA3).aceptar(new ExportadorHTML());

        assertTrue(html.contains("<td>Bed &amp; &lt;Breakfast&gt;</td>"));
    }


    @Test
    void test014_cadaReporteLlamaAlMetodoDelExportadorQueLeCorresponde() {
        Exportador registrador = new Exportador() {
            public String visitarServicioMasReservado(ReporteServicioMasReservado r) { return "masReservado"; }
            public String visitarOcupacion(ReporteOcupacion r) { return "ocupacion"; }
            public String visitarProveedor(ReporteProveedor r) { return "proveedor"; }
        };

        assertEquals("masReservado", new ReporteServicioMasReservado(catalogo, DIA1, DIA3).aceptar(registrador));
        assertEquals("ocupacion", new ReporteOcupacion(catalogo, DIA1, DIA3).aceptar(registrador));
        assertEquals("proveedor", new ReporteProveedor(catalogo).aceptar(registrador));
    }
}
