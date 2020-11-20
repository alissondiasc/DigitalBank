package com.banco.entity;

import com.banco.domain.Indicador;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CONTA")
public class Conta implements Serializable {

    private static final long serialVersionUID = 5190278561763153761L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTA")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO_TITULAR")
    private Usuario titular;

    @Column(name = "SALDO_CONTA")
    private Integer saldo;

    @Column(name = "AGENCIA_CONTA")
    private String agencia;

    @Column(name="NUMERO_CONTA", unique=true)
    private String numeroConta;

    @Column(name = "IND_CONTA_BLOQ")
    @Enumerated(EnumType.STRING)
    private Indicador indContaBloqueada;


}
