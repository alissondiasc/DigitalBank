package com.banco.service;

import javax.xml.bind.ValidationException;

public interface PasswordService {

    void salvarSenhaConta(Integer idUser, String senha);

    String obterSenha(Integer idUser) throws ValidationException;
}
