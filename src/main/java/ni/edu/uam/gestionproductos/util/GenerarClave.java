package ni.edu.uam.gestionproductos.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad temporal del laboratorio. Ejecutar Main y copiar el Base64
 * a la variable de entorno JWT_SECRET_BASE64. No publicar el valor.
 */
public class GenerarClave {

    public static void main(String[] args) {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        System.out.println(Base64.getEncoder().encodeToString(bytes));
    }
}
