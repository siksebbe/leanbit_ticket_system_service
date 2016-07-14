package se.leanbit.ticketsystem.service.interfaces;

import java.util.List;

import se.leanbit.ticketsystem.model.Team;
import se.leanbit.ticketsystem.model.User;

public interface TeamServiceInterface
{
	Team addTeam(final Team team);

	Team getTeam(final String teamName);

	void removeTeam(final String teamName);

	List<Team> getAllTeams();

	List<User> getAllUsersFromTeam(final String teamName);
}
