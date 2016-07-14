package se.leanbit.ticketsystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.leanbit.ticketsystem.model.Issue;

public interface IssueRepository extends PagingAndSortingRepository<Issue, Long>
{
	
	@Query("select i from Issue i")
	List<Issue> getAllIssueNotPaged();
	
	Page findAll(Pageable pageable);
}
