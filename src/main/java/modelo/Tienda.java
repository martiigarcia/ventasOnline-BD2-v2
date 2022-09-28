package modelo;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Tienda {
    @Id
    @GeneratedValue
    private long id;
    //se hacen dos listas para mantener el registro historico de cada tipo de promociones
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_tienda_M")
    private List<Promocion> marcaPromociones;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_tienda_C")
    private List<Promocion> tarjetaPromociones;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_tienda")
    private List<Venta> ventaList;


    public Tienda() {
        this.ventaList = new ArrayList<>();
        this.marcaPromociones = new ArrayList<>();
        this.tarjetaPromociones = new ArrayList<>();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Promocion> getMarcaPromociones() {
        return marcaPromociones;
    }

    public void setMarcaPromociones(List<Promocion> marcaPromociones) {
        this.marcaPromociones = marcaPromociones;
    }

    public List<Promocion> getTarjetaPromociones() {
        return tarjetaPromociones;
    }

    public void setTarjetaPromociones(List<Promocion> tarjetaPromociones) {
        this.tarjetaPromociones = tarjetaPromociones;
    }

    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }


    public void agregarVenta(Venta venta) {
        this.ventaList.add(venta);
    }

    public List<Venta> verVentasRealizadas() {
        return ventaList;
    }


    //para actulizar las promociones
    public void setMarcaPromocion(MarcaPromocion marcaPromocion) throws RuntimeException{

        if (marcaPromocion == null)
            throw new RuntimeException("La promocion no puede ser vacia");

        if (!marcaPromocion.fechaValida())
            throw new RuntimeException("No se puede crear una promocion con una fecha de finalizacion ya expirada.");

        if (marcaPromociones.isEmpty()) {
            this.marcaPromociones.add(marcaPromocion);
        } else {
            this.marcaPromociones.get(marcaPromociones.size() - 1).setEstado();
            this.marcaPromociones.add(marcaPromocion);

        }

    }

    public void setTarjetaPromocion(Promocion tarjetaPromocion) throws RuntimeException{
        if (tarjetaPromocion == null)
            throw new RuntimeException("La promocion no puede ser vacia");

        if (!tarjetaPromocion.fechaValida())
            throw new RuntimeException("No se puede crear una promocion con una fecha de finalizacion ya expirada.");

        if (this.tarjetaPromociones.isEmpty()) {
            System.out.println("entra");
            this.tarjetaPromociones.add(tarjetaPromocion);

            System.out.println("llego");
        } else {
            System.out.println("entra al otro");
            this.tarjetaPromociones.get(tarjetaPromociones.size() - 1).setEstado();
            this.tarjetaPromociones.add(tarjetaPromocion);
            System.out.println("llego");
        }
    }


    //retornar la promocion de marca vigente
    public Promocion MarcaPromocionVigente() {
        return this.marcaPromociones.get(marcaPromociones.size() - 1);
    }

    //retornar la promocion de tarjeta vigente
    public Promocion TarjetaPromocionVigente() {
        return this.tarjetaPromociones.get(tarjetaPromociones.size() - 1);
    }

    public List<Promocion> marcaPromocionList() {
        return marcaPromociones;
    }

    public List<Promocion> tarjetaPromocionList() {
        return tarjetaPromociones;
    }



  @Override
    public String toString() {
        return "Tienda{" +
                "marcaPromociones=" + marcaPromociones +
                ", tarjetaPromociones=" + tarjetaPromociones +
                ", ventaList=" + ventaList +
                '}';
    }

}
