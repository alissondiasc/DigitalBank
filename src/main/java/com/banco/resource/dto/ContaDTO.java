package com.banco.resource.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ContaDTO {
    private UsuarioDTO usuarioDTO;
    private String senha;
    private String agencia;

}
