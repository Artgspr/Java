/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validator;

import model.dto.UsuarioDTO;

public class LoginValidador implements Validador<UsuarioDTO> {

    private String mensagemErro;

    @Override
    public boolean validar(UsuarioDTO usuario) {
        String login = usuario.getLogin();

        if (login == null || login.trim().isEmpty()) {
            mensagemErro = "O login é obrigatório.";
            return false;
        }

        if (login.contains(" ")) {
            mensagemErro = "O login não pode conter espaços.";
            return false;
        }

        return true;
    }

    @Override
    public String obterMensagemErro() {
        return mensagemErro;
    }
}
