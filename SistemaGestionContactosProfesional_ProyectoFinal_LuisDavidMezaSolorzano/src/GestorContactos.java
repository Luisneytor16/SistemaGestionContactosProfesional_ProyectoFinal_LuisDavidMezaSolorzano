import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase GestorContactos
 *
 * Administra:
 *   contactos (ListaEnlazada<Contacto>)
 *   empresas (ListaEnlazada<Empresa>)
 *   historial (ListaEnlazada<EntradaHistorial>)
 *
 * Ademas incorpora:
 *   pilaEliminados (Pila<Contacto>) -> para permitir deshacer contactos
 *   pilaAcciones (Pila<Accion>) -> para permitir deshacer empresas
 *
 * Notas:
 *   La pila guarda unicamente el objeto Contacto eliminado.
 *   Si se desea restaurar historial al deshacer, habría que
 *   guardar también las entradas de historial asociadas.
 */
public class GestorContactos {

    public ListaEnlazada<Contacto> contactos;
    public ListaEnlazada<Empresa> empresas;
    public ListaEnlazada<EntradaHistorial> historial;
    

    // Pila para guardar contactos eliminados
    public Pila<Contacto> pilaEliminados;
    
    // Pila para deshacer acciones
    public Pila<Accion> pilaAcciones = new Pila<>();


    private int siguienteIdContacto;
    private int siguienteIdEmpresa;
    private int siguienteIdHistorial;

    public GestorContactos() {
        contactos = new ListaEnlazada<>();
        empresas = new ListaEnlazada<>();
        historial = new ListaEnlazada<>();
        pilaEliminados = new Pila<>();

        siguienteIdContacto = 1;
        siguienteIdEmpresa = 1;
        siguienteIdHistorial = 1;
    }

    //  EMPRESAS
    public Empresa agregarEmpresa(String nombre, String direccion, String telefono, String industria) {
        Empresa e = new Empresa(siguienteIdEmpresa++, nombre, direccion, telefono, industria);
        empresas.agregar(e);
        return e;
    }

    public boolean actualizarEmpresa(int id, String nombre, String direccion, String telefono, String industria) {
        Empresa e = empresas.buscar(emp -> emp.getId() == id);
        if (e == null) return false;
        e.setNombre(nombre);
        e.setDireccion(direccion);
        e.setTelefono(telefono);
        e.setIndustria(industria);
        return true;
    }

    /**
     * eliminarEmpresa:
     *  Elimina la empresa de la lista.
     *  Ademas, desasocia (pone null) el idEmpresa de los contactos
     *  que pertenecían a esa empresa (no eliminamos los contactos).
     */
    public boolean eliminarEmpresa(int id) {
        Empresa e = empresas.buscar(emp -> emp.getId() == id);
        if (e == null) {
            return false;
        }

        // Se guarda la accion para deshacer
        pilaAcciones.push(new Accion("empresa", e));

        return empresas.eliminar(e);
    }


    public Empresa buscarEmpresaPorId(int id) {
        return empresas.buscar(emp -> emp.getId() == id);
    }

    //  CONTACTOS
    public Contacto agregarContacto(String nombre, String apellido, String correo, String telefono, Integer idEmpresa) {
        Contacto c = new Contacto(siguienteIdContacto++, nombre, apellido, correo, telefono, idEmpresa);
        contactos.agregar(c);
        return c;
    }

    public boolean actualizarContacto(int id, String nombre, String apellido, String correo, String telefono, Integer idEmpresa) {
        Contacto c = contactos.buscar(cont -> cont.getId() == id);
        if (c == null) return false;
        c.setNombre(nombre);
        c.setApellido(apellido);
        c.setCorreo(correo);
        c.setTelefono(telefono);
        c.setIdEmpresa(idEmpresa);
        return true;
    }

    /**
     * eliminarContacto:
     *  Guarda el contacto en la pilaEliminados.
     *  Guarda la empresa en la pilaAcciones.
     *  Elimina el contacto  y la empresa de la lista principal.
     *  Elimina el historial asociado.
     */
    public boolean eliminarContacto(int id) {
        Contacto c = contactos.buscar(cont -> cont.getId() == id);
        if (c == null) {
            return false;
        }

        // Guardamos la acción para deshacer
        pilaAcciones.push(new Accion("contacto", c));

        boolean eliminado = contactos.eliminar(c);

        if (eliminado) {
            ListaEnlazada<EntradaHistorial> porEliminar = new ListaEnlazada<>();

            historial.porCada(h -> {
                if (h.getIdContacto() == id) {
                    porEliminar.agregar(h);
                }
            });

            porEliminar.porCada(h -> historial.eliminar(h));
        }

        return eliminado;
    }


    public Contacto buscarContactoPorId(int id) {
        return contactos.buscar(cont -> cont.getId() == id);
    }

    //  HISTORIAL
    public EntradaHistorial agregarEntradaHistorial(int idContacto, String tipo, String nota) {
        Contacto c = buscarContactoPorId(idContacto);
        if (c == null) throw new IllegalArgumentException("No existe un contacto con ese ID.");

        EntradaHistorial entrada = new EntradaHistorial(siguienteIdHistorial++, idContacto, LocalDateTime.now(), tipo, nota);
        historial.agregar(entrada);
        return entrada;
    }

