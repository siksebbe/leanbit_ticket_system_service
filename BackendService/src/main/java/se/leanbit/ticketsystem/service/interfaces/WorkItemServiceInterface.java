package se.leanbit.ticketsystem.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import se.leanbit.ticketsystem.model.WorkItem;

public interface WorkItemServiceInterface
{
	WorkItem addWorkItem(final WorkItem workItem);

	WorkItem getWorkItem(final String name);

	WorkItem updateWorkItem(final WorkItem workItem);

	void removeWorkItem(final WorkItem workItem);

	Page<WorkItem> getAllWorkItems(int pageNumber, int itemNumbers);

	List<WorkItem> getWorkItemsWithIssue();

	List<WorkItem> getAllWorkItemsWithStatus(final String status);

	List<WorkItem> getWorkItemWithDescriptionLike(final String description);

	WorkItem changeWorkItemStatus(WorkItem workItem, final String status);
}
