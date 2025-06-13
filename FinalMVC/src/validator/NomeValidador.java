package validator;

import model.dto.UsuarioDTO;

public class NomeValidador implements Validador<UsuarioDTO> {
    private String mensagemErro;

    @Override
    public boolean validar(UsuarioDTO usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            mensagemErro = "O nome é obrigatório.";
            return false;
        }
        return true;
    }

    @Override
    public String obterMensagemErro() {
        return mensagemErro;
    }
}
