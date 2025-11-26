import java.util.Objects;

/**
 * Clase que representa un contacto profesional
 */
public class Contacto {
 
    private int id; // identificador único
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private Integer idEmpresa; // puede ser null si no pertenece a ninguna empresa


    public Contacto(int id, String nombre, String apellido, String correo, String telefono, Integer idEmpresa) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.correo = correo;
    this.telefono = telefono;
    this.idEmpresa = idEmpresa;
    }
    
    //Metodos Get y Set

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

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    @Override
    public String toString() {
    return "Empresa{id=" + id + ", nombre=" + nombre + ", industria=" + industria + "}";
    }


    @Override
    public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Empresa empresa = (Empresa) o;
    return id == empresa.id;
    }


    @Override
    public int hashCode() {
    return Objects.hash(id);
    }
}
