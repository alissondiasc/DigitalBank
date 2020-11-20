package com.banco.service.impl;

import com.banco.domain.Indicador;
import com.banco.entity.Conta;
import com.banco.entity.Usuario;
import com.banco.repository.ContaRepository;
import com.banco.repository.UsuarioRepository;
import com.banco.resource.dto.ContaDTO;
import com.banco.resource.dto.DepositoDTO;
import com.banco.resource.dto.TransferenciaDTO;
import com.banco.service.ContaService;
import com.banco.service.PasswordService;
import com.banco.util.ObjectMapperUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.Objects;

import static com.banco.util.template.MessageLoader.getMessage;
import static com.banco.util.template.MessageLoader.msgErroNaoEncotrado;
import static java.util.Optional.ofNullable;

@AllArgsConstructor
@Service
@Slf4j
public class ContaServiceImpl implements ContaService {

    public static final String VALIDACAO_SENHA_INCORRETA = "msg.validacao.outras.msg07";
    public static final String VALIDACAO_SALDO_INSUFICIENTE = "msg.validacao.conta.msg01";
    public static final String USUARIO_ENCONTRADO = "msg.validacao.conta.msg02";
    public static final String CONTA_NAO_ENCONTRADA= "msg.validacao.conta.msg03";
    public static final String USUARIO_NAO_ENCONTRADA= "msg.validacao.usuario.msg04";

    private final UsuarioRepository usuarioRepository;
    private final ContaRepository contaRepository;
    private final PasswordService passwordService;


    @Override
    public ResponseEntity<Void> criar(ContaDTO contaDto) throws ValidationException {
        if(Objects.nonNull(this.usuarioRepository.obterUsuarioPorCpf(contaDto.getUsuarioDTO().getCpf()))){
            throw new ValidationException(getMessage(USUARIO_ENCONTRADO));
        }
        Usuario usuario = this.usuarioRepository.saveAndFlush(ObjectMapperUtils.map(contaDto.getUsuarioDTO(), Usuario.class));



        this.contaRepository.saveAndFlush(Conta.builder()
                .titular(usuario)
                .indContaBloqueada(Indicador.N)
                .numeroConta(ultimoSequencialConta())
                .saldo(0)
                .agencia(contaDto.getAgencia()).build());
        this.passwordService.salvarSenhaConta(usuario.getId(), contaDto.getSenha());
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<Void> depositar(DepositoDTO depositoDTO) throws ValidationException {
        Conta conta = ofNullable(this.contaRepository.obterContaPorNumeroConta(depositoDTO.getNumConta()))
                .orElseThrow(() -> msgErroNaoEncotrado(CONTA_NAO_ENCONTRADA));
        validarSenha(depositoDTO.getSenha(), conta);
        conta.setSaldo(conta.getSaldo() + depositoDTO.getValor());
        this.contaRepository.saveAndFlush(conta);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> transferir(TransferenciaDTO transferenciaDTO) throws ValidationException {
        Conta contaOrigem =ofNullable(this.contaRepository.obterContaPorNumeroConta(transferenciaDTO.getNumeroContaOrigem()))
                .orElseThrow(() -> msgErroNaoEncotrado(CONTA_NAO_ENCONTRADA));
        Conta contaDestino =ofNullable(this.contaRepository.obterContaPorNumeroConta(transferenciaDTO.getNumeroCcontaDestino()))
                .orElseThrow(() -> msgErroNaoEncotrado(CONTA_NAO_ENCONTRADA));
        if(contaOrigem.getSaldo() == 0 || contaOrigem.getSaldo() < transferenciaDTO.getValor()){
            throw new ValidationException(getMessage(VALIDACAO_SALDO_INSUFICIENTE));
        }
        validarSenha(transferenciaDTO.getSenha(), contaOrigem);

        contaOrigem.setSaldo(contaOrigem.getSaldo() - transferenciaDTO.getValor());
        contaDestino.setSaldo(contaDestino.getSaldo() + transferenciaDTO.getValor());
        this.contaRepository.saveAndFlush(contaDestino);
        this.contaRepository.saveAndFlush(contaOrigem);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Boolean> bloquearAndDesbloquear(String numeroConta) {
        Conta conta =ofNullable(this.contaRepository.obterContaPorNumeroConta(numeroConta))
                .orElseThrow(() -> msgErroNaoEncotrado(CONTA_NAO_ENCONTRADA));
        if (conta.getIndContaBloqueada().getValor().equals(Boolean.TRUE)) {
            conta.setIndContaBloqueada(Indicador.N);
        } else {
            conta.setIndContaBloqueada(Indicador.S);
        }
        this.contaRepository.saveAndFlush(conta);
        return ResponseEntity.ok(conta.getIndContaBloqueada().getValor());
    }

    private void validarSenha(String senha, Conta conta) throws ValidationException {
        if(!this.confirmaTransacaoSenha(conta.getTitular().getCpf(), senha)){
            throw new ValidationException(getMessage(VALIDACAO_SENHA_INCORRETA));
        }
    }
    private String ultimoSequencialConta(){
        Conta a = this.contaRepository.findTopByOrderByIdDesc();
        if(Objects.nonNull(a)){
            Integer num = Integer.parseInt(a.getNumeroConta());
            num = num +1;
            return num.toString();
        }
        return "100";
    }

    private boolean confirmaTransacaoSenha(String cpf, String senha) throws ValidationException {
        Usuario usuario =ofNullable(this.usuarioRepository.obterUsuarioPorCpf(cpf))
                .orElseThrow(() -> msgErroNaoEncotrado(USUARIO_NAO_ENCONTRADA));
        if(!this.passwordService.obterSenha(usuario.getId()).equals(senha)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
