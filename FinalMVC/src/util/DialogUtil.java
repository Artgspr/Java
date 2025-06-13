package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class DialogUtil {
       public static void mostrarAlerta(AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem); 
        alert.showAndWait();
    }

    public static void mostrarErro(String mensagem) {
        mostrarAlerta(AlertType.ERROR, "Erro", mensagem);
    }

    public static void mostrarSucesso(String mensagem) {
        mostrarAlerta(AlertType.INFORMATION, "Sucesso", mensagem);
    }

    public static void mostrarAviso(String mensagem) {
        mostrarAlerta(AlertType.WARNING, "Atenção", mensagem);
    }
    
    public static boolean mostrarConfirmacao(String titulo, String mensagem) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensagem);

    Optional<ButtonType> resultado = alert.showAndWait();
    return resultado.isPresent() && resultado.get() == ButtonType.OK;
}

    

}

