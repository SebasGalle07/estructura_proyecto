package model;

import java.util.*;

public class GrafoAfinidad {
    private Map<String, Vertice> vertices;

    public GrafoAfinidad() {
        this.vertices = new HashMap<>();
    }

    public void agregarUsuario(Usuario u) {
        if (!vertices.containsKey(u.getId())) {
            vertices.put(u.getId(), new Vertice(u));
        }
    }

    public void conectar(String id1, String id2) {
        Vertice v1 = vertices.get(id1);
        Vertice v2 = vertices.get(id2);
        if (v1 != null && v2 != null && !id1.equals(id2)) {
            v1.conectar(v2);
            v2.conectar(v1);
        }
    }

    public List<Usuario> sugerenciasDeAmigos(String id) {
        List<Usuario> sugerencias = new ArrayList<>();
        Vertice origen = vertices.get(id);
        if (origen == null) return sugerencias;

        Set<String> directos = new HashSet<>();
        for (Vertice v : origen.getConexiones()) {
            directos.add(v.getUsuario().getId());
        }

        for (Vertice v : origen.getConexiones()) {
            for (Vertice amigo : v.getConexiones()) {
                if (!directos.contains(amigo.getUsuario().getId()) &&
                        !amigo.getUsuario().getId().equals(id) &&
                        !sugerencias.contains(amigo.getUsuario())) {
                    sugerencias.add(amigo.getUsuario());
                }
            }
        }
        return sugerencias;
    }

    public List<Usuario> caminoMasCorto(String idInicio, String idFin) {
        Map<String, Usuario> prev = new HashMap<>();
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.offer(idInicio);
        visitados.add(idInicio);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(idFin)) break;
            Vertice vertice = vertices.get(actual);
            if (vertice == null) continue;
            for (Vertice vecino : vertice.getConexiones()) {
                String idVecino = vecino.getUsuario().getId();
                if (!visitados.contains(idVecino)) {
                    visitados.add(idVecino);
                    prev.put(idVecino, vertices.get(actual).getUsuario());
                    cola.offer(idVecino);
                }
            }
        }

        LinkedList<Usuario> camino = new LinkedList<>();
        String paso = idFin;
        while (prev.containsKey(paso)) {
            camino.addFirst(vertices.get(paso).getUsuario());
            paso = prev.get(paso).getId();
        }

        if (!camino.isEmpty()) {
            camino.addFirst(vertices.get(idInicio).getUsuario());
        }

        return camino;
    }

    public Map<String, Vertice> getVertices() {
        return vertices;
    }
}