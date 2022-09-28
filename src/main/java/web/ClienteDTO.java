package web;

public class ClienteDTO {

    private String nombre;
    private String apellido;
    private String dni;
    private String email;

    private String[] tarjetas;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(String[] tarjetas) {
        this.tarjetas = tarjetas;
    }

}
