package modelo;

public enum TipoTarjeta {

    MERCADOPAGO("Mercado pago"),
    VISA("Visa"),
    UALA("Uala");
    private String nombre;
    TipoTarjeta(String nombre){
        this.nombre = nombre;
    }
    public String nombre(){
        return this.nombre;
    }
};