package com.banco.repository;

import com.banco.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

    @Query("SELECT ct FROM Conta ct WHERE ct.numeroConta =:numeroConta ")
    Conta obterContaPorNumeroConta(@Param("numeroConta") String numeroConta);
    Conta findTopByOrderByIdDesc();
}
