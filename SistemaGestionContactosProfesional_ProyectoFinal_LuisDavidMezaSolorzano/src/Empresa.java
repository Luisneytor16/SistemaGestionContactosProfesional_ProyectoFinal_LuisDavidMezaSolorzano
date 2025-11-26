import java.util.Objects;

/**
* Representa una empresa a la cual un contacto puede pertenecer.
*/
public class Empresa {
    
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String industria;


    public Empresa(int id, String nombre, String direccion, String telefono, String industria) {
    this.id = id;
    this.nombre = nombre;
    this.direccion = direccion;
    this.telefono = telefono;
    this.industria = industria;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIndustria() {
        return industria;
    }

    public void setIndustria(String industria) {
        this.industria = industria;
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