    public ListaEnlazada<EntradaHistorial> obtenerHistorialPorContacto(int idContacto) {
        ListaEnlazada<EntradaHistorial> r = new ListaEnlazada<>();
        historial.porCada(entry -> {
            if (entry.getIdContacto() == idContacto) r.agregar(entry);
        });
        return r;
    }

    //  DESHACER ULTIMA ELIMINACION
    public boolean deshacerUltimaAccion() {

        if (pilaAcciones.estaVacia()) {
            return false;
        }

        Accion a = pilaAcciones.pop();

        switch (a.tipo) {

            case "contacto": {
                Contacto c = (Contacto) a.dato;
                contactos.agregar(c);

                if (c.getId() >= siguienteIdContacto) {
                    siguienteIdContacto = c.getId() + 1;
                }

                return true;
            }

            case "empresa": {
                Empresa e = (Empresa) a.dato;
                empresas.agregar(e);

                if (e.getId() >= siguienteIdEmpresa) {
                    siguienteIdEmpresa = e.getId() + 1;
                }

                return true;
            }
        }

        return false;
    }


    //  LISTADOS COMPLETOS
    public ListaEnlazada<Contacto> listarTodosContactos() {
        return contactos;
    }

    public ListaEnlazada<Empresa> listarTodasEmpresas() {
        return empresas;
    }

    //  PERSISTENCIA EN ARCHIVO (CSV simple)
    private String escapar(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\n", "\\n").replace(";", "\\;");
    }

    private String desEscapar(String s) {
        if (s == null) return null;
        return s.replace("\\n", "\n").replace("\\;", ";").replace("\\\\", "\\");
    }

    /**
     * guardarEnArchivo:
     *  Guarda en secciones: #EMPRESAS, #CONTACTOS, #HISTORIAL
     */
    public void guardarEnArchivo(String archivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

        // EMPRESAS
        bw.write("#EMPRESAS");
        bw.newLine();
        empresas.porCada(e -> {
            try {
                bw.write(e.getId() + ";" + escapar(e.getNombre()) + ";" + escapar(e.getDireccion()) + ";" +
                        escapar(e.getTelefono()) + ";" + escapar(e.getIndustria()));
                bw.newLine();
            } catch (IOException ignored) {}
        });

        // CONTACTOS
        bw.write("#CONTACTOS");
        bw.newLine();
        contactos.porCada(c -> {
            try {
                bw.write(c.getId() + ";" + escapar(c.getNombre()) + ";" + escapar(c.getApellido()) + ";" +
                        escapar(c.getCorreo()) + ";" + escapar(c.getTelefono()) + ";" +
                        (c.getIdEmpresa() == null ? "" : c.getIdEmpresa()));
                bw.newLine();
            } catch (IOException ignored) {}
        });

        // HISTORIAL
        bw.write("#HISTORIAL");
        bw.newLine();
        DateTimeFormatter f = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        historial.porCada(h -> {
            try {
                bw.write(h.getId() + ";" + h.getIdContacto() + ";" + h.getFechaHora().format(f) + ";" +
                        escapar(h.getTipo()) + ";" + escapar(h.getNota()));
                bw.newLine();
            } catch (IOException ignored) {}
        });

        bw.close();
    }

    /**
     * cargarDesdeArchivo:
     *  Reconstruye listas y actualiza contadores nextId
     */
    public void cargarDesdeArchivo(String archivo) throws IOException {
        File f = new File(archivo);
        if (!f.exists()) return;

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String seccion = "";
        String linea;

        while ((linea = br.readLine()) != null) {
            if (linea.startsWith("#")) {
                seccion = linea.trim();
                continue;
            }
            if (linea.trim().isEmpty()) continue;

            String[] p = linea.split(";", -1);

            switch (seccion) {
                case "#EMPRESAS": {
                    Empresa e = new Empresa(Integer.parseInt(p[0]), desEscapar(p[1]), desEscapar(p[2]), desEscapar(p[3]), desEscapar(p[4]));
                    empresas.agregar(e);
                    siguienteIdEmpresa = Math.max(siguienteIdEmpresa, e.getId() + 1);
                    break;
                }
                case "#CONTACTOS": {
                    Integer idEmp = p[5].isEmpty() ? null : Integer.parseInt(p[5]);
                    Contacto c = new Contacto(Integer.parseInt(p[0]), desEscapar(p[1]), desEscapar(p[2]),
                            desEscapar(p[3]), desEscapar(p[4]), idEmp);
                    contactos.agregar(c);
                    siguienteIdContacto = Math.max(siguienteIdContacto, c.getId() + 1);
                    break;
                }
                case "#HISTORIAL": {
                    EntradaHistorial h = new EntradaHistorial(Integer.parseInt(p[0]), Integer.parseInt(p[1]),
                            LocalDateTime.parse(p[2]), desEscapar(p[3]), desEscapar(p[4]));
                    historial.agregar(h);
                    siguienteIdHistorial = Math.max(siguienteIdHistorial, h.getId() + 1);
                    break;
                }
            }
        }

        br.close();
    }
}




