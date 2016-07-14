package se.leanbit.service.interfaces;

import se.leanbit.ticketsystem.model.Issue;

import javax.ws.rs.core.Response;

public interface IssueWebServiceInterface
{
    Response addIssue(final Issue issue);

    Response getIssue(final long id);

    Response updateIssue(final Issue issue, final long id);

    Response removeIssue(final long id);
}
