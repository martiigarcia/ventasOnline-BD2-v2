package modelo;

import javax.persistence.*;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Categoria() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Categoria: " + nombre+ "id: "+ id;
    }
}
