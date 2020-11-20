package com.banco.service;

import com.banco.resource.dto.ContaDTO;
import com.banco.resource.dto.DepositoDTO;
import com.banco.resource.dto.TransferenciaDTO;
import org.springframework.http.ResponseEntity;

import javax.xml.bind.ValidationException;

public interface ContaService {

    ResponseEntity<Void> criar(ContaDTO contaDto) throws ValidationException;

    ResponseEntity<Void> depositar(DepositoDTO depositoDTO) throws ValidationException;

    ResponseEntity<Void> transferir(TransferenciaDTO transferenciaDTO) throws ValidationException;

    ResponseEntity<Boolean> bloquearAndDesbloquear(String numeroConta);
}
