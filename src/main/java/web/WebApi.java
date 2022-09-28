package web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import api.ClienteServicio;
import api.ProductoServicio;
import api.VentaServicio;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ClienteException;
import io.javalin.Javalin;
import io.javalin.core.util.Header;
import io.javalin.http.Handler;
import modelo.*;
import servicios.PromocionService;
import servicios.VentaService;

public class WebApi {


    //- Mostrará todos los productos (en un JList por ejemplo si utilizan Swing o
    //      un Select HTML de selección múltiple), permitiendo seleccionar uno o varios.
    //- Mostrará todas las tarjetas de crédito del cliente (en un Jlist o Select
    //      HTML) (iniciemos la aplicación seleccionando un cliente cualquiera pasando
    //      el ID por parámetro). Permitiendo elegir una para pagar.
    //- Permitirá revisar el monto total hasta el momento de los productos
    //      seleccionados (mediante un botón “precio total”).
    //- Permitirá realizar la compra (mediante otro botón).
    // - Mostrará los mensajes de error utilizando un JOptionPane en Swing o
    //      <span> en HTML.


    private ProductoServicio productos;
    private PromocionService promocionService;
    private ClienteServicio clienteServicio;
    private VentaServicio ventaServicio;
    private int webPort;

    public WebApi(int webPort, ProductoServicio productos, PromocionService promocionService, VentaService ventaServicio, ClienteServicio clienteServicio) {
        this.webPort = webPort;
        this.productos = productos;
        this.promocionService = promocionService;
        this.clienteServicio = clienteServicio;
        this.ventaServicio = ventaServicio;
    }

    public void start() {
        Javalin app = Javalin.create(config ->
        {
            config.enableCorsForAllOrigins();
        }).start(this.webPort);
        //posts y gets
        app.get("/productos", this.listarProductos());
        app.get("/tarjetas", this.listarTarjetas());
        app.post("/calcularmonto", this.precioTotal());
        app.post("/pagar", this.comprar());

        //excepciones
        app.exception(ClienteException.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.json(Map.of("result", "error", "errors", e.toMap()));
            // log error in a stream...
        });

        app.exception(Exception.class, (e, ctx) -> {
            e.printStackTrace();
            ctx.json(Map.of("result", "error", "message", "Ups... algo se rompió.: " + e.getMessage()));
            // log error in a stream...
        });
    }

    public Handler listarProductos() {
        return ctx -> {

            List<Producto> productosList = this.productos.listarProductos();

            var list = new ArrayList<Map<String, Object>>();
            for (Producto producto : productosList) {
                list.add(producto.toMap());
            }

            ctx.json(Map.of("result", "success", "productos", list));

        };
    }

    public Handler listarTarjetas() {
        return ctx -> {

            List<Tarjeta> tarjetaList = this.clienteServicio.listarTarjetas(1L);

            var list = new ArrayList<Map<String, Object>>();
            for (Tarjeta tarjeta : tarjetaList) {
                list.add(tarjeta.toMap());
            }

            ctx.json(Map.of("result", "success", "tarjetas", list));

        };
    }

    public Handler precioTotal() throws RuntimeException{
        return ctx -> {

            Map<String, List<String>> parametros = ctx.queryParamMap();

            String prod = parametros.get("productos").get(0);
            String tarj = parametros.get("tarjeta").get(0);

            if (prod.isEmpty())
                throw new RuntimeException("No se puede calcular el monto sin productos seleccionados.");
            if (tarj.isEmpty())
                throw new RuntimeException("No se puede calcular el monto sin una tarjeta seleccionada seleccionados.");

            List<Long> productosLong = Arrays.asList(prod.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            float monto = this.ventaServicio.calcularMonto(productosLong, Long.valueOf(tarj));

            ctx.json(Map.of("result", "success", "monto", monto));

        };
    }

    public Handler comprar() throws RuntimeException{
        return ctx -> {
            Map<String, List<String>> parametros = ctx.queryParamMap();

            String prod = parametros.get("productos").get(0);
            String tarj = parametros.get("tarjeta").get(0);

           
            if (prod.isEmpty())
                throw new RuntimeException("No se puede generar la compra sin productos seleccionados.");
            if (tarj.isEmpty())
                throw new RuntimeException("No se puede generar la compra sin una tarjeta seleccionada seleccionados.");

            List<Long> productosLong = Arrays.asList(prod.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            this.ventaServicio.realizarVenta(1L, productosLong, Long.valueOf(tarj));


            ctx.json(Map.of("result", "success"));

        };
    }

}
