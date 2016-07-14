package se.leanbit.service.interfaces;

import se.leanbit.ticketsystem.model.User;
import se.leanbit.ticketsystem.model.WorkItem;

import javax.ws.rs.core.Response;
import java.util.List;

public interface UserWebServiceInterface
{
	Response addUser(final User user);
	
	Response updateUser(final User user);
	
	Response removeUser(final String userID);
	
	Response getUserWithID(final String userID);
	
	Response getUserWithUserName(final String userName);
	
	Response getUsersWithFirstName(final String firstName);
	
	Response getUsersWithLastName(final String lastName);
	
	Response getUsersByTeamName(final String teamName);
	
	Response getWorkItemsFromUser(final String userID);
	
	Response getUsersWithWorkItem(final String workItem);
	
	Response getAllUsers();
}
