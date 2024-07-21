import java.util.*;

public class TopologicalSort {
    private int vertices; // Número de vértices
    private LinkedList<Integer> adj[]; // Lista de adyacencia

    // Constructor
    TopologicalSort(int v) {
        vertices = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // Método para añadir una arista al grafo
    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // Método recursivo usado por topologicalSort
    void topologicalSortUtil(int v, boolean visited[], Stack<Integer> stack) {
        // Marcar el nodo actual como visitado
        visited[v] = true;
        Integer i;

        // Recorrer todos los vértices adyacentes
        for (Integer vertex : adj[v]) {
            if (!visited[vertex])
                topologicalSortUtil(vertex, visited, stack);
        }

        // Poner el vértice actual en la pila que contiene el resultado
        stack.push(v);
    }

    // El método para realizar el ordenamiento topológico
    void topologicalSort() {
        Stack<Integer> stack = new Stack<>();

        // Marcar todos los vértices como no visitados
        boolean visited[] = new boolean[vertices];
        for (int i = 0; i < vertices; i++)
            visited[i] = false;

        // Llamar al helper recursivo para almacenar el orden topológico
        for (int i = 0; i < vertices; i++)
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);

        // Imprimir el contenido de la pila
        while (!stack.isEmpty())
            System.out.print(stack.pop() + " ");
    }

    // Método principal para probar el código
    public static void main(String args[]) {
        // Crear un grafo con 6 vértices
        TopologicalSort g = new TopologicalSort(6);

        // Añadir aristas al grafo
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(0, 3);
        g.addEdge(0, 4);
        g.addEdge(0, 5);
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(1, 5);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 5);

        System.out.println("El ordenamiento topológico del grafo es:");
        g.topologicalSort();
    }
}
