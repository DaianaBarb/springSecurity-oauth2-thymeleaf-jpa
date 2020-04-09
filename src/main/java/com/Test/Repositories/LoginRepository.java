package com.Test.Repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.Test.Entities.LoginEntity;
@Transactional
@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Long> {

	LoginEntity findByLogin(String login);
	LoginEntity findByNome(String nome);
	
	
	@Modifying
	@Query("UPDATE login SET logado =:logado WHERE login =:nome")
	   int ALterLogado(@Param("logado")Boolean logado, @Param("nome")String nome);



}
