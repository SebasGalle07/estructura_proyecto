package controller;

import model.GrupoEstudio;
import model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GrupoEstudioController {

    private Map<String, GrupoEstudio> grupos;

    public GrupoEstudioController() {
        grupos = new HashMap<>();
    }

    // Crea un grupo manualmente por tema y agrega usuarios
    public boolean crearGrupoPorTema(List<Usuario> usuarios, String tema) {
        if (grupos.containsKey(tema)) return false;

        GrupoEstudio grupo = new GrupoEstudio(tema);
        for (Usuario u : usuarios) {
            grupo.agregarIntegrante(u);
        }

        grupos.put(tema, grupo);
        return true;
    }

    // Agrupa automáticamente por intereses (retorna lista sin guardar en el mapa)
    public List<GrupoEstudio> agruparPorInteres(List<Usuario> usuarios, List<String> temas) {
        List<GrupoEstudio> gruposGenerados = new ArrayList<>();

        for (String tema : temas) {
            List<Usuario> miembros = usuarios.stream()
                    .filter(u -> u.getIntereses().contiene(tema))
                    .collect(Collectors.toList());

            if (!miembros.isEmpty()) {
                GrupoEstudio grupo = new GrupoEstudio(tema);
                for (Usuario u : miembros) {
                    grupo.agregarIntegrante(u);
                }
                gruposGenerados.add(grupo);
            }
        }

        return gruposGenerados;
    }

    // Agregar usuario a un grupo ya existente
    public boolean agregarUsuarioAGrupo(String tema, Usuario usuario) {
        GrupoEstudio grupo = grupos.get(tema);
        if (grupo != null) {
            grupo.agregarIntegrante(usuario);
            return true;
        }
        return false;
    }

    // Obtener grupo por tema
    public GrupoEstudio getGrupo(String tema) {
        return grupos.get(tema);
    }

    // Obtener todos los grupos creados
    public List<GrupoEstudio> getTodosLosGrupos() {
        return new ArrayList<>(grupos.values());
    }
}
