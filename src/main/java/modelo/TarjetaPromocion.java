package modelo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("PROMOCION_TARJETA")
public class TarjetaPromocion extends Promocion {

    private String tarjeta;

    public String getTarjeta() {
        return tarjeta;
    }


    public TarjetaPromocion(boolean estado, LocalDate fechaInicio, LocalDate fechaFin, double porcentaje, String tarjeta) throws RuntimeException {
        super(estado, fechaInicio, fechaFin, porcentaje);
        this.tarjeta = tarjeta;
    }

    protected TarjetaPromocion() {

    }

    @Override
    public double descuento(String tipo) {
        LocalDate hoy = LocalDate.now();
        if (!this.tarjeta.equals(tipo))
            return 0;

        if (hoy.isAfter(this.fechaFin())
                || hoy.isBefore(this.fechaInicio())) {
            return 0;
        }
        return super.getDescuento();
    }



    public String tarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }


    public String toString() {
        return (super.toString() + "TarjetaPromocion{ " + tarjeta + " }");
    }
}
