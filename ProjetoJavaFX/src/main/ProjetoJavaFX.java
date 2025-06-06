/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ra2357094
 */
public class ProjetoJavaFX extends Application {
    
    @Override
public void start(Stage stage) throws Exception {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLDocument.fxml"));
        
        if (loader.getLocation() == null) {
            throw new RuntimeException("Arquivo FXML não encontrado em: /controller/FXMLDocument.fxml");
        }
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Sistema de Usuários");
        stage.show();
    } catch (Exception e) {
        System.err.println("Erro ao carregar FXML: " + e.getMessage());
        e.printStackTrace();
        throw e;
    }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Application.launch(ProjetoJavaFX.class, args);
    }   
    
}