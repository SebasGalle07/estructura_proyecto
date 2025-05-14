package controller;

import model.GrupoEstudio;
import model.Usuario;
import model.ListaEnlazada;
import model.Pair;

public class GrupoEstudioController {

    private ListaEnlazada<Pair<String, GrupoEstudio>> grupos;

    public GrupoEstudioController() {
        this.grupos = new ListaEnlazada<>();
    }

    public boolean crearGrupoPorTema(ListaEnlazada<Usuario> usuarios, String tema) {
        if (buscarGrupoPorTema(tema) != null) return false;

        GrupoEstudio grupo = new GrupoEstudio(tema);
        for (Usuario u : usuarios) {
            grupo.agregarIntegrante(u);
        }
        grupos.insertarFinal(new Pair<>(tema, grupo));
        return true;
    }

    public ListaEnlazada<GrupoEstudio> agruparPorInteres(ListaEnlazada<Usuario> usuarios, ListaEnlazada<String> temas) {
        ListaEnlazada<GrupoEstudio> gruposGenerados = new ListaEnlazada<>();

        for (String tema : temas) {
            GrupoEstudio grupo = new GrupoEstudio(tema);
            for (Usuario u : usuarios) {
                if (u.getIntereses().contiene(tema)) {
                    grupo.agregarIntegrante(u);
                }
            }
            if (!grupo.getIntegrantes().estaVacia()) {
                gruposGenerados.insertarFinal(grupo);
            }
        }

        return gruposGenerados;
    }

    public boolean agregarUsuarioAGrupo(String tema, Usuario usuario) {
        GrupoEstudio grupo = buscarGrupoPorTema(tema);
        if (grupo != null) {
            grupo.agregarIntegrante(usuario);
            return true;
        }
        return false;
    }

    public GrupoEstudio getGrupo(String tema) {
        return buscarGrupoPorTema(tema);
    }

    public ListaEnlazada<GrupoEstudio> getTodosLosGrupos() {
        ListaEnlazada<GrupoEstudio> todos = new ListaEnlazada<>();
        for (Pair<String, GrupoEstudio> par : grupos) {
            todos.insertarFinal(par.getValue());
        }
        return todos;
    }

    private GrupoEstudio buscarGrupoPorTema(String tema) {
        for (Pair<String, GrupoEstudio> par : grupos) {
            if (par.getKey().equalsIgnoreCase(tema)) {
                return par.getValue();
            }
        }
        return null;
    }
}
