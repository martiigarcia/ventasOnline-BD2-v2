
import modelo.*;
import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test ;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class CarritoTest {

    private Tienda tienda;
    private Cliente cliente;
    private Tarjeta tarjeta;
    private Carrito carrito;
    private Marca marcaAcme, marcaEco, marcaFrutiloqui;
    private Categoria categoria, categoria2;
    private Producto producto1, producto2, producto3, producto4;
    private LocalDate fecha2DiasAntes, fecha2DiasDesp;

    @BeforeEach
    public void before() {

        tienda = new Tienda();
        carrito = new Carrito();

        cliente = new Cliente("Martina", "Garcia", "12345678", "marti@gmail.com");
        tarjeta = new Tarjeta(1111, TipoTarjeta.MERCADOPAGO);

        marcaAcme = new Marca("Acme");
        marcaEco = new Marca("Eco");
        marcaFrutiloqui = new Marca("Frutiloqui");

        categoria = new Categoria("Cosmetico");
        categoria2 = new Categoria("Fruta");

        producto1 = new Producto("1", 100, "Labial", categoria, marcaAcme);
        producto2 = new Producto("2", 100, "Rimel", categoria, marcaAcme);
        producto4 = new Producto("3", 34, "Manzana", categoria2, marcaEco);
        producto3 = new Producto("4", 6, "Pera", categoria2, marcaFrutiloqui);


        fecha2DiasAntes = LocalDate.now().minusDays(2);
        fecha2DiasDesp = LocalDate.now().plusDays(2);
    }

    @Test
    public void agregarProductoAlCarrito(){
        carrito.agregarProductoAlCarrito(producto1);
        assertEquals(1, carrito.productos().size());
    }
    @Test
    public void agregarProductoVacio(){
        assertThrows(RuntimeException.class, () ->  carrito.agregarProductoAlCarrito(null));
    }

    @Test
    public void descuentoDeProducto(){
        // Calcular el monto total del carrito con un descuento vigente para los productos marca Acme.
        // no descuento en tarjeta
        carrito.agregarProductoAlCarrito(producto1);
        carrito.agregarProductoAlCarrito(producto2);
        assertEquals(190, carrito.calcularMontoCarrito(
                new MarcaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp,0.05, marcaAcme.getNombre()),
                new TarjetaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp, 0.08, TipoTarjeta.UALA.nombre()),
                tarjeta //tarjeta de mp
        ));
    }

    @Test
    public void descuentoDeCompra(){
        // Calcular el monto total del carrito con un descuento vigente del tipo de Medio de pago.
        // no descuento en productos
        carrito.agregarProductoAlCarrito(producto3);
        carrito.agregarProductoAlCarrito(producto4);
        assertEquals(36.8, carrito.calcularMontoCarrito(
                new MarcaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp, 0.05,marcaAcme.getNombre()),
                new TarjetaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp, 0.08, TipoTarjeta.MERCADOPAGO.nombre()),
                tarjeta //tarjeta de mp
        ));
    }

    @Test
    public void descuentoDeProductosYCompra(){
        // Calcular el monto total del carrito con dos descuentos vigentes,
        // sobre productos marca Acme y para tarjeta de crédito MP.

        carrito.agregarProductoAlCarrito(producto1);
        carrito.agregarProductoAlCarrito(producto2);
        assertEquals(174.8, carrito.calcularMontoCarrito(
                new MarcaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp, 0.05,marcaAcme.getNombre()),
                new TarjetaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp, 0.08, TipoTarjeta.MERCADOPAGO.nombre()),
                tarjeta //tarjeta de mp
        ));
    }

    @Test
    public void descuentosCaducados(){
        // Calcular el monto total del carrito seleccionado sin descuentos vigentes, pero con descuentos que ya caducaron.
        // sobre productos marca Acme y para tarjeta de crédito MP.

        carrito.agregarProductoAlCarrito(producto1);
        carrito.agregarProductoAlCarrito(producto2);
        assertThrows(RuntimeException.class, () -> carrito.calcularMontoCarrito(
                new MarcaPromocion(true,
                        fecha2DiasAntes, LocalDate.now().minusDays(1),0.05, marcaAcme.getNombre()),
                new TarjetaPromocion(true,
                        fecha2DiasAntes, LocalDate.now().minusDays(1), 0.08, TipoTarjeta.MERCADOPAGO.nombre()),
                tarjeta //tarjeta de mp
        ));
    }

    @Test
    public void pagarYRegistrarVenta(){
        // aca registrar la venta desde el pagar del carrito

        assertEquals(Venta.class, carrito.pagar(cliente,
                new MarcaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp, 0.05,marcaAcme.getNombre()),
                new TarjetaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp, 0.08, TipoTarjeta.MERCADOPAGO.nombre()),
                tarjeta).getClass());

    }

    @Test
    public void pagarYRegistrarVentaSinTarjetaValida(){
        // aca registrar la venta desde el pagar del carrito

        assertEquals(Venta.class, carrito.pagar(cliente,
                new MarcaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp,0.05, marcaAcme.getNombre()),
                new TarjetaPromocion(true,
                        fecha2DiasAntes, fecha2DiasDesp, 0.08, TipoTarjeta.MERCADOPAGO.nombre()),
                null).getClass());

    }







}