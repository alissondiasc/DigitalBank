package com.banco.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SENHA_CONTA")
public class Password implements Serializable {

    private static final long serialVersionUID = 5190278561763153761L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PASSWORD")
    private Integer id;

    @Column(name = "PASSWORD")
    private String senha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO_TITULAR")
    private Usuario titular;

}
