import java.io.IOException;
import java.util.Scanner;

/**
 * Clase AppAgenda
 *
 * Interfaz de consola que presenta el menu principal y llama
 * a GestorContactos para realizar las operaciones.
 *
 */
public class AppAgenda {

    private GestorContactos gestor;
    private Scanner scanner;
    private static final String ARCHIVO = "agenda.txt";

    public AppAgenda() {
        gestor = new GestorContactos();
        scanner = new Scanner(System.in);

        try {
            gestor.cargarDesdeArchivo(ARCHIVO);
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo inicial.");
        }
    }

    /**
     * Menú principal: se repite hasta que el usuario elige salir.
     */
    public void ejecutar() {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=========== AGENDA PROFESIONAL ===========");
            System.out.println(" 1. Agregar contacto");
            System.out.println(" 2. Listar contactos");
            System.out.println(" 3. Buscar contacto por ID");
            System.out.println(" 4. Agregar empresa");
            System.out.println(" 5. Listar empresas");
            System.out.println(" 6. Actualizar contacto");
            System.out.println(" 7. Actualizar empresa");
            System.out.println(" 8. Eliminar contacto");
            System.out.println(" 9. Eliminar empresa");
            System.out.println("10. Agregar entrada de historial");
            System.out.println("11. Mostrar historial por contacto");
            System.out.println("12. Ordenar contactos alfabeticamente");
            System.out.println("13. Deshacer ultima eliminacion");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": flujoAgregarContacto(); break;
                case "2": flujoListarContactos(); break;
                case "3": flujoBuscarContactoPorId(); break;
                case "4": flujoAgregarEmpresa(); break;
                case "5": flujoListarEmpresas(); break;
                case "6": flujoActualizarContacto(); break;
                case "7": flujoActualizarEmpresa(); break;
                case "8": flujoEliminarContacto(); break;
                case "9": flujoEliminarEmpresa(); break;
                case "10": flujoAgregarEntradaHistorial(); break;
                case "11": flujoMostrarHistorial(); break;
                case "12": flujoOrdenarContactos(); break;
                case "13": flujoDeshacerAccion(); break;
                case "0":
                    salir = true;
                    try {
                        gestor.guardarEnArchivo(ARCHIVO);
                        System.out.println("Datos guardados correctamente.");
                    } catch (IOException e) {
                        System.out.println("Error al guardar archivo.");
                    }
                    break;
                default:
                    System.out.println("Opción invalida.");
            }
        }

        System.out.println("Cerrando programa");
    }

    //  FLUJOS DE FUNCIONALIDAD
    private void flujoAgregarContacto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();

        System.out.print("ID Empresa (o sin empresa): ");
        String idE = scanner.nextLine();
        Integer idEmpresa = idE.trim().isEmpty() ? null : Integer.parseInt(idE);

        Contacto c = gestor.agregarContacto(nombre, apellido, correo, telefono, idEmpresa);
        System.out.println("Contacto agregado: " + c);
    }

    private void flujoListarContactos() {
        ListaEnlazada<Contacto> lista = gestor.listarTodosContactos();
        if (lista.tamaño() == 0) {
            System.out.println("No hay contactos registrados.");
            return;
        }
        lista.porCada(c -> System.out.println(c));
    }

   
    private void flujoBuscarContactoPorId() {
        System.out.print("ID del contacto: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Contacto c = gestor.buscarContactoPorId(id);
            if (c == null) {
                System.out.println("No existe un contacto con ese ID.");
            } else {
                System.out.println(c);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID invalido.");
        }
    }

    private void flujoAgregarEmpresa() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Direccion: ");
        String dir = scanner.nextLine();
        System.out.print("Telefono: ");
        String tel = scanner.nextLine();
        System.out.print("Industria: ");
        String ind = scanner.nextLine();

        Empresa e = gestor.agregarEmpresa(nombre, dir, tel, ind);
        System.out.println("Empresa agregada: " + e);
    }

    private void flujoListarEmpresas() {
        ListaEnlazada<Empresa> lista = gestor.listarTodasEmpresas();
        if (lista.tamaño() == 0) {
            System.out.println("No hay empresas registradas.");
            return;
        }
        lista.porCada(e -> System.out.println(e));
    }

    private void flujoActualizarContacto() {
        System.out.print("ID del contacto a actualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("ID invalido.");
            return;
        }

        Contacto c = gestor.buscarContactoPorId(id);
        if (c == null) {
            System.out.println("No existe un contacto con ese ID.");
            return;
        }

        System.out.println("Dejar vacio para mantener el valor actual.");

        System.out.print("Nombre (" + c.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (nombre.trim().isEmpty()) nombre = c.getNombre();

        System.out.print("Apellido (" + c.getApellido() + "): ");
        String apellido = scanner.nextLine();
        if (apellido.trim().isEmpty()) apellido = c.getApellido();

        System.out.print("Correo (" + c.getCorreo() + "): ");
        String correo = scanner.nextLine();
        if (correo.trim().isEmpty()) correo = c.getCorreo();

        System.out.print("Telefono (" + c.getTelefono() + "): ");
        String telefono = scanner.nextLine();
        if (telefono.trim().isEmpty()) telefono = c.getTelefono();

        System.out.print("ID Empresa (" + (c.getIdEmpresa() == null ? "vacio" : c.getIdEmpresa()) + "): ");
        String idE = scanner.nextLine();
        Integer idEmpresa = idE.trim().isEmpty() ? c.getIdEmpresa() : Integer.parseInt(idE);

        boolean ok = gestor.actualizarContacto(id, nombre, apellido, correo, telefono, idEmpresa);
        if (ok) System.out.println("Contacto actualizado.");
        else System.out.println("Error al actualizar.");
    }

    private void flujoActualizarEmpresa() {
        System.out.print("ID de la empresa a actualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("ID invalido.");
            return;
        }

        Empresa e = gestor.buscarEmpresaPorId(id);
        if (e == null) {
            System.out.println("No existe una empresa con ese ID.");
            return;
        }

        System.out.println("Dejar vacio para mantener el valor actual.");

        System.out.print("Nombre (" + e.getNombre() + "): ");
        String nombre = scanner.nextLine();
        if (nombre.trim().isEmpty()) nombre = e.getNombre();

        System.out.print("Direccion (" + e.getDireccion() + "): ");
        String dir = scanner.nextLine();
        if (dir.trim().isEmpty()) dir = e.getDireccion();

        System.out.print("Telefono (" + e.getTelefono() + "): ");
        String tel = scanner.nextLine();
        if (tel.trim().isEmpty()) tel = e.getTelefono();

        System.out.print("Industria (" + e.getIndustria() + "): ");
        String ind = scanner.nextLine();
        if (ind.trim().isEmpty()) ind = e.getIndustria();

        boolean ok = gestor.actualizarEmpresa(id, nombre, dir, tel, ind);
        if (ok) System.out.println("Empresa actualizada.");
        else System.out.println("Error al actualizar.");
    }

    private void flujoEliminarContacto() {
        System.out.print("ID del contacto a eliminar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("ID invalido.");
            return;
        }

        boolean ok = gestor.eliminarContacto(id);
        if (ok) System.out.println("Contacto eliminado correctamente.");
        else System.out.println("No existe un contacto con ese ID.");
    }

    private void flujoEliminarEmpresa() {
        System.out.print("ID de la empresa a eliminar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("ID invalido.");
            return;
        }

        boolean ok = gestor.eliminarEmpresa(id);
        if (ok) System.out.println("Empresa eliminada correctamente (los contactos fueron desasociados).");
        else System.out.println("No existe una empresa con ese ID.");
    }

    private void flujoAgregarEntradaHistorial() {
        System.out.print("ID del contacto: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("ID invalido.");
            return;
        }

        System.out.print("Tipo (llamada, email, reunion): ");
        String tipo = scanner.nextLine();

        System.out.print("Nota: ");
        String nota = scanner.nextLine();

        try {
            gestor.agregarEntradaHistorial(id, tipo, nota);
            System.out.println("Registro agregado al historial.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void flujoMostrarHistorial() {
        System.out.print("ID contacto: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("ID invalido.");
            return;
        }

        ListaEnlazada<EntradaHistorial> h = gestor.obtenerHistorialPorContacto(id);
        if (h.tamaño() == 0) {
            System.out.println("No hay entradas registradas.");
            return;
        }
        h.porCada(entry -> System.out.println(entry));
    }

    private void flujoOrdenarContactos() {
        ListaEnlazada.Nodo<Contacto> ordenada = OrdenarListaEnlazada.mergeSort(gestor.contactos.obtenerCabeza());
        gestor.contactos.establecerCabeza(ordenada);
        System.out.println("Contactos ordenados correctamente.");
    }

    private void flujoDeshacerAccion() {
        boolean ok = gestor.deshacerUltimaAccion();
        if (ok) {
            System.out.println("Ultima acción revertida.");
        } else {
            System.out.println("No hay acciones para deshacer.");
        }
    }


    
    public static void main(String[] args) {
        AppAgenda app = new AppAgenda();
        app.ejecutar();
    }
}


