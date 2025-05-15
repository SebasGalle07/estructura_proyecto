package app;

import controller.*;
import view.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Inicializar controladores
        UsuarioController usuarioCtrl = new UsuarioController();
        ContenidoController contenidoCtrl = new ContenidoController();
        AyudaController ayudaCtrl = new AyudaController();
        GrafoController grafoCtrl = new GrafoController();
        GrupoEstudioController grupoCtrl = new GrupoEstudioController();
    }
}