package model;

public class GrafoAfinidad {
    private ListaEnlazada<Pair<String, Vertice>> vertices;

    public GrafoAfinidad() {
        this.vertices = new ListaEnlazada<>();
    }

    public void agregarUsuario(Usuario u) {
        if (buscarVertice(u.getId()) == null) {
            vertices.insertarFinal(new Pair<>(u.getId(), new Vertice(u)));
        }
    }

    public void conectar(String id1, String id2) {
        Vertice v1 = buscarVertice(id1);
        Vertice v2 = buscarVertice(id2);
        if (v1 != null && v2 != null && !id1.equals(id2)) {
            v1.conectar(v2);
            v2.conectar(v1);
        }
    }

    public ListaEnlazada<Usuario> sugerenciasDeAmigos(String id) {
        ListaEnlazada<Usuario> sugerencias = new ListaEnlazada<>();
        Vertice origen = buscarVertice(id);
        if (origen == null) return sugerencias;

        ListaEnlazada<String> directos = new ListaEnlazada<>();
        NodoLista<Vertice> nodo = origen.getConexiones().getCabeza();
        while (nodo != null) {
            directos.insertarUnico(nodo.getDato().getUsuario().getId());
            nodo = nodo.getSiguiente();
        }

        nodo = origen.getConexiones().getCabeza();
        while (nodo != null) {
            Vertice amigo = nodo.getDato();
            NodoLista<Vertice> vecinos = amigo.getConexiones().getCabeza();
            while (vecinos != null) {
                Usuario u = vecinos.getDato().getUsuario();
                if (!directos.contiene(u.getId()) && !u.getId().equals(id) && !sugerencias.contiene(u)) {
                    sugerencias.insertarFinal(u);
                }
                vecinos = vecinos.getSiguiente();
            }
            nodo = nodo.getSiguiente();
        }

        return sugerencias;
    }

    public ListaEnlazada<Usuario> caminoMasCorto(String idInicio, String idFin) {
        Cola<String> cola = new Cola<>();
        ListaEnlazada<String> visitados = new ListaEnlazada<>();
        ListaEnlazada<Pair<String, String>> prev = new ListaEnlazada<>();

        cola.encolar(idInicio);
        visitados.insertarFinal(idInicio);

        while (!cola.estaVacia()) {
            String actual = cola.desencolar();
            if (actual.equals(idFin)) break;

            Vertice v = buscarVertice(actual);
            if (v == null) continue;

            NodoLista<Vertice> nodo = v.getConexiones().getCabeza();
            while (nodo != null) {
                String vecino = nodo.getDato().getUsuario().getId();
                if (!visitados.contiene(vecino)) {
                    visitados.insertarFinal(vecino);
                    prev.insertarFinal(new Pair<>(vecino, actual));
                    cola.encolar(vecino);
                }
                nodo = nodo.getSiguiente();
            }
        }

        ListaEnlazada<Usuario> camino = new ListaEnlazada<>();
        String paso = idFin;
        while (true) {
            Pair<String, String> par = buscarParPrevio(prev, paso);
            if (par == null) break;
            camino.insertarInicio(buscarVertice(par.getKey()).getUsuario());
            paso = par.getValue();
        }

        if (!camino.estaVacia()) {
            camino.insertarInicio(buscarVertice(idInicio).getUsuario());
        }

        return camino;
    }

    private Vertice buscarVertice(String id) {
        NodoLista<Pair<String, Vertice>> actual = vertices.getCabeza();
        while (actual != null) {
            if (actual.getDato().getKey().equals(id)) {
                return actual.getDato().getValue();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    private Pair<String, String> buscarParPrevio(ListaEnlazada<Pair<String, String>> lista, String key) {
        NodoLista<Pair<String, String>> actual = lista.getCabeza();
        while (actual != null) {
            if (actual.getDato().getKey().equals(key)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public ListaEnlazada<Pair<String, Vertice>> getVertices() {
        return vertices;
    }
}
