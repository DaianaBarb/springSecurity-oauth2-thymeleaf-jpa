package com.Test.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Test.Entities.CadastroEntity;

@Repository
public interface CadastroRepository extends JpaRepository<CadastroEntity, Long> {

	@Query(value = "SELECT *FROM cadastronovo where nome like %?1% ", nativeQuery = true)
	List<CadastroEntity> FindNome(String nome);

}
