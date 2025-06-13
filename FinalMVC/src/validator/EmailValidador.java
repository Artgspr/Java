/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validator;

import model.dto.UsuarioDTO;

/**
 *
 * @author cti
 */
public class EmailValidador implements Validador<UsuarioDTO> {
     private String mensagemErro;

   @Override
    public boolean validar(UsuarioDTO usuario) {
        String email = usuario.getEmail();
        if (email == null || email.trim().isEmpty()) {
            mensagemErro = "O email é obrigatório.";
            return false;
        }
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            mensagemErro = "Formato de e-mail inválido.";
            return false;
        }
        return true;
    }

    @Override
    public String obterMensagemErro() {
        return mensagemErro;
    }
    
}
