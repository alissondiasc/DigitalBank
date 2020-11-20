package com.banco.testes.service.impl;

import com.banco.domain.Indicador;
import com.banco.entity.Conta;
import com.banco.entity.Usuario;
import com.banco.repository.ContaRepository;
import com.banco.repository.PasswordRepository;
import com.banco.repository.UsuarioRepository;
import com.banco.resource.dto.ContaDTO;
import com.banco.resource.dto.DepositoDTO;
import com.banco.resource.dto.TransferenciaDTO;
import com.banco.resource.dto.UsuarioDTO;
import com.banco.service.PasswordService;
import com.banco.service.impl.ContaServiceImpl;
import com.banco.util.ObjectMapperUtils;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ContaServiceImplServiceTest {

    @InjectMocks
    private ContaServiceImpl contaService;
    @Mock
    private PasswordService passwordService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordRepository passwordRepository;
    @Mock
    private ContaRepository contaRepository;

    private ContaDTO conta1DTO;
    private ContaDTO conta2DTO;
    private DepositoDTO depositoDTO;
    private TransferenciaDTO transferenciaDTO;


    @Before
    public void setUp() {
        this.transferenciaDTO = TransferenciaDTO.builder()
                .numeroCcontaDestino("101")
                .numeroContaOrigem("100")
                .senha("231360")
                .valor(100)
                .build();
        this.depositoDTO = DepositoDTO.builder()
                .numConta("100")
                .senha("231360")
                .valor(20)
                .build();
        this.conta1DTO = ContaDTO.builder()
                .senha("231360")
                .agencia("44180")
                .usuarioDTO(UsuarioDTO.builder()
                        .nome("Alisson Dias")
                        .cpf("03782383109")
                        .dtNascimento(new Date())
                        .build())
                .build();
        this.conta2DTO = ContaDTO.builder()
                .senha("231360")
                .agencia("44180")
                .usuarioDTO(UsuarioDTO.builder()
                        .nome("Alex Dias")
                        .cpf("03782383110")
                        .dtNascimento(new Date())
                        .build())
                .build();


    }
    @SneakyThrows
    @Test
    public void deveCriarConta() {

        when(this.usuarioRepository.saveAndFlush(any(Usuario.class)))
                .thenReturn(Usuario.builder().id(1).build());
        ResponseEntity<Void> retorno = this.contaService.criar(this.conta1DTO);
        assertThat(retorno).isNotNull();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }

    @SneakyThrows
    @Test
    public void deveDepositarQuantia() {

        when(this.contaRepository.obterContaPorNumeroConta("100"))
                .thenReturn(Conta.builder().saldo(0).titular(Usuario.builder().id(10).cpf(this.conta1DTO.getUsuarioDTO().getCpf()).build()).build());
        when(this.passwordService.obterSenha(10))
                .thenReturn("231360");
        when(this.usuarioRepository.obterUsuarioPorCpf(this.conta1DTO.getUsuarioDTO().getCpf()))
                .thenReturn(Usuario.builder().id(10).cpf(this.conta1DTO.getUsuarioDTO().getCpf()).build());
        ResponseEntity<Void> retorno = this.contaService.depositar(this.depositoDTO);
        assertThat(retorno).isNotNull();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }

    @SneakyThrows
    @Test
    public void deveTransferirQuantia() {

        when(this.contaRepository.obterContaPorNumeroConta(transferenciaDTO.getNumeroContaOrigem()))
                .thenReturn(Conta.builder().saldo(100).titular(Usuario.builder().id(10).cpf(this.conta1DTO.getUsuarioDTO().getCpf()).build()).build());
        when(this.contaRepository.obterContaPorNumeroConta(transferenciaDTO.getNumeroCcontaDestino()))
                .thenReturn(Conta.builder().saldo(50).titular(Usuario.builder().id(11).cpf(this.conta2DTO.getUsuarioDTO().getCpf()).build()).build());
        when(this.passwordService.obterSenha(10))
                .thenReturn("231360");

        when(this.passwordService.obterSenha(10))
                .thenReturn("231360");
        when(this.usuarioRepository.obterUsuarioPorCpf(this.conta1DTO.getUsuarioDTO().getCpf()))
                .thenReturn(Usuario.builder().id(10).cpf(this.conta1DTO.getUsuarioDTO().getCpf()).build());
        ResponseEntity<Void> retorno = this.contaService.transferir(this.transferenciaDTO);
        assertThat(retorno).isNotNull();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }

    @Test
    public void deveAtaulizarStauts() {

        when(this.contaRepository.obterContaPorNumeroConta(transferenciaDTO.getNumeroContaOrigem()))
                .thenReturn(Conta.builder().saldo(100).indContaBloqueada(Indicador.N).titular(Usuario.builder().id(10).cpf(this.conta1DTO.getUsuarioDTO().getCpf()).build()).build());

        when(this.usuarioRepository.obterUsuarioPorCpf(this.conta1DTO.getUsuarioDTO().getCpf()))
                .thenReturn(Usuario.builder().id(10).cpf(this.conta1DTO.getUsuarioDTO().getCpf()).build());
        ResponseEntity<Boolean> retorno = this.contaService.bloquearAndDesbloquear("100");
        assertThat(retorno).isNotNull();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }

    @SneakyThrows
    @Test(expected = ValidationException.class)
    public void deveRetornarErroContaJaExiste() {

        when(this.usuarioRepository.obterUsuarioPorCpf("03782383109"))
                .thenReturn(Usuario.builder().id(1).build());
        when(this.usuarioRepository.saveAndFlush(any(Usuario.class)))
                .thenReturn(Usuario.builder().id(1).build());
        ResponseEntity<Void> retorno = this.contaService.criar(this.conta1DTO);
        assertThat(retorno).isNotNull();
        assertEquals(HttpStatus.OK, retorno.getStatusCode());
    }
}
