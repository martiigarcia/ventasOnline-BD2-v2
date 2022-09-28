package web;

import modelo.Categoria;
import modelo.Marca;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

public class ProductoDTO {

    private String codigo;
    private String descripcion;
    private CategoriaDTO categoria;
    private double precio;
    private MarcaDTO marca;

    private long id;

    public long id() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setMarca(MarcaDTO marca) {
        this.marca = marca;
    }

    public String codigo() {
        return codigo;
    }

    public String descripcion() {
        return descripcion;
    }

    public MarcaDTO marca() {
        return marca;
    }

    public CategoriaDTO categoria() {
        return categoria;
    }

    public double precio() {
        return precio;
    }

}