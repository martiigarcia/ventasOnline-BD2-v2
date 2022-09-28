package modelo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Inheritance
@DiscriminatorColumn(name = "tipo_promocion")
public abstract class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean estado;
    private Date fechaInicio;
    private Date fechaFin;
    private double descuento;


    public Promocion(boolean estado, LocalDate fechaInicio, LocalDate fechaFin, double descuento) throws RuntimeException {
        this.estado = estado;
        this.descuento = descuento;
        if (this.validarFecha(fechaInicio, fechaFin)) {
            this.fechaInicio = Date.from(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
            this.fechaFin = Date.from(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } else {
            throw new RuntimeException("Las fechas de la promocion no son validas.");
        }

    }

    protected Promocion() {

    }
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }



    public void setEstado() {
        this.estado = !this.estado;
    }

    private Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    protected double getDescuento() {
        return this.descuento;
    }

    public abstract double descuento(String tipo);

    public LocalDate fechaInicio() {
        return fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDate fechaFin() {
        return fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private boolean validarFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDate hoy = LocalDate.now();
        return (fechaInicio.isBefore(fechaFin) && hoy.isBefore(fechaFin));
    }

    public boolean fechaValida() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = this.fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fin = this.fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (inicio.isBefore(fin) && hoy.isBefore(fin)); //si se cumplen ambas y retorna true, es fecha valida
    }


    @Override
    public String toString() {
        return "Datos" + " {" +
                "estado=" + estado +
                ", fechaInicio=" + fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() +
                ", fechaFin=" + fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() +
                '}';
    }


}


