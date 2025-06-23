package model.dao;

import util.DialogUtil;
import model.dto.UsuarioDTO;
import model.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;  
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level; 
import java.util.logging.Logger;
import javafx.scene.control.Alert;

public class UsuarioDAO {

    private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class.getName());

    public void cadastrarUsuario(UsuarioDTO usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha, login) VALUES (?, ?, ?, ?)";
        try (Connection c = new Conexao().conectaBD(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getLogin());
            ps.execute();
            //DialogUtil.mostrarSucesso("O Usuário " + usuario.getNome() + " foi cadastrado com sucesso!");
        } catch (SQLException e) {
            //LOGGER.log(Level.SEVERE, "Erro ao cadastrar usuário", e);
            //DialogUtil.mostrarErro("Erro ao cadastrar usuário no banco de dados.");
        }
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("login")
                );
                usuario.setId(rs.getInt("id"));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            //LOGGER.log(Level.SEVERE, "Erro ao listar usuários", ex);
            //DialogUtil.mostrarErro("Erro ao listar usuários.");
        }
        return usuarios;
    }

    public List<UsuarioDTO> pesquisarUsuarios(String termo) {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nome LIKE ? OR login LIKE ?";
        try (Connection c = new Conexao().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + termo + "%");
            ps.setString(2, "%" + termo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioDTO usuario = new UsuarioDTO(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("login")
                );
                usuario.setId(rs.getInt("id"));
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
           // LOGGER.log(Level.SEVERE, "Erro ao pesquisar usuários", ex);
            //DialogUtil.mostrarErro("Erro ao pesquisar usuários.");
        }
        return usuarios;
    }

    public void excluirUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection c = new Conexao().conectaBD(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            //DialogUtil.mostrarSucesso("Usuário " + id + " excluído com sucesso!");
        } catch (SQLException ex) {
            //LOGGER.log(Level.SEVERE, "Erro ao excluir usuário com ID: " + id, ex);
            //DialogUtil.mostrarErro("Erro ao excluir usuário.");
        }
    }

    public void atualizarUsuario(UsuarioDTO usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, login = ? WHERE id = ?";
        try (Connection c = new Conexao().conectaBD(); 
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getLogin());
            ps.setInt(5, usuario.getId());
            ps.executeUpdate();
            //DialogUtil.mostrarSucesso("Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            //LOGGER.log(Level.SEVERE, "Erro ao atualizar usuário com nome: " + usuario.getNome(), e);
            //DialogUtil.mostrarErro("Erro ao atualizar usuário.");
        }
    }
}
