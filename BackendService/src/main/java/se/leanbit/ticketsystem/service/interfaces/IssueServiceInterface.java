package se.leanbit.ticketsystem.service.interfaces;

import se.leanbit.ticketsystem.model.Issue;

public interface IssueServiceInterface
{
	Issue addIssue(final Issue issue);

	Issue getIssue(final long id);

	Issue updateIssue(Issue issue, Long issueID);

	void removeIssue(final long id);
}
