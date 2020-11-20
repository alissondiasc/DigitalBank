package com.banco.repository;

import com.banco.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT us FROM Usuario us WHERE us.cpf =:cpf ")
    Usuario obterUsuarioPorCpf(@Param("cpf") String cpf);
}
