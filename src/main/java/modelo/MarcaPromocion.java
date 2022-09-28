package modelo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@DiscriminatorValue("PROMOCION_MARCA")
public class MarcaPromocion extends Promocion {

    public String getMarca() {
        return marca;
    }

    private String marca;

    public MarcaPromocion(boolean estado, LocalDate fechaInicio, LocalDate fechaFin, double porcentaje, String marca) throws RuntimeException {
        super(estado, fechaInicio, fechaFin, porcentaje);
        this.marca = marca;
    }

    public MarcaPromocion() {

    }

    @Override
    public double descuento(String tipo) {
        LocalDate hoy = LocalDate.now();

        if (!this.marca.equals(tipo))
            return 0;

        if (hoy.isAfter(this.fechaFin()) || hoy.isBefore(this.fechaInicio())) {
            return 0;
        }
        return super.getDescuento();

    }



    public String marca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }


    @Override
    public String toString() {
        return "MarcaPromocion{ " + marca + " }";
    }
}
