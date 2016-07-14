package se.leanbit.ticketsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import se.leanbit.ticketsystem.exception.*;
import se.leanbit.ticketsystem.model.Issue;
import se.leanbit.ticketsystem.model.Team;
import se.leanbit.ticketsystem.model.User;
import se.leanbit.ticketsystem.model.Token;
import se.leanbit.ticketsystem.model.WorkItem;
import se.leanbit.ticketsystem.service.interfaces.*;
import se.leanbit.ticketsystem.service.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TicketSystemService implements TicketSystemServiceInterface
{
	@Autowired
	UserService userService;
	@Autowired
	WorkItemService workItemService;
	@Autowired
	TeamService teamService;
	@Autowired
	IssueService issueService;
	@Autowired
	TokenService tokenService;
	
	public TicketSystemService()
	{
	}
	
	// User service methods
	@Override
	public User addUser(final User user)
	{
		if (null != user)
		{
			Team team = null;
			try
			{
				team = teamService.getTeam(user.getTeam().getTeamName());
			}
			catch (final UserServiceException exception)
			{
			}
			
			try
			{
				if (null == team)
				{
					team = addTeam(user.getTeam());
				}
				user.setTeam(team);
				
				try
				{
					user.setPassword(PasswordHash.createHash(user.getPassword()));
				}
				catch (NoSuchAlgorithmException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (InvalidKeySpecException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not add user team: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
			
			try
			{
				userService.addUser(user);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not add user: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
			}
			
			// Returning the addUser object ignores all DB relations??
			return getUserWithID(user.getUserID());
		}
		else
		{
			throw new TicketSystemServiceException("Could not add user: Cannot add an empty user!");
		}
	}
	
	@Override
	public User getUserWithID(final String userID)
	{
		if (!userID.isEmpty())
		{
			try
			{
				return userService.getUserWithID(userID);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not get user with id: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get user with id: empty id!");
		}
	}
	
	@Override
	public User updateUser(final User user)
	{
		if (null != user)
		{
			try
			{
				User newUser = getUserWithID(user.getUserID());
				newUser.setUserName(user.getUserName());
				newUser.setFirstName(user.getFirstName());
				newUser.setLastName(user.getLastName());
				newUser.setPassword(user.getPassword());
				if (null != user.getTeam())
				{
					Team team = null;
					try
					{
						team = getTeam(user.getTeam().getTeamName());
					}
					catch (TeamServiceException e)
					{
					}
					
					if (null != team)
					{
						newUser.setTeam(team);
					}
					else
					{
						team = addTeam(user.getTeam());
						newUser.setTeam(team);
					}
					
				}
				return userService.updateUser(newUser);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not update user: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not update user: Cannot update an empty object!");
		}
	}
	
	@Override
	public void removeUser(final String userID)
	{
		if (!userID.isEmpty())
		{
			try
			{
				userService.removeUser(userID);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not remove user: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not remove user: Cannot remove an empty user!");
		}
	}
	
	@Override
	public User getUserWithUserName(final String userName)
	{
		if (!userName.isEmpty())
		{
			try
			{
				return userService.getUserWithUserName(userName);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get user with username: " + exception.getMessage()
						+ ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get user with username: No username provided!");
		}
	}
	
	public boolean userLogin(String userName, String password)
	{
		User proxyUser = getUserWithUserName(userName);
		try
		{
			if (PasswordHash.validatePassword(password, proxyUser.getPassword()))
			{
				return true;
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvalidKeySpecException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new TicketSystemServiceException("Password validation problems");
	}
	
	@Override
	public List<User> getUsersWithFirstName(final String firstName)
	{
		if (!firstName.isEmpty())
		{
			try
			{
				return userService.getUsersWithFirstName(firstName);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get users with firstname: " + exception.getMessage()
						+ ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get users with firstname: No firstname provided!");
		}
		
	}
	
	@Override
	public List<User> getUsersWithLastName(final String lastName)
	{
		if (!lastName.isEmpty())
		{
			try
			{
				return userService.getUsersWithLastName(lastName);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get users with lastname: " + exception.getMessage()
						+ ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get users with lastname: No lastname provided!");
		}
		
	}
	
	@Override
	public List<User> getUsersByTeamName(final String teamName)
	{
		if (!teamName.isEmpty())
		{
			try
			{
				return userService.getUsersByTeamName(teamName);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get users with teamname: " + exception.getMessage()
						+ ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get users with teamname: No teamname provided!");
		}
		
	}
	
	public List<User> getAllUsersNotPaged()
	{
		try
		{
			return userService.getAllUsersNotPaged();
		}
		catch (final UserServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not get all users: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
	}
	
	public Page<User> getAllUsers(int pageNum, int itemNumbers)
	{
		try
		{
			return userService.getAllUsers(pageNum, itemNumbers);
		}
		catch (final UserServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not get all users: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
	}
	
	@Override
	public List<User> getUsersWithWorkItem(WorkItem workItem)
	{
		if (null != workItem)
		{
			try
			{
				return userService.getUsersWithWorkItem(workItem);
			}
			catch (final UserServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get users with work item: " + exception.getMessage()
						+ ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get users with work item: No work item provided!");
		}
		
	}
	
	// Team service methods
	@Override
	public Team addTeam(final Team team)
	{
		if (null != team)
		{
			try
			{
				teamService.addTeam(team);
			}
			catch (final TeamServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not add team: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
			}
			
			return teamService.getTeam(team.getTeamName());
		}
		else
		{
			throw new TicketSystemServiceException("Could not add team: no team provided!");
		}
	}
	
	@Override
	public Team getTeam(final String teamName)
	{
		if (!teamName.isEmpty())
		{
			try
			{
				return teamService.getTeam(teamName);
			}
			catch (final TeamServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not get team: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get team: No teamname provided!");
		}
	}
	
	@Override
	public void removeTeam(final String teamName)
	{
		if (!teamName.isEmpty())
		{
			try
			{
				List<User> teamMembers = userService.getUsersByTeamName(teamName);
				for (User user : teamMembers)
				{
					user.setTeam(null);
					userService.updateUser(user);
				}
				teamService.removeTeam(teamName);
			}
			catch (final TeamServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not remove team: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not remove team: No teamname provided!");
		}
	}
	
	@Override
	public List<Team> getAllTeams()
	{
		try
		{
			return teamService.getAllTeams();
		}
		catch (final TeamServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not get all teams: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
	}
	
	@Override
	public List<User> getAllUsersFromTeam(final String teamName)
	{
		if (!teamName.isEmpty())
		{
			try
			{
				return teamService.getAllUsersFromTeam(teamName);
			}
			catch (final TeamServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get all users from team: " + exception.getMessage()
						+ ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get all users from team: No teamname provided");
		}
	}
	
	// WorkItems service methods
	@Override
	public WorkItem addWorkItem(final WorkItem workItem)
	{
		if (null != workItem)
		{
			try
			{
				workItemService.addWorkItem(workItem);
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not add work item: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
			
			return workItemService.getWorkItem(workItem.getName());
		}
		else
		{
			throw new TicketSystemServiceException("Could not add work item: Cannot add an empty Workitem!");
		}
	}
	
	@Override
	public WorkItem getWorkItem(final String workItemName)
	{
		if (!workItemName.isEmpty())
		{
			try
			{
				return workItemService.getWorkItem(workItemName);
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not get work item: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get work item: Cannot get an empty item!");
		}
	}
	
	@Override
	public WorkItem updateWorkItem(final WorkItem workItem)
	{
		if (null != workItem)
		{
			try
			{
				WorkItem updateWorkItem = workItemService.getWorkItem(workItem.getName());
				updateWorkItem.setDescription(workItem.getDescription());
				updateWorkItem.setIssue(workItem.getIssue());
				updateWorkItem.setName(workItem.getName());
				updateWorkItem.setPriority(workItem.getPriority());
				updateWorkItem.setStatus(workItem.getStatus());
				return workItemService.updateWorkItem(updateWorkItem);
				
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not update work item: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not update work item: Cannot update with an empty object!");
		}
	}
	
	@Override
	public void removeWorkItem(final WorkItem workItem)
	{
		if (null != workItem)
		{
			try
			{
				workItemService.getWorkItem(workItem.getName());
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not remove work item: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
			
			try
			{
				List<User> workUsers = userService.getUsersWithWorkItem(workItem);
				for (User user : workUsers)
				{
					user.removeWorkItem(workItem);
					userService.updateUser(user);
				}
				
				workItemService.removeWorkItem(workItem);
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException("Could not remove work item: Cannot remove an empty object!",
						exception);
			}
			
		}
	}
	
	@Override
	public List<WorkItem> getWorkItemsFromTeam(final Team team)
	{
		if (null != team)
		{
			try
			{
				getTeam(team.getTeamName());
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not get team: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
			}
			
			try
			{
				List<User> users = getUsersByTeamName(team.getTeamName());
				List<WorkItem> workItems = new ArrayList<>();
				
				for (User currentUser : users)
				{
					workItems.addAll(currentUser.getAllWorkItems());
				}
				return workItems;
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get workitems from team users: "
						+ exception.getMessage() + ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException(
					"Could not get work items from team: Cannot get workitems from an empty team!");
		}
		
	}
	
	@Override
	public User addWorkItemToUser(final String userID, final WorkItem workItem)
	{
		User user;
		if (null != userID)
		{
			if (null != workItem)
			{
				try
				{
					user = getUserWithID(userID);
					WorkItem newWorkItem = getWorkItem(workItem.getName());
					if (null == newWorkItem)
					{
						workItemService.addWorkItem(workItem);
						user.addWorkItem(workItem);
					}
					else
					{
						user.addWorkItem(newWorkItem);
					}
				}
				catch (final WorkItemServiceException exception)
				{
					throw new TicketSystemServiceException("Could not add work item to user: " + exception.getMessage()
							+ ": " + exception.getStackTrace(), exception);
				}
				
				try
				{
					return userService.updateUser(user);
				}
				catch (final WorkItemServiceException exception)
				{
					throw new TicketSystemServiceException("Could not add work item to user: Could not update user: "
							+ exception.getMessage() + ": " + exception.getStackTrace(), exception);
				}
			}
			else
			{
				throw new TicketSystemServiceException(
						"Could not add work item to user: Cannot add an empty work item!");
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not add work item to user: Empty user provided!");
		}
	}
	
	@Override
	public Page<WorkItem> getAllWorkItems(int pageNumber, int itemNumbers)
	{
		try
		{
			return workItemService.getAllWorkItems(pageNumber, itemNumbers);
		}
		catch (final WorkItemServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not get all work items: " + exception.getMessage() + ": " + exception.getStackTrace(),
					exception);
		}
		
	}
	
	public List<WorkItem> getWorkItemsNotPaged()
	{
		try
		{
			return workItemService.getAllWorkItemsNotPaged();
		}
		catch (final WorkItemServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not get all work items: " + exception.getMessage() + ": " + exception.getStackTrace(),
					exception);
		}
		
	}
	
	@Override
	public List<WorkItem> getWorkItemsWithIssue()
	{
		try
		{
			return workItemService.getWorkItemsWithIssue();
		}
		catch (final WorkItemServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not get work item with issue: " + exception.getMessage() + ": " + exception.getStackTrace(),
					exception);
		}
		
	}
	
	@Override
	public List<WorkItem> getAllWorkItemsWithStatus(final String status)
	{
		if (!status.isEmpty())
		{
			try
			{
				return workItemService.getAllWorkItemsWithStatus(status);
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get all work items with issue: "
						+ exception.getMessage() + ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get all work items with issue: No status provided?");
		}
		
	}
	
	@Override
	public List<WorkItem> getWorkItemWithDescriptionLike(final String description)
	{
		if (!description.isEmpty())
		{
			try
			{
				return workItemService.getWorkItemWithDescriptionLike(description);
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get all work items with description: "
						+ exception.getMessage() + ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException(
					"Could not get all work items with descriptioon: No description provided!");
		}
	}
	
	@Override
	public WorkItem changeWorkItemStatus(final WorkItem workItem, final String status)
	{
		if (null != workItem)
		{
			if (!status.isEmpty())
			{
				try
				{
					return workItemService.changeWorkItemStatus(workItem, status);
				}
				catch (final WorkItemServiceException exception)
				{
					throw new TicketSystemServiceException("Could not change work item status: "
							+ exception.getMessage() + ": " + exception.getStackTrace(), exception);
				}
			}
			else
			{
				throw new TicketSystemServiceException("Could not change work item status: Empty status object!");
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not change work item status: Empty work item provided!");
		}
		
	}
	
	@Override
	public List<WorkItem> getWorkItemsFromUser(String userID)
	{
		if (null != userID)
		{
			try
			{
				return userService.getWorkItemsFromUser(userID);
			}
			catch (final WorkItemServiceException exception)
			{
				throw new TicketSystemServiceException("Could not get work items from users: " + exception.getMessage()
						+ ": " + exception.getStackTrace(), exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not get work items from user: Empty user ID provided!");
		}
	}
	
	@Override
	public WorkItem addIssueToWorkItem(final String workItemName, final long issueID)
	{
		WorkItem gotWorkItem = null;
		Issue gotIssue = null;
		try
		{
			gotWorkItem = getWorkItem(workItemName);
			gotIssue = getIssue(issueID);
		}
		catch (final Exception exception)
		{
			throw new TicketSystemServiceException("Could not add issue to work item: Cannot get objects!"
					+ exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
		
		try
		{
			gotWorkItem.setIssue(gotIssue);
			return updateWorkItem(gotWorkItem);
		}
		catch (final Exception exception)
		{
			throw new TicketSystemServiceException("Could not add issue to work item: Cannot update objects!"
					+ exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
	}
	
	public List<WorkItem> doneItemsBetweenTwoDates(Date startDate, Date endDate)
	{
		return workItemService.doneItemsBetweenTwoDates(startDate, endDate);
	}
	
	// Issue service methods
	@Override
	public Issue addIssue(final Issue issue)
	{
		if (null != issue)
		{
			try
			{
				issueService.addIssue(issue);
			}
			catch (final IssueServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not add issue: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
			}
			
			return issueService.getIssue(issue.getId());
		}
		else
		{
			throw new TicketSystemServiceException("Could not add issue: Empty issue provided!");
		}
	}
	
	@Override
	public Issue getIssue(long id)
	{
		try
		{
			return issueService.getIssue(id);
		}
		catch (final IssueServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not getissue: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
	}
	
	public List<Issue> getAllIssuesNotPaged()
	{
		try
		{
			return issueService.getAllIssuesNotPaged();
		}
		catch (final IssueServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not getissue: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
	}
	
	public Page<Issue> getAllIssues(int pageNumber, int itemNumbers)
	{
		try
		{
			return issueService.getAllIssues(pageNumber, itemNumbers);
		}
		catch (final IssueServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not getissue: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
	}
	
	@Override
	public Issue updateIssue(Issue issue, Long issueID)
	{
		if (null != issue)
		{
			try
			{
				Issue updateIssue = issueService.getIssue(issueID);
				updateIssue.setDescription(issue.getDescription());
				updateIssue.setName(issue.getName());
				updateIssue.setPriority(issue.getPriority());
				return issueService.updateIssue(updateIssue);
			}
			catch (final IssueServiceException exception)
			{
				throw new TicketSystemServiceException(
						"Could not update issue: " + exception.getMessage() + ": " + exception.getStackTrace(),
						exception);
			}
		}
		else
		{
			throw new TicketSystemServiceException("Could not update issue: empty issue provided!");
		}
	}
	
	@Override
	public void removeIssue(final long id)
	{
		try
		{
			issueService.removeIssue(id);
		}
		catch (final IssueServiceException exception)
		{
			throw new TicketSystemServiceException(
					"Could not remove issue: " + exception.getMessage() + ": " + exception.getStackTrace(), exception);
		}
	}
	
	public void saveToken(Token token)
	{
		tokenService.addToken(token);
	}
	
	public boolean checkToken(String token)
	{
		if (tokenService.getToken(token) != null)
		{
			return true;
		}
		return false;
	}

	public void removeToken(final String token)
	{
		tokenService.removeToken(token);
	}
}