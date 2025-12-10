
/**
 * Clase EntradaHistorial
 *
 * Representa una interaccion registrada con un contacto:
 * llamadas, correos, reuniones, notas, etc.
 *
 * Se usa dentro del gestor para mantener el historial completo
 * de actividades asociadas a los contactos.
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class EntradaHistorial {

    private int id;                  // Identificador unico del registro
    private int idContacto;          // Identificador del contacto al que pertenece esta entrada
    private LocalDateTime fechaHora; // Fecha y hora en que ocurrio la actividad
    private String tipo;             // Tipo de interaccion (llamada, email, reunión)
    private String nota;             // Detalle o descripcion del evento

    public EntradaHistorial(int id, int idContacto, LocalDateTime fechaHora, String tipo, String nota) {

        this.id = id;
        this.idContacto = idContacto;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.nota = nota;
    }

    //Metodos get y set
    public int getId() {
        return id;
    }

    public int getIdContacto() {
        return idContacto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNota() {
        return nota;
    }

    //Metodo toString()
    @Override
    public String toString() {
        // Formateador de fecha y hora
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return "EntradaHistorial{id = " + id + '\''
                + ", contactoId = " + idContacto + '\''
                + ", fecha = " + fechaHora.format(f) + '\''
                + ", tipo = " + tipo + '\''
                + ", nota = " + nota + "}";
    }

    /**
     * Dos entradas del historial se consideran iguales si su ID es el mismo.
     * Esto permite eliminar correctamente una entrada dentro de la lista
     * enlazada.
     */
    @Override
    public boolean equals(Object o) {

        //Si es el mismo objeto 
        if (this == o) {
            return true;
        }

        //Si es null o no es la misma clase
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        //Se compara por ID
        EntradaHistorial that = (EntradaHistorial) o;
        return id == that.id;
    }

    /**
     * Basado unicamente en id, para cumplir la regla: equals true = hashCode
     * igual
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
