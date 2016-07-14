package se.leanbit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;

import se.leanbit.service.interfaces.SystemServiceInterface;
import se.leanbit.ticketsystem.exception.TicketSystemServiceException;
import se.leanbit.ticketsystem.model.Issue;
import se.leanbit.ticketsystem.model.LoginInfo;
import se.leanbit.ticketsystem.model.Team;
import se.leanbit.ticketsystem.model.Token;
import se.leanbit.ticketsystem.model.User;
import se.leanbit.ticketsystem.model.WorkItem;
import se.leanbit.ticketsystem.service.TicketSystemService;
import se.leanbit.ticketsystem.service.TokenService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("ticketsystem")
@Produces(
{ MediaType.APPLICATION_JSON })
@Consumes(
{ MediaType.APPLICATION_JSON })
public class SystemService implements SystemServiceInterface
{
	@Context
	public UriInfo uriInfo;
	
	private static TicketSystemService ticketSystemService;
	private static Gson gson = new GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create();
	
	public SystemService()
	{
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("se.leanbit.ticketsystem.config");
		context.refresh();
		ticketSystemService = context.getBean(TicketSystemService.class);
	}
	
	@Override
	@POST
	@Path("/user")
	public Response addUser(final User user)
	{
		try
		{
			ticketSystemService.addUser(user);
			final URI location = uriInfo.getAbsolutePathBuilder().path(user.getUserID()).build();
			return Response.created(location).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@PUT
	@Path("/user")
	public Response updateUser(final User user)
	{
		try
		{
			final User updateUser = ticketSystemService.updateUser(user);
			return Response.status(200).entity(gson.toJson(updateUser)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
		
	}
	
	@Override
	@DELETE
	@Path("/user/{userID}")
	public Response removeUser(@PathParam("userID") final String userID)
	{
		try
		{
			ticketSystemService.removeUser(userID);
			return Response.status(200).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
		
	}
	
	@Override
	@GET
	@Path("/user/{userID}")
	public Response getUserWithID(@PathParam("userID") final String userID)
	{
		try
		{
			final User user = ticketSystemService.getUserWithID(userID);
			return Response.status(200).entity(gson.toJson(user)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/user/username/{userName}")
	public Response getUserWithUserName(@PathParam("userName") final String userName)
	{
		try
		{
			final User user = ticketSystemService.getUserWithUserName(userName);
			return Response.status(200).entity(gson.toJson(user)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
		
	}
	
	@Override
	@GET
	@Path("/user/firstname/{firstName}")
	public Response getUsersWithFirstName(@PathParam("firstName") final String firstName)
	{
		try
		{
			final List<User> users = ticketSystemService.getUsersWithFirstName(firstName);
			return Response.status(200).entity(gson.toJson(users)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/user/lastname/{lastName}")
	public Response getUsersWithLastName(@PathParam("lastName") final String lastName)
	{
		try
		{
			final List<User> users = ticketSystemService.getUsersWithLastName(lastName);
			return Response.status(200).entity(gson.toJson(users)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/user/team/{teamName}")
	public Response getUsersByTeamName(@PathParam("teamName") final String teamName)
	{
		try
		{
			final List<User> users = ticketSystemService.getUsersByTeamName(teamName);
			return Response.status(200).entity(gson.toJson(users)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/user/getall")
	public Response getAllUsers()
	{
		try
		{
			final List<User> users = ticketSystemService.getAllUsersNotPaged();
			return Response.status(200).entity(gson.toJson(users)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@GET
	@Path("/user/getall/{pageNum}/{itemNumbers}")
	public Response getAllUsers(@PathParam("pageNum") int pageNum, @PathParam("itemNumbers") int itemNumbers)
	{
		try
		{
			final Page<User> users = ticketSystemService.getAllUsers(pageNum, itemNumbers);
			List<User> userList = users.getContent();
			return Response.status(200).entity(gson.toJson(userList)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	/*
	 * @GET
	 * 
	 * @Path("/user/login/{userName}/{password}") public Response
	 * userLogin(@PathParam("userName") final String userName,
	 * 
	 * @PathParam("password") final String password){
	 * 
	 * if(ticketSystemService.userLogin(userName, password) == true){
	 * 
	 * return Response.status(200).build(); }else{ return
	 * Response.status(400).build(); } }
	 */
	
	@GET
	@Path("/user/login/{userName}/{password}")
	public Response userLogin(@PathParam("userName") final String userName, @PathParam("password") final String password)
	{
		
		if (ticketSystemService.userLogin(userName, password))
		{
			User proxyUser = ticketSystemService.getUserWithUserName(userName);
			Token token = new Token(UUID.randomUUID().toString(), proxyUser);
			ticketSystemService.saveToken(token);
			return Response.status(200).entity(gson.toJson(token.getToken())).build();
		}
		else
		{
			return Response.status(400).build();
		}
	}
	@DELETE
	@Path("/user/logout/{token}")
	public Response userLogout(@PathParam("token") final String token)
	{
		if(!token.isEmpty())
		{
			ticketSystemService.removeToken(token);
			return Response.status(200).build();
		}
		else
		{
			return Response.status(400).build();
		}
	}
	
	@Override
	@POST
	@Path("/issue")
	public Response addIssue(final Issue issue)
	{
		try
		{
			ticketSystemService.addIssue(issue);
			final URI location = uriInfo.getAbsolutePathBuilder().path("" + issue.getId()).build();
			return Response.created(location).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/issue/{issueID}")
	public Response getIssue(@PathParam("issueID") final long id)
	{
		try
		{
			Issue issue = ticketSystemService.getIssue(id);
			return Response.status(200).entity(gson.toJson(issue)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@GET
	@Path("/issue/getall")
	public Response getAllIssuesNotPaged()
	{
		try
		{
			List<Issue> issues = ticketSystemService.getAllIssuesNotPaged();
			return Response.status(200).entity(gson.toJson(issues)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@GET
	@Path("/issue/getall/{pageNumber}/{itemNumbers}")
	public Response getAllIssues(@PathParam("pageNumber") final int pageNumber,
			@PathParam("itemNumbers") final int itemNumbers)
	{
		try
		{
			Page<Issue> issues = ticketSystemService.getAllIssues(pageNumber, itemNumbers);
			return Response.status(200).entity(gson.toJson(issues.getContent())).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@PUT
	@Path("/issue/{issueID}")
	public Response updateIssue(final Issue issue, @PathParam("issueID") final long issueID)
	{
		try
		{
			Issue updatedIssue = ticketSystemService.updateIssue(issue, issueID);
			return Response.status(200).entity(gson.toJson(updatedIssue)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@DELETE
	@Path("/issue/{issueID}")
	public Response removeIssue(@PathParam("issueID") final long id)
	{
		try
		{
			ticketSystemService.removeIssue(id);
			return Response.status(200).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@POST
	@Path("/team")
	public Response addTeam(final Team team)
	{
		try
		{
			ticketSystemService.addTeam(team);
			final URI location = uriInfo.getAbsolutePathBuilder().path(team.getTeamName()).build();
			return Response.created(location).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/team/{teamName}")
	public Response getTeam(@PathParam("teamName") final String teamName)
	{
		try
		{
			Team team = ticketSystemService.getTeam(teamName);
			return Response.status(200).entity(gson.toJson(team)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@DELETE
	@Path("/team/{teamName}")
	public Response removeTeam(@PathParam("teamName") final String teamName)
	{
		try
		{
			ticketSystemService.removeTeam(teamName);
			return Response.status(200).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/team/getall")
	public Response getAllTeams()
	{
		try
		{
			List<Team> allTeams = ticketSystemService.getAllTeams();
			return Response.status(200).entity(gson.toJson(allTeams)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/team/getallusers/{teamName}") // unnecessary
	public Response getAllUsersFromTeam(@PathParam("teamName") final String teamName)
	{
		try
		{
			List<User> teamMembers = ticketSystemService.getUsersByTeamName(teamName);
			return Response.status(200).entity(gson.toJson(teamMembers)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
}

	@Override
	@POST
	@Path("/workitem")
	public Response addWorkItem(WorkItem workItem)
	{
		try
		{
			ticketSystemService.addWorkItem(workItem);
			final URI location = uriInfo.getAbsolutePathBuilder().path(workItem.getName()).build();
			return Response.created(location).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/workitem/{workItemName}")
	public Response getWorkItem(@PathParam("workItemName") final String workItemName)
	{
		try
		{
			WorkItem workItem = ticketSystemService.getWorkItem(workItemName);
			return Response.status(200).entity(gson.toJson(workItem)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@PUT
	@Path("/workitem")
	public Response updateWorkItem(WorkItem workItem)
	{
		try
		{
			WorkItem updatedWorkItem = ticketSystemService.updateWorkItem(workItem);
			final URI location = uriInfo.getAbsolutePathBuilder().path(updatedWorkItem.getName()).build();
			return Response.created(location).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@DELETE
	@Path("/workitem/{workItemName}")
	public Response removeWorkItem(@PathParam("workItemName") final String workItemName)
	{
		try
		{
			WorkItem workItem = ticketSystemService.getWorkItem(workItemName);
			ticketSystemService.removeWorkItem(workItem);
			return Response.status(200).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@GET
	@Path("/workitem/getall")
	public Response getAllWorkItemsNotPaged()
	{
		try
		{
			List<WorkItem> workItems = ticketSystemService.getWorkItemsNotPaged();
			return Response.status(200).entity(gson.toJson(workItems)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@GET
	@Path("/workitem/getall/{pageNumber}/{itemNumbers}")
	public Response getAllWorkItems(@PathParam("pageNumber") final int pageNumber,
			@PathParam("itemNumbers") final int itemNumbers)
	{
		try
		{
			Page<WorkItem> workItems = ticketSystemService.getAllWorkItems(pageNumber, itemNumbers);
			return Response.status(200).entity(gson.toJson(workItems.getContent())).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/workitem/getallwithissue")
	public Response getWorkItemsWithIssue()
	{
		try
		{
			List<WorkItem> workItems = ticketSystemService.getWorkItemsWithIssue();
			return Response.status(200).entity(gson.toJson(workItems)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@PUT
	@Path("/workitem/addissue/{workItemName}/{issueID}")
	public Response addIssueToWorkItem(@PathParam("workItemName") final String workItemName,
			@PathParam("issueID") final long issueID)
	{
		try
		{
			final WorkItem gotWorkItem = ticketSystemService.addIssueToWorkItem(workItemName, issueID);
			final URI location = uriInfo.getAbsolutePathBuilder().path(gotWorkItem.getName()).build();
			return Response.created(location).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
		
	}
	
	@Override
	@GET
	@Path("/workitem/getallwithstatus/{statusName}")
	public Response getAllWorkItemsWithStatus(@PathParam("statusName") final String statusName)
	{
		try
		{
			List<WorkItem> workItems = ticketSystemService.getAllWorkItemsWithStatus(statusName);
			return Response.status(200).entity(gson.toJson(workItems)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/workitem/descriptionlike/{description}")
	public Response getWorkItemWithDescriptionLike(@PathParam("description") final String description)
	{
		try
		{
			List<WorkItem> workItems = ticketSystemService.getWorkItemWithDescriptionLike(description);
			return Response.status(200).entity(gson.toJson(workItems)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@PUT
	@Path("/workitem/changestatus/{workItemName}/{status}")
	public Response changeWorkItemStatus(@PathParam("workItemName") final String workItemName,
			@PathParam("status") final String status)
	{
		try
		{
			WorkItem updateWorkItem = ticketSystemService.getWorkItem(workItemName);
			updateWorkItem = ticketSystemService.changeWorkItemStatus(updateWorkItem, status);
			return Response.status(200).entity(gson.toJson(updateWorkItem)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("user/workitem/{workItemName}")
	public Response getUsersWithWorkItem(@PathParam("workItemName") final String workItemName)
	{
		try
		{
			WorkItem workItem = ticketSystemService.getWorkItem(workItemName);
			List<User> users = ticketSystemService.getUsersWithWorkItem(workItem);
			return Response.status(200).entity(gson.toJson(users)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/workitem/itemsfromuser/{userID}")
	public Response getWorkItemsFromUser(@PathParam("userID") final String userID)
	{
		try
		{
			List<WorkItem> workItems = ticketSystemService.getWorkItemsFromUser(userID);
			return Response.status(200).entity(gson.toJson(workItems)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@GET
	@Path("/workitem/itemsfromteam/{teamName}")
	public Response getWorkItemsFromTeam(@PathParam("teamName") final Team teamName)
	{
		try
		{
			
			List<WorkItem> workItems = ticketSystemService.getWorkItemsFromTeam(teamName);
			return Response.status(200).entity(gson.toJson(workItems)).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@Override
	@PUT
	@Path("/workitem/addtouser/{userID}")
	public Response addWorkItemToUser(final WorkItem workItem, @PathParam("userID") final String userID)
	{
		try
		{
			final User user = ticketSystemService.getUserWithID(userID);
			ticketSystemService.addWorkItemToUser(userID, workItem);
			final URI location = uriInfo.getAbsolutePathBuilder().path(user.getUserID()).build();
			return Response.created(location).build();
		}
		catch (final TicketSystemServiceException exception)
		{
			return Response.status(400).entity(exception.getMessage()).build();
		}
	}
	
	@GET
	@Path("/workitem/done/{startDate}/{endDate}")
	public Response doneItemsBetweenTwoDates(@PathParam("startDate") final String startDate,
			@PathParam("endDate") final String endDate)
	{
		
		Date end = new Date();
		Date start = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-d");
		try
		{
			start = format.parse(startDate);
			end = format.parse(endDate);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<WorkItem> doneList = ticketSystemService.doneItemsBetweenTwoDates(start, end);
		
		if (doneList != null)
		{
			return Response.status(200).entity(gson.toJson(doneList)).build();
		}
		else
		{
			return Response.status(400).build();
		}
	}
	
	@GET
	@Path("/token/{token}")
	public Response checkToken(@PathParam("token") final String token)
	{
		if(ticketSystemService.checkToken(token)){
		 return Response.status(200).build();	
		}
		return Response.status(400).build();
	}
}
