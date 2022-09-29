import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import modelo.Tarjeta;
import modelo.TipoTarjeta;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import modelo.Cliente;

class ClienteTest {

    //todos los casos de prueba:
    public static Object[] parametros() {
        return new Object[][] {
                { "", "Garcia", "00000000", "mar@gmail.com" },  //sin nombre
                { "Marti", "", "00000000", "mar@gmail.com" },   //sin apellido
                { "Marti", "Garcia", "", "mar@gmail.com" },     //sin dni
                { "Marti", "Garcia", "00000000", "mar@gmail" }, //email invalido
                { "Marti", "Garcia", "00000000", "" },          //sin email
                { "Marti", "Garcia", "000", "mar@gmail.com" },  //dni invalido
        };
    }

    @ParameterizedTest
    @MethodSource("parametros")
    void crearCliente(String nombre, String apellido, String dni, String email) {
        assertThrows(RuntimeException.class, () -> new Cliente(nombre, apellido, dni, email));
    }

    @Test
    public void agregarTarjeta(){
        Cliente cliente =  new Cliente("Martina", "Garcia", "12345678", "marti@gmail.com");
        Tarjeta tarjeta = new Tarjeta(12, TipoTarjeta.MERCADOPAGO);
        cliente.agregarTarjeta(tarjeta);
        assertEquals(1, cliente.getTarjetas().size());
    }

}


