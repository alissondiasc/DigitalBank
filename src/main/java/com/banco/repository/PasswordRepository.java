package com.banco.repository;

import com.banco.entity.Conta;
import com.banco.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Integer> {

    @Query("SELECT pw FROM Password pw WHERE pw.titular.id =:idUsuario ")
    List<Password> obterSenhaPorIdUsuario(@Param("idUsuario") Integer idUsuario);
}
