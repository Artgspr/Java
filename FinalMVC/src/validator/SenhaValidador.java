package validator;

import model.dto.UsuarioDTO;

public class SenhaValidador implements Validador<UsuarioDTO> {
    private String mensagemErro;

    @Override
    public boolean validar(UsuarioDTO usuario) {
        String senha = usuario.getSenha();

        if (senha == null || senha.trim().isEmpty()) {
            mensagemErro = "A senha é obrigatória.";
            return false;
        }

        if (senha.length() < 6) {
            mensagemErro = "A senha deve ter pelo menos 6 caracteres.";
            return false;
        }

        return true;
    }

    @Override
    public String obterMensagemErro() {
        return mensagemErro;
    }
}
