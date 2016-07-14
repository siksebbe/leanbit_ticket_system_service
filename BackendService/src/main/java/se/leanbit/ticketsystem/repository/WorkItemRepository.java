package se.leanbit.ticketsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.PagingAndSortingRepository;

import se.leanbit.ticketsystem.model.WorkItem;

import java.util.List;

public interface WorkItemRepository extends PagingAndSortingRepository<WorkItem, Long>
{
	@Query("select w from WorkItem w where w.name = ?1")
	WorkItem getWorkItem(final String name);

	@Transactional
	@Modifying
	@Query("delete from WorkItem w where w.name = ?1")
	void removeWorkItem(final String name);

	@Query("select w from WorkItem w where w.description like %?1%")
	List<WorkItem> getWorkItemWithDescriptionLike(final String description);
	
	@Query("select w from WorkItem w where w.status = 'done'")
	List<WorkItem> getAllDoneWorkItems();
	
	@Query("select w from WorkItem w")
	List<WorkItem> getAllWorkItemsNotPaged();
	
	Page findAll(Pageable pageable);
}