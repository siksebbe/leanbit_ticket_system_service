package se.leanbit.ticketsystem.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

import se.leanbit.ticketsystem.model.User;
import se.leanbit.ticketsystem.model.WorkItem;

public interface UserServiceInterface
{
	User addUser(final User user);

	User updateUser(final User user);

	void removeUser(final String userID);

	User getUserWithID(final String userID);

	User getUserWithUserName(final String userName);

	List<User> getUsersWithFirstName(final String firstName);

	List<User> getUsersWithLastName(final String lastName);

	List<User> getUsersByTeamName(final String teamName);

	List<WorkItem> getWorkItemsFromUser(final String userID);

	List<User> getUsersWithWorkItem(final WorkItem workItem);

	Page<User> getAllUsers(int pageNumber, int itemNumbers);
}
