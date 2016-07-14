package se.leanbit.ticketsystem.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.transaction.annotation.Transactional;
import se.leanbit.ticketsystem.model.Token;

public interface TokenRepository extends PagingAndSortingRepository<Token, Long>
{
	
	@Query("select t from Token t where t.token = ?1")
	Token getToken(final String token);

	@Transactional
	@Modifying
	@Query("delete from Token t where t.token = ?1")
	void removeToken(final String token);
}
