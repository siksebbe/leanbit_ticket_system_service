package se.leanbit.ticketsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import se.leanbit.ticketsystem.exception.IssueServiceException;
import se.leanbit.ticketsystem.model.Issue;
import se.leanbit.ticketsystem.repository.IssueRepository;
import se.leanbit.ticketsystem.service.interfaces.IssueServiceInterface;

public class IssueService implements IssueServiceInterface
{
	@Autowired
	private IssueRepository issueRepository;
	
	public Issue addIssue(final Issue issue)
	{
		try
		{
			return issueRepository.save(issue);
		}
		catch (final Exception exception)
		{
			throw new IssueServiceException("IssueService: Could not save Issue!", exception);
		}
	}
	
	public Issue getIssue(final long id)
	{
		try
		{
			return issueRepository.findOne(id);
		}
		catch (final Exception exception)
		{
			throw new IssueServiceException("IssueService: Could not getIssue!", exception);
		}
	}
	
	@Override
	public Issue updateIssue(Issue issue, Long issueID)
	{
		return null;
	}
	
	public Issue updateIssue(final Issue issue)
	{
		try
		{
			return issueRepository.save(issue);
		}
		catch (final Exception exception)
		{
			throw new IssueServiceException("IssueService: Could not updateIssue!", exception);
		}
	}
	
	public void removeIssue(final long id)
	{
		try
		{
			issueRepository.delete(id);
		}
		catch (final Exception exception)
		{
			throw new IssueServiceException("IssueService: Could not removeIssue!", exception);
		}
	}
	
	public List<Issue> getAllIssuesNotPaged()
	{
		try
		{
			return issueRepository.getAllIssueNotPaged();
		}
		catch (final Exception exception)
		{
			throw new IssueServiceException("IssueService: Could not get Issues", exception);
		}
	}
	
	public Page<Issue> getAllIssues(int pageNumber, int itemNumbers)
	{
		try
		{
			return issueRepository.findAll(new PageRequest(pageNumber, itemNumbers));
		}
		catch (final Exception exception)
		{
			throw new IssueServiceException("IssueService: Could not get Issues", exception);
		}
	}
}
