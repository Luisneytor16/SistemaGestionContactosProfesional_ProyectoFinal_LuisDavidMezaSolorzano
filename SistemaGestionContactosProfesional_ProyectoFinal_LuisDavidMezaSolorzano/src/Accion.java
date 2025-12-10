/**
 * Clase Accion
 * 
 * Representa una acción reversible para la función "Deshacer".
 *
 * Puede ser:
 *   - tipo = "contacto"
 *   - tipo = "empresa"
 *
 * El campo dato almacena el objeto que debe restaurarse.
 */
public class Accion {

    public String tipo; // "contacto" o "empresa"
    public Object dato; // Contacto o Empresa

    public Accion(String tipo, Object dato) {
        this.tipo = tipo;
        this.dato = dato;
    }
}

