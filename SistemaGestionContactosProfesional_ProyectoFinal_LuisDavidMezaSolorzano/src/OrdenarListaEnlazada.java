
/**
 * Clase OrdenarListaEnlazada (MergeSort)
 *
 * Esta clase implementa MergeSort para listas enlazadas de Contacto.
 * Ordena los contactos segun:
 *   Apellido   (ignorando mayusculas/minusculas)
 *   Nombre     (si los apellidos son iguales)
 */
public class OrdenarListaEnlazada {

    /**
     * Método principal del algoritmo.
     *
     * Recibe la cabeza: primer nodo de una lista enlazada Retorna: el nuevo
     * nodo cabeza de la lista ya ordenada
     *
     * Pasos: Caso base: si la lista tiene 0 o 1 elementos, ya está ordenada
     * Dividir la lista en 2 mitades Ordenar cada mitad recursivamente Mezclar
     * ambas mitades ordenadas
     */
    public static ListaEnlazada.Nodo<Contacto> mergeSort(ListaEnlazada.Nodo<Contacto> cabeza) {

        //Caso base: lista vacia o de un solo nodo, ya está ordenada
        if (cabeza == null || cabeza.siguiente == null) {
            return cabeza;
        }

        //Encontrar la mitad usando dos punteros (lento/rapido)
        ListaEnlazada.Nodo<Contacto> mitad = obtenerMitad(cabeza);
        ListaEnlazada.Nodo<Contacto> mitadSiguiente = mitad.siguiente;

        //Se rompe la lista aqui para dividirla en dos listas
        mitad.siguiente = null;

        //Ordenar cada mitad 
        ListaEnlazada.Nodo<Contacto> izquierda = mergeSort(cabeza);
        ListaEnlazada.Nodo<Contacto> derecha = mergeSort(mitadSiguiente);

        //Unir ambas listas ordenadas
        return merge(izquierda, derecha);
    }

    /**
     * obtenerMitad()
     *
     * Encuentra la mitad de la lista usando los punteros: EL puntero lento
     * avanza de 1 en 1 Mientras que el rapido avanza de 2 en 2
     *
     * Cuando el puntero rapido llegue al final, el otro estara en la mitad.
     */
    private static ListaEnlazada.Nodo<Contacto> obtenerMitad(ListaEnlazada.Nodo<Contacto> cabeza) {

        ListaEnlazada.Nodo<Contacto> lento = cabeza;          // Avanza de 1 en 1
        ListaEnlazada.Nodo<Contacto> rapido = cabeza.siguiente; // Avanza de 2 en 2

        // Mientras rapido no llegue al final
        while (rapido != null && rapido.siguiente != null) {
            lento = lento.siguiente;            // lento +1
            rapido = rapido.siguiente.siguiente; // rápido +2
        }

        // Cuando termina, lento apunta al nodo de la mitad
        return lento;
    }

    /**
     * merge()
     *
     * Mezcla dos listas enlazadas ya ordenadas.
     *
     * Se usa un nodo "dummy" para no lidiar con casos especiales Vamos
     * comparando nodos de ambas listas Enlazamos el menor en la lista resultado
     */
    private static ListaEnlazada.Nodo<Contacto> merge(
            ListaEnlazada.Nodo<Contacto> a,
            ListaEnlazada.Nodo<Contacto> b) {

        // Nodo "falso" para simplificar el armado de la lista
        ListaEnlazada.Nodo<Contacto> dummy = new ListaEnlazada.Nodo<>(null);

        ListaEnlazada.Nodo<Contacto> tail = dummy; // puntero al final de la lista ordenada

        // Mientras ambas listas tengan nodos
        while (a != null && b != null) {

            // Comparamos contacto a con contacto b
            if (compararContactos(a.dato, b.dato) <= 0) {

                // 'a' va primero, se conecta
                tail.siguiente = a;
                a = a.siguiente; //Se avanza en 'a'

            } else {

                // 'b' va primero, lo se conecta
                tail.siguiente = b;
                b = b.siguiente; //Se avanza en 'b'
            }

            //Se avanza en la lista resultado
            tail = tail.siguiente;
        }

        // Si quedan elementos en A o B, se conectan al final
        tail.siguiente = (a != null) ? a : b;

        // La cabeza real esta después del nodo dummy
        return dummy.siguiente;
    }

    /**
     * compararContactos()
     *
     * Devuelve: 0 si x < y
     *   0  si x == y
     *   0  si x > y
     *
     * Se compara por: Apellido (sin distinguir mayusculas) Nombre (si los
     * apellidos son iguales)
     *
     * Esto permite orden un alfabetico.
     */
    private static int compararContactos(Contacto x, Contacto y) {

        // Casos especiales
        if (x == null && y == null) {
            return 0;
        }
        if (x == null) {
            return -1;
        }
        if (y == null) {
            return 1;
        }

        // Comparar por apellido (ignora mayusculas/minusculas)
        int c = x.getApellido().compareToIgnoreCase(y.getApellido());

        if (c != 0) {
            return c; // si son distintos, se retorna ese resultado
        }

        // Si los apellidos son iguales se compara por nombre
        return x.getNombre().compareToIgnoreCase(y.getNombre());
    }
}
