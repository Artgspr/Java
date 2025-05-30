package controller;

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

    @FXML private TextField txtLogin, txtNome, txtEmail;
    @FXML private PasswordField pswdSenha;
    @FXML private TextArea txtPesquisar;
    @FXML private TableView<UsuarioDTO> tblUsuario;
    @FXML private TableColumn<UsuarioDTO, String> colLogin, colNome, colEmail;
    
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabela();
        listarUsuarios();
        configurarSelecaoTabela();
    }

    private void configurarTabela() {
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void configurarSelecaoTabela() {
        tblUsuario.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            if (selecionado != null) {
                preencherCampos(selecionado);
            }
        });
    }

    @FXML
    private void cadastrar(ActionEvent event) {
        try {
            validarCampos();
            UsuarioDTO usuario = criarUsuarioFromForm();
            usuarioDAO.cadastrar(usuario);
            mostrarMensagemSucesso("Cadastro realizado", "Usuário cadastrado com sucesso.");
            atualizarListaELimparCampos();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos obrigatórios", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível cadastrar o usuário: " + e.getMessage());
        }
    }

    @FXML
    private void pesquisar(ActionEvent btnPesquisar) {
        String termo = txtPesquisar.getText().trim();
        List<UsuarioDTO> usuarios = termo.isEmpty() 
            ? usuarioDAO.listarTodos() 
            : usuarioDAO.pesquisar(termo);
        tblUsuario.getItems().setAll(usuarios);
    }

    @FXML
    private void excluir(ActionEvent event) {
        UsuarioDTO selecionado = tblUsuario.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Nenhuma seleção", "Selecione um usuário para excluir.");
            return;
        }

        mostrarConfirmacao("Confirmação", "Tem certeza que deseja excluir este usuário?", () -> {
            try {
                usuarioDAO.remover(selecionado.getId());
                mostrarMensagemSucesso("Exclusão concluída", "Usuário excluído com sucesso.");
                atualizarListaELimparCampos();
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível excluir o usuário: " + e.getMessage());
            }
        });
    }

    @FXML
    private void editar(ActionEvent event) {
        try {
            UsuarioDTO selecionado = tblUsuario.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                throw new IllegalArgumentException("Selecione um usuário para editar.");
            }
            
            validarCampos();
            atualizarUsuarioFromForm(selecionado);
            usuarioDAO.atualizar(selecionado);
            mostrarMensagemSucesso("Atualização realizada", "Usuário atualizado com sucesso.");
            atualizarListaELimparCampos();
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível atualizar o usuário: " + e.getMessage());
        }
    }

    // Métodos auxiliares
    private void validarCampos() {
        if (txtLogin.getText().isEmpty() || txtNome.getText().isEmpty() || 
            txtEmail.getText().isEmpty() || pswdSenha.getText().isEmpty()) {
            throw new IllegalArgumentException("Por favor, preencha todos os campos.");
        }
    }

    private UsuarioDTO criarUsuarioFromForm() {
        return new UsuarioDTO(
            txtNome.getText(),
            txtEmail.getText(),
            pswdSenha.getText(),
            txtLogin.getText()
        );
    }

    private void atualizarUsuarioFromForm(UsuarioDTO usuario) {
        usuario.setLogin(txtLogin.getText());
        usuario.setNome(txtNome.getText());
        usuario.setEmail(txtEmail.getText());
        usuario.setSenha(pswdSenha.getText());
    }

    private void preencherCampos(UsuarioDTO usuario) {
        txtLogin.setText(usuario.getLogin());
        txtNome.setText(usuario.getNome());
        txtEmail.setText(usuario.getEmail());
        pswdSenha.setText(usuario.getSenha());
    }

    private void listarUsuarios() {
        tblUsuario.getItems().setAll(usuarioDAO.listarTodos());
    }

    private void atualizarListaELimparCampos() {
        listarUsuarios();
        limparCampos();
    }

    @FXML
    private void limparCampos() {
        txtLogin.clear();
        txtNome.clear();
        txtEmail.clear();
        pswdSenha.clear();
    }

    private void mostrarMensagemSucesso(String titulo, String mensagem) {
        mostrarAlerta(Alert.AlertType.INFORMATION, titulo, mensagem);
    }

    private void mostrarConfirmacao(String titulo, String mensagem, Runnable acaoConfirmacao) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                acaoConfirmacao.run();
            }
        });
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}