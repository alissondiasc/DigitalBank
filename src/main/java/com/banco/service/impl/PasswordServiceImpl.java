package com.banco.service.impl;

import com.banco.entity.Password;
import com.banco.entity.Usuario;
import com.banco.repository.PasswordRepository;
import com.banco.repository.UsuarioRepository;
import com.banco.service.PasswordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.Optional;

import static com.banco.util.template.MessageLoader.getMessage;

@AllArgsConstructor
@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    public static final String VALIDACAO_SEM_SENHA = "msg.validacao.outras.msg06";
    private final PasswordRepository passwordRepository;

    @Override
    public void salvarSenhaConta(Integer idUser, String senha) {
        this.passwordRepository
                .saveAndFlush(Password
                        .builder().senha(senha)
                        .titular(Usuario.builder().id(idUser).build()).build());


    }

    @Override
    public String obterSenha(Integer idUser) throws ValidationException {
        Optional<Password> senhas =this.passwordRepository.obterSenhaPorIdUsuario(idUser).stream().reduce((first, second) -> second);
        if(!senhas.isPresent()){
            throw new ValidationException(getMessage(VALIDACAO_SEM_SENHA));
        }
        return senhas.get().getSenha();
    }
}
