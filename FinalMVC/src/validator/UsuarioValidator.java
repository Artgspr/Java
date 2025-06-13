package validator;

import java.util.ArrayList;
import java.util.List;
import model.dto.UsuarioDTO;

public class UsuarioValidator implements IUsuarioValidator {

    private final List<Validador<UsuarioDTO>> validadores = new ArrayList<>();
    private final List<String> mensagensErro = new ArrayList<>();

    public UsuarioValidator() {
        validadores.add(new ValidadorCamposObrigatoriosUsuario());
        validadores.add(new NomeValidador());
        validadores.add(new EmailValidador());
        validadores.add(new SenhaValidador());
        validadores.add(new LoginValidador());
    }
    
    @Override
    public boolean validar(UsuarioDTO usuario) {
        mensagensErro.clear();

        Validador<UsuarioDTO> validadorCamposObrigatorios = validadores.get(0);
        if (!validadorCamposObrigatorios.validar(usuario)) {
            mensagensErro.add(validadorCamposObrigatorios.obterMensagemErro());
            return false;  // se falhar, retorna false e não executa as outras validações
        }

        for (Validador<UsuarioDTO> validador : validadores) {
            if (!validador.validar(usuario)) {
                mensagensErro.add(validador.obterMensagemErro());
            }
        }

        return mensagensErro.isEmpty();
    }

    @Override
    public String obterMensagemErro() {
        return String.join("\n", mensagensErro);
    }

    @Override
    public boolean validarTermoPesquisa(String termo) {
        return termo != null && !termo.trim().isEmpty();
    }

}
