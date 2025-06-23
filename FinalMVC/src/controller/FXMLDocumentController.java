package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.dao.UsuarioDAO;
import javafx.scene.control.TextField;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.UsuarioDTO;
import util.DialogUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.PasswordField;
import validator.IUsuarioValidator;



public class FXMLDocumentController implements Initializable {

    @FXML private TextField txtLogin, txtNome, txtEmail, txtPesquisar;
    @FXML private PasswordField pswdSenha;
    @FXML private TableView<UsuarioDTO> tblUsuario;
    @FXML private TableColumn<UsuarioDTO, String> colLogin, colNome, colEmail;

    private static final Logger LOGGER = Logger.getLogger(FXMLDocumentController.class.getName());
    private IUsuarioValidator usuarioValidator;
    
    public void setUsuarioValidator(IUsuarioValidator usuarioValidator) {
        this.usuarioValidator = usuarioValidator;
    }

    @FXML
    private void cadastrar(ActionEvent event) {
        UsuarioDTO objUsuarioDTO = new UsuarioDTO(
                txtNome.getText(),
                txtEmail.getText(),
                pswdSenha.getText(),
                txtLogin.getText()
        );

        if (!usuarioValidator.validar(objUsuarioDTO)) {
            DialogUtil.mostrarAviso(usuarioValidator.obterMensagemErro());
            return;
        }

        try {
            new UsuarioDAO().cadastrarUsuario(objUsuarioDTO);
            tblUsuario.getItems().add(0, objUsuarioDTO);
            limparCampos();
            DialogUtil.mostrarSucesso("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao cadastrar usuário", e);
            DialogUtil.mostrarErro("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        tblUsuario.getItems().setAll(new UsuarioDAO().listarUsuarios());
    }

    @FXML
    private void pesquisar(ActionEvent event) {
        String termo = txtPesquisar.getText().trim();
        List<UsuarioDTO> usuarios;

        try {
            if (!usuarioValidator.validarTermoPesquisa(termo)) {
                //DialogUtil.mostrarAviso("Digite um termo (nome ou login) válido para pesquisar");
                listarUsuarios();
            }

            usuarios = new UsuarioDAO().pesquisarUsuarios(termo);
            tblUsuario.getItems().setAll(usuarios);
            txtPesquisar.clear();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao pesquisar usuários", e);
            DialogUtil.mostrarErro("Erro ao pesquisar usuários: " + e.getMessage());
        }
    }

    @FXML
    private void editar(ActionEvent event) {
        UsuarioDTO usuarioSelecionado = obterUsuarioSelecionado();

        if (usuarioSelecionado != null) {
            boolean confirmarAlteracao = DialogUtil.mostrarConfirmacao("Confirmação", "Você deseja realmente alterar os dados deste usuário?");

            if (confirmarAlteracao) {
                usuarioSelecionado.setLogin(txtLogin.getText());
                usuarioSelecionado.setNome(txtNome.getText());
                usuarioSelecionado.setEmail(txtEmail.getText());
                usuarioSelecionado.setSenha(pswdSenha.getText());

                if (!usuarioValidator.validar(usuarioSelecionado)) {
                    DialogUtil.mostrarAviso(usuarioValidator.obterMensagemErro());
                    return;
                }

                try {
                    new UsuarioDAO().atualizarUsuario(usuarioSelecionado);
                    listarUsuarios();
                    limparCampos();
                    DialogUtil.mostrarSucesso("Usuário alterado com sucesso!");
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Erro ao alterar usuário", e);
                    DialogUtil.mostrarErro("Erro ao alterar usuário: " + e.getMessage());
                }
            }
        } else {
            DialogUtil.mostrarAviso("Por favor, selecione um usuário para alterar");
        }
    }

    @FXML
    private void excluir(ActionEvent event) {
        UsuarioDTO usuarioSelecionado = tblUsuario.getSelectionModel().getSelectedItem();

        if (usuarioSelecionado != null) {
            boolean confirmarExclusao = DialogUtil.mostrarConfirmacao("Confirmação", "Você deseja realmente excluir este usuário?");

            if (confirmarExclusao) {
                try {
                    new UsuarioDAO().excluirUsuario(usuarioSelecionado.getId());
                    listarUsuarios();
                    limparCampos();
                    DialogUtil.mostrarSucesso("Usuário excluído com sucesso!");
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Erro ao excluir usuário", e);
                    DialogUtil.mostrarErro("Erro ao excluir usuário: " + e.getMessage());
                }
            }
        } else {
            DialogUtil.mostrarAviso("Por favor, selecione um usuário para excluir.");
        }
    }

    private void limparCampos() {
        txtLogin.clear();
        txtNome.clear();
        txtEmail.clear();
        pswdSenha.clear();
    }

    @FXML
    private void limparFormularioUsuario(ActionEvent event) {
        limparCampos();
        tblUsuario.getSelectionModel().clearSelection();
    }

    private UsuarioDTO obterUsuarioSelecionado() {
        return tblUsuario.getSelectionModel().getSelectedItem();
    }

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

}
