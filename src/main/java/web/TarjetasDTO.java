package web;

import modelo.TipoTarjeta;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class TarjetasDTO {

    private long id;
    private int numero;
    private String tipo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
