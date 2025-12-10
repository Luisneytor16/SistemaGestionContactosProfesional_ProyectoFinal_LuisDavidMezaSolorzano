/**
 * Clase Pila<T>
 *
 * Operaciones:
 *   push(item): apilar un elemento
 *   pop(): desapilar y devolver el elemento superior
 *   peek(): ver el elemento superior sin sacarlo
 *   estaVacia(): true si no hay elementos
 *   tamaño(): retorna la cantidad de elementos
 *   vaciar(): limpia la pila
 *

 * El proposito de esta clase es guardar contactos eliminados para permitir la funcionalidad
 * "Deshacer ultima eliminacion".
 */
public class Pila<T> {

    /**
     * Nodo interno que almacena el dato y una referencia al nodo
     * que queda debajo.
     */
    private static class Nodo<T> {
        T dato;
        Nodo<T> abajo;

        Nodo(T dato, Nodo<T> abajo) {
            this.dato = dato;
            this.abajo = abajo;
        }
    }

    private Nodo<T> cima; // referencia al nodo superior 
    private int tamaño;

    /**
     * Constructor: inicia una pila vacia.
     */
    public Pila() {
        cima = null;
        tamaño = 0;
    }

    /**
     * push: apila un elemento en la cima.
     * - Crea un nuevo nodo cuyo 'abajo' apunta a la cima anterior.
     * - Actualiza la cima.
     * - Incrementa el tamaño.
     */
    public void push(T item) {
        Nodo<T> nuevo = new Nodo<>(item, cima);
        cima = nuevo;
        tamaño++;
    }

    /**
     * pop: desapila y devuelve el elemento de la cima.
     * - Si la pila esta vacia lanza IllegalStateException.
     * - Actualiza la cima al nodo de abajo y decrementa tamaño.
     */
    public T pop() {
        if (cima == null) {
            throw new IllegalStateException("La pila esta vacia.");
        }
        T valor = cima.dato;
        cima = cima.abajo;
        tamaño--;
        return valor;
    }

    /**
     * peek: devuelve el elemento de la cima sin eliminarlo.
     * - Devuelve null si la pila esta vacia.
     */
    public T peek() {
        if (cima == null) return null;
        return cima.dato;
    }

    /**
     * estaVacia: indica si la pila no contiene elementos.
     */
    public boolean estaVacia() {
        return cima == null;
    }

    /**
     * tamaño: devuelve la cantidad de elementos en la pila.
     */
    public int tamaño() {
        return tamaño;
    }

    /**
     * vaciar: elimina todos los elementos de la pila.
     */
    public void vaciar() {
        cima = null;
        tamaño = 0;
    }
}
