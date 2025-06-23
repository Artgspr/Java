/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package main;

import controller.FXMLDocumentController;
import validator.UsuarioValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author cti
 */  
public class SistemaUsuarioFX extends Application {
    
     @Override
    public void start(Stage stage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/finalmvc/view/FXMLDocument.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLDocument.fxml")); // barra inicial aqui!
        Parent root = loader.load();
        FXMLDocumentController controller = loader.getController();
        controller.setUsuarioValidator(new UsuarioValidator());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
