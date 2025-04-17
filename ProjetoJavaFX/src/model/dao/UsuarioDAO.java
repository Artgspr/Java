package model.dao;

import java.sql.Connection;
import model.dto.UsuarioDTO;
import model.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ra2357094
 */
public class UsuarioDAO {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    // Método para cadastrar um usuário
    public boolean cadastrarUsuario(UsuarioDTO usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha, login) VALUES (?, ?, ?, ?)";

        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getLogin());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar usuário", e);
            return false;
        }
    }

    // Método para listar todos os usuários
    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection c = new Conexao().getConecta();
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
            logger.log(Level.SEVERE, "Erro ao listar usuários", ex);
        }
        return usuarios;
    }

    // Método para pesquisar usuários por nome ou login
    public List<UsuarioDTO> pesquisarUsuarios(String termo) {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nome ILIKE ? OR login ILIKE ?";

        try (Connection c = new Conexao().getConecta();
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
            logger.log(Level.SEVERE, "Erro ao pesquisar usuários", ex);
        }

        return usuarios;
    }

    // Método para atualizar informações do usuário
    public boolean atualizarUsuario(UsuarioDTO usuario) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, login = ? WHERE id = ?";

        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getLogin());
            ps.setInt(5, usuario.getId());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar usuário", e);
            return false;
        }
    }

    // Método para remover um usuário
    public boolean removerUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao remover usuário", e);
            return false;
        }
    }
}
