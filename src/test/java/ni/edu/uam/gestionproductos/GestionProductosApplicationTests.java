package ni.edu.uam.gestionproductos;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GestionProductosApplicationTests {

    @BeforeAll
    static void configurarSecretoJwt() {
        // Solo para pruebas locales; no es un secreto de producción.
        System.setProperty(
                "JWT_SECRET_BASE64",
                "dGVzdC1qd3Qtc2VjcmV0LW5pY2FtYXJrZXQtbGFiLTMyYnl0ZXMh");
    }

    @Test
    void contextLoads() {
    }
}
