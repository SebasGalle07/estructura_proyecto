package app;

import view.MainView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //  Cargar datos quemados
        DatosPrueba.cargar(
                AppContext.usuarioController,
                AppContext.contenidoController,
                AppContext.ayudaController,
                AppContext.grafoController,
                AppContext.grupoEstudioController
        );

        // Lanzar GUI
        SwingUtilities.invokeLater(() -> {
            MainView main = new MainView();
            main.setVisible(true);
        });
    }
}
