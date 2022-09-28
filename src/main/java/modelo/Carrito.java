package modelo;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<Producto> productos;

    public List<Producto> productos() {
        return productos;
    }

    public Carrito() {
        this.productos = new ArrayList<>();
        //    this.cliente = cliente;
    }

    public void agregarProductoAlCarrito(Producto producto) throws RuntimeException {
        if (producto == null)
            throw new RuntimeException("El producto a agregar no puede ser vacio.");
        this.productos.add(
                new Producto(producto.codigo(), producto.precio(),
                        producto.descripcion(), producto.categoria(),
                        producto.marca()));
    }


    public double calcularMontoCarrito(Promocion marcaPromocion, Promocion tarjetaPromocion, Tarjeta tarjeta) throws RuntimeException {


        if (tarjeta == null)
            throw new RuntimeException("La tarjeta para calcular el monto no puede ser vacia.");

        if (productos == null)
            throw new RuntimeException("Debe existir como minimo un producto en el carrito.");


        if (marcaPromocion == null || tarjetaPromocion == null)
            throw new RuntimeException("Las promociones no pueden ser vacias.");

        double precio = 0;
        for (Producto producto : this.productos) {

            //TOODO ESTO SE PUEDE MEJORAR HACIENDO LA COMPARACION
            // DE AMBOS IFS ADENTRO DE LAS PROMOCIONES

            //compara que el nombre de la marca del producto sea igual al nombre de la marca de la promocion

            precio = precio + (producto.precio() -
                    (producto.precio() * marcaPromocion.descuento(producto.marca().getNombre())));

        }

        //compara el STRING tipo de tarjeta con la tarjeta de la promocion
        precio = precio - (precio * tarjetaPromocion.descuento(tarjeta.tipoTarjeta().nombre()));


        return precio;

    }

    public Venta pagar(Cliente cliente, Promocion marcaPromocion, Promocion tarjetaPromocion, Tarjeta tarjeta) throws RuntimeException {

        if (productos == null)
            throw new RuntimeException("Debe existir como minimo un producto en el carrito.");

        if (tarjeta != null) { //deberia ser la validacion del servicio
            return new Venta(cliente, tarjeta, EstadoVenta.COMPLETA, productos, calcularMontoCarrito(marcaPromocion, tarjetaPromocion, tarjeta));
        }
        return new Venta(cliente, tarjeta, EstadoVenta.CANCELADA, productos, 0);
    }

   /* @Override
    public String toString() {
        return "Carrito{ " + productos +
                " }";
    }*/
}
