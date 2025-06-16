package validator;
import model.dto.UsuarioDTO;

public class ValidadorCamposObrigatoriosUsuario implements Validador<UsuarioDTO>{
      private String mensagemErro;

    @Override
    public boolean validar(UsuarioDTO usuario) {
        if (usuario.getNome().isEmpty() &&
            usuario.getEmail().isEmpty() &&
            usuario.getLogin().isEmpty() &&
            usuario.getSenha().isEmpty()) {

            mensagemErro = "Todos os campos devem ser preenchidos.";
            return false;
        }
        return true;
    }

    @Override
    public String obterMensagemErro() {
         return mensagemErro;
    }

    
}
