
/**
 * Clase Contacto
 * 
 * Clase que representa un contacto profesional en la agenda.
 */
import java.util.Objects;

public class Contacto {

    private int id;                 // Identificador unico del contacto
    private String nombre;          // Nombre de pila
    private String apellido;        // Apellido
    private String correo;          // Correo electronico
    private String telefono;        // Telefono 
    private Integer idEmpresa;      // Id de la empresa asociada 

    public Contacto(int id, String nombre, String apellido, String correo, String telefono, Integer idEmpresa) {

        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.idEmpresa = idEmpresa;
    }

    //Metodos get y set
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // idEmpresa
    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    //Metodo to string
    @Override
    public String toString() {
        return "Contacto{"
                + "id = " + id
                + ", nombre = '" + nombre + '\''
                + ", apellido = '" + apellido + '\''
                + ", correo = '" + correo + '\''
                + ", telefono = '" + telefono + '\''
                + ", idEmpresa = " + idEmpresa
                + '}';
    }

    /**
     * Se define que dos Contacto son iguales si tienen el mismo 'id'. Esto
     * permite usar operaciones de comparacion en estructuras de datos (p. ej.
     * eliminar por objeto) y garantiza consistencia.
     */
    @Override
    public boolean equals(Object o) {
        //Si es la misma referencia, son iguales = true
        if (this == o) {
            return true;
        }

        //Si es null o no es la misma clase, no son iguales = false
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        //Cast seguro, ya se compara campos relevantes 
        Contacto contacto = (Contacto) o;
        return id == contacto.id;
    }

    /**
     * Genera un código hash basado en 'id'.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
