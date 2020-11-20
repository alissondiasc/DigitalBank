package com.banco.resource.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DepositoDTO {
    private Integer valor;
    private String numConta;
    private String senha;
}
