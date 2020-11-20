package com.banco.resource;

import com.banco.resource.dto.ContaDTO;
import com.banco.resource.dto.DepositoDTO;
import com.banco.resource.dto.TransferenciaDTO;
import com.banco.service.ContaService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@Slf4j
@AllArgsConstructor
@RestController
@SuppressWarnings("java:S4834")
@RequestMapping("conta")
public class ContaResource {
    private ContaService contaService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cria uma conta bancaria"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/criar")
    public ResponseEntity<Void> criar(@RequestBody ContaDTO contaDTO) throws ValidationException {
        return contaService.criar(contaDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Deposita valor"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/depositar")
    public ResponseEntity<Void> depositar(@RequestBody DepositoDTO depositoDTO) throws ValidationException {
        return contaService.depositar(depositoDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Realiza transferencia"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping("/transferir")
    public ResponseEntity<Void> transferir(@RequestBody TransferenciaDTO transferenciaDTO) throws ValidationException {
        return contaService.transferir(transferenciaDTO);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Alterar status de conta"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/alterar-status-cartao/num-cartao/{numCartao}")
    public ResponseEntity<Boolean> bloaquearDesbloaquear(@PathVariable String numCartao) {
        return contaService.bloquearAndDesbloquear(numCartao);
    }

}
