package com.nelioalves.cursomc.com.arivaldo.bolao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nelioalves.cursomc.com.arivaldo.bolao.domain.Bolao;

@Repository
public interface BolaoRepository extends JpaRepository<Bolao, Integer> {

}
