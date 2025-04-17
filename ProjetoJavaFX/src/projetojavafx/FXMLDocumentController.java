package projetojavafx;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.UsuarioDAO;
import model.dto.UsuarioDTO;

public class FXMLDocumentController implements Initializable {

    @FXML private Button btnCadastrar, btnPesquisar, btnExcluir, btnEditar;
    @FXML private TextField txtLogin, txtNome, txtEmail;
    @FXML private PasswordField pswdSenha;
    @FXML private TextArea txtPesquisar;
    @FXML private TableView<UsuarioDTO> tblUsuario;
    @FXML private TableColumn<UsuarioDTO, String> colLogin, colNome, colEmail;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        listarUsuarios();
        
        tblUsuario.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
        if (selecionado != null) {
            txtLogin.setText(selecionado.getLogin());
            txtNome.setText(selecionado.getNome());
            txtEmail.setText(selecionado.getEmail());
            pswdSenha.setText(selecionado.getSenha());
        }
    });
    }

    @FXML
    private void cadastrar(ActionEvent event) {
        if (camposVazios()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obrigatórios", "Por favor, preencha todos os campos.");
            return;
        }

        UsuarioDTO usuario = new UsuarioDTO(
            txtNome.getText(),
            txtEmail.getText(),
            pswdSenha.getText(),
            txtLogin.getText()
        );

        boolean sucesso = new UsuarioDAO().cadastrarUsuario(usuario);

        if (sucesso) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cadastro realizado", "Usuário cadastrado com sucesso.");
            listarUsuarios();
            limparCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível cadastrar o usuário.");
        }
    }

    @FXML
    private void pesquisar(ActionEvent event) {
        String termo = txtPesquisar.getText().trim();
        List<UsuarioDTO> usuarios;

        if (termo.isEmpty()) {
            usuarios = new UsuarioDAO().listarUsuarios();
        } else {
            usuarios = new UsuarioDAO().pesquisarUsuarios(termo);
        }

        tblUsuario.getItems().setAll(usuarios);
    }

    @FXML
    private void excluir(ActionEvent event) {
        UsuarioDTO selecionado = tblUsuario.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma seleção", "Selecione um usuário para excluir.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir este usuário?");
        
        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean sucesso = new UsuarioDAO().removerUsuario(selecionado.getId());

                if (sucesso) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Exclusão concluída", "Usuário excluído com sucesso.");
                    listarUsuarios();
                    limparCampos();
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível excluir o usuário.");
                }
            }
        });
    }

    @FXML
    private void editar(ActionEvent event) {
        UsuarioDTO selecionado = tblUsuario.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma seleção", "Selecione um usuário para editar.");
            return;
        }

        if (camposVazios()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obrigatórios", "Preencha todos os campos para editar.");
            return;
        }

        selecionado.setLogin(txtLogin.getText());
        selecionado.setNome(txtNome.getText());
        selecionado.setEmail(txtEmail.getText());
        selecionado.setSenha(pswdSenha.getText());

        boolean sucesso = new UsuarioDAO().atualizarUsuario(selecionado);

        if (sucesso) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Atualização realizada", "Usuário atualizado com sucesso.");
            listarUsuarios();
            limparCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível atualizar o usuário.");
        }
    }

    private void listarUsuarios() {
        tblUsuario.getItems().setAll(new UsuarioDAO().listarUsuarios());
    }

    private void limparCampos() {
        txtLogin.clear();
        txtNome.clear();
        txtEmail.clear();
        pswdSenha.clear();
    }

    private boolean camposVazios() {
        return txtLogin.getText().isEmpty() ||
               txtNome.getText().isEmpty() ||
               txtEmail.getText().isEmpty() ||
               pswdSenha.getText().isEmpty();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
