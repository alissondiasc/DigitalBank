package com.banco.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 5190278961763153781L;

    @Id
    @Column(name = "ID_USUARIO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NOME_USUARIO")
    private String nome;

    @Column(name = "CPF_USUARIO", unique=true)
    private String cpf;

    @Column(name = "DATA_NASC_USUARIO")
    private Date dtNascimento;
}
