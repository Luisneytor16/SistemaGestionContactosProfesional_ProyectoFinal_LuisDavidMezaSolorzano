
/**
 * Clase Empresa
 * 
 * Representa una empresa a la cual un contacto puede pertenecer.
 */
import java.util.Objects;

public class Empresa {

    int id;                    // ID unico de la empresa
    private String nombre;     // Nombre comercial
    private String direccion;  // Direccion fisica
    private String telefono;   // Telefono
    private String industria;  // Sector empresarial

    public Empresa(int id, String nombre, String direccion, String telefono, String industria) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.industria = industria;
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

    //Metodo toString()
    @Override
    public String toString() {
        return "Empresa{id = " + id + '\''
                + ", nombre = " + nombre + '\''
                + ", dirrecion = " + direccion + '\''
                + ", telefono = " + telefono + '\''
                + ", industria = " + industria + 
                "}";
    }


    /**
     * equals()
     *
     * Compara dos objetos. Se consideran iguales si: Son el mismo objeto O si
     * tienen el mismo ID
     *
     * Esto es util para eliminaciones y busquedas en listas.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;                       // Si son el mismo objeto = true
        }
        if (o == null || getClass() != o.getClass()) // Si es null o de otra clase = false
        {
            return false;
        }

        Empresa empresa = (Empresa) o;
        return id == empresa.id;
    }


    /**
     * hashCode() 
     * 
     * Genera un valor hash basado unicamente en id. Si equals es true hace que
     * ambos hashCode deben ser iguales
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
