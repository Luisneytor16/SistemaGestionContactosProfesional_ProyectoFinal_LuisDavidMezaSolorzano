
/**
 * Clase ListaEnlazada<T>
 *
 * Implementacion manual de una lista enlazada simple generica. La lista usa
 * Nodos enlazados uno tras otro (estructura lineal).
 */
public class ListaEnlazada<T> {

    /**
     *Clase interna Nodo<T>
     * 
     * Representa cada elemento dentro de la lista.
     */
    public static class Nodo<T> {

        T dato;               // Dato almacenado en el nodo
        Nodo<T> siguiente;    // Enlace al siguiente nodo (null si es el ultimo)

        Nodo(T d) {
            dato = d;
            siguiente = null;  // Por defecto no apunta a nada
        }
    }

    private Nodo<T> cabeza;   // Primer nodo de la lista
    private int tamaño;       // Cantidad de elementos almacenados

    public ListaEnlazada() {
        cabeza = null;
        tamaño = 0;
    }

    /**
     * agregar(T item) Agrega un elemento al *final* de la lista.
     *
     * Crear un nuevo nodo con el dato. Si la lista esta vacia el nuevo nodo es
     * la cabeza. Si no, recorrer hasta el último nodo y enlazarlo.
     */
    public void agregar(T item) {
        Nodo<T> nuevo = new Nodo<>(item);

        //En caso de una lista vacia la cabeza apunta al nuevo nodo
        if (cabeza == null) {
            cabeza = nuevo;
        } else {

            //Recorrer la lista para hallar el ultimo nodo
            Nodo<T> cur = cabeza;

            //Mientras el siguiente no sea null se avanza
            while (cur.siguiente != null) {
                cur = cur.siguiente;
            }

            //Aqui cur esta en el ultimo nodo y conecta a el nuevo nodo
            cur.siguiente = nuevo;
        }

        tamaño++;  // Se aumenta el contador de elementos
    }

    /**
     * eliminar(T item) 
     * 
     * Elimina un elemento de la lista comparando con equals(). Si la
     * lista esta vacia entonces no se puede eliminar. Revisar si el primer nodo
     * contiene el valor a eliminar. Recorrer la lista buscando el nodo previo
     * al nodo objetivo. Enlazar para “saltar” el nodo eliminado.
     */
    public boolean eliminar(T item) {

        // Caso: lista vacaa
        if (cabeza == null) {
            return false;
        }

        // Caso: el elemento esta en el primer nodo
        if (cabeza.dato.equals(item)) {
            cabeza = cabeza.siguiente;  // Se mueve la cabeza al siguiente nodo
            tamaño--;
            return true;
        }

        // Caso general: buscar en el resto de la lista
        Nodo<T> prev = cabeza;            // Nodo anterior
        Nodo<T> cur = cabeza.siguiente;   // Nodo actual

        while (cur != null) {

            // Si  se encuetra el nodo que contiene el valor se elimina
            if (cur.dato.equals(item)) {
                prev.siguiente = cur.siguiente;  // Se salta el nodo actual
                tamaño--;
                return true;
            }

            // Avanzar en la lista
            prev = cur;
            cur = cur.siguiente;
        }

        // En caso de recorrer toda la lista y no se encontro
        return false;
    }

    /**
     * buscar(Predicate<T> pred)
     * 
     * Busca elprimer elemento que cumpla un predicado. Permite construir búsquedas
     * como: lista.buscar(c -> c.getId() == 10)
     */
    public T buscar(java.util.function.Predicate<T> pred) {
        Nodo<T> cur = cabeza;

        while (cur != null) {

            // Se evalua si el predicado esta sobre el dato del nodo actual
            if (pred.test(cur.dato)) {
                return cur.dato;  // Si cumple se devuelve
            }

            cur = cur.siguiente;  // Se avanza al siguiente nodo
        }

        return null;  // No hay coincidencias
    }

    /**
     * obtener(int indice) 
     * 
     * Devuelve el elemento en la posicion indicada. La lista es 0-based = el
     * primer elemento es indice 0.
     * Se verifica que el indice este dentro de rango. Se recorre la lista hasta
     * alcanzar el nodo deseado.
     */
    public T obtener(int indice) {

        // Validacion de rango
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException();
        }

        Nodo<T> cur = cabeza;

        //Se avanza 'indice' veces
        for (int i = 0; i < indice; i++) {
            cur = cur.siguiente;
        }

        return cur.dato;  // Se devuelve el dato encontrado
    }

    /**
     * tamaño()
     * 
     * Devuelve la cantidad de elementos en la lista.
     */
    public int tamaño() {
        return tamaño;
    }

    /**
     * porCada(Consumer<T> accion)
     * 
     * Recorre la lista completa y ejecuta una accion sobre cada elemento. 
     * Funciona como un forEach manual.
     */
    public void porCada(java.util.function.Consumer<T> accion) {
        Nodo<T> cur = cabeza;

        while (cur != null) {
            accion.accept(cur.dato);   // Se ejecuta la accion
            cur = cur.siguiente;       // Avanza
        }
    }

    /**
     * Metodos auxiliares para ordenamiento externo
     *
     * obtenerCabeza(): permite acceder al nodo inicial. 
     * establecerCabeza():permite reasignar la cabeza luego de ordenar.
     */
    public Nodo<T> obtenerCabeza() {
        return cabeza;
    }

    public void establecerCabeza(Nodo<T> n) {
        cabeza = n;
    }
}
