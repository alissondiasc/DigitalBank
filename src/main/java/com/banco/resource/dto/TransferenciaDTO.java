package com.banco.resource.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TransferenciaDTO {
    private Integer valor;
    private String senha;
    private String numeroContaOrigem;
    private String numeroCcontaDestino;
}
