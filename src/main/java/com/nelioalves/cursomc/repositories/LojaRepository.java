package com.nelioalves.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nelioalves.cursomc.domain.Loja;
import com.nelioalves.cursomc.domain.enums.StatusLoja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Integer> {

	@Transactional(readOnly=true)
	public Page<Loja> findBySituacao(Integer situacao, Pageable pageRequest);
}
