package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dto.UsuarioDTO;
import model.Conexao;

public class UsuarioDAO {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    public void cadastrar(UsuarioDTO usuario) throws Exception {
        validarUsuario(usuario);
        verificarLoginExistente(usuario.getLogin());
        verificarEmailExistente(usuario.getEmail());
        
        String sql = "INSERT INTO usuario (nome, email, senha, login) VALUES (?, ?, ?, ?)";
        
            try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getLogin());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar usuário", e);
            throw new Exception("Erro ao cadastrar usuário no banco de dados");
        }
    }

    public List<UsuarioDTO> listarTodos() {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(criarUsuarioFromResultSet(rs));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao listar usuários", ex);
        }
        return usuarios;
    }

    public List<UsuarioDTO> pesquisar(String termo) {
        List<UsuarioDTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE nome ILIKE ? OR email ILIKE ? OR login ILIKE ?";

        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String likeTermo = "%" + termo + "%";
            for (int i = 1; i <= 3; i++) {
                ps.setString(i, likeTermo);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(criarUsuarioFromResultSet(rs));
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erro ao pesquisar usuários", ex);
        }
        return usuarios;
    }

    public void atualizar(UsuarioDTO usuario) throws Exception {
        validarUsuario(usuario);
        verificarLoginExistente(usuario.getLogin(), usuario.getId());
        verificarEmailExistente(usuario.getEmail(), usuario.getId());

        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, login = ? WHERE id = ?";

        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getSenha());
            ps.setString(4, usuario.getLogin());
            ps.setInt(5, usuario.getId());

            if (ps.executeUpdate() == 0) {
                throw new Exception("Nenhum usuário foi atualizado");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao atualizar usuário", e);
            throw new Exception("Erro ao atualizar usuário no banco de dados");
        }
    }

    public void remover(int id) throws Exception {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new Exception("Nenhum usuário foi removido");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao remover usuário", e);
            throw new Exception("Erro ao remover usuário do banco de dados");
        }
    }

    // Métodos auxiliares
    private void validarUsuario(UsuarioDTO usuario) throws IllegalArgumentException {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (usuario.getLogin() == null || usuario.getLogin().trim().isEmpty()) {
            throw new IllegalArgumentException("Login é obrigatório");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha é obrigatória");
        }
    }

    private void verificarLoginExistente(String login) throws Exception {
        verificarLoginExistente(login, 0);
    }

    private void verificarLoginExistente(String login, int idIgnorar) throws Exception {
        String sql = "SELECT COUNT(*) FROM usuario WHERE login = ? AND id != ?";
        
        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, login);
            ps.setInt(2, idIgnorar);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new Exception("Login já está em uso por outro usuário");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao verificar login existente", e);
            throw new Exception("Erro ao verificar disponibilidade do login");
        }
    }

    private void verificarEmailExistente(String email) throws Exception {
        verificarEmailExistente(email, 0);
    }

    private void verificarEmailExistente(String email, int idIgnorar) throws Exception {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ? AND id != ?";
        
        try (Connection c = new Conexao().getConecta();
             PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setInt(2, idIgnorar);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new Exception("Email já está em uso por outro usuário");
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao verificar email existente", e);
            throw new Exception("Erro ao verificar disponibilidade do email");
        }
    }

    private UsuarioDTO criarUsuarioFromResultSet(ResultSet rs) throws SQLException {
        UsuarioDTO usuario = new UsuarioDTO(
            rs.getString("nome"),
            rs.getString("email"),
            rs.getString("senha"),
            rs.getString("login")
        );
        usuario.setId(rs.getInt("id"));
        return usuario;
    }
}