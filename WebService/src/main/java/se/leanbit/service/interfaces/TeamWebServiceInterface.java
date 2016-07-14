package se.leanbit.service.interfaces;

import se.leanbit.ticketsystem.model.Team;
import se.leanbit.ticketsystem.model.User;

import javax.ws.rs.core.Response;
import java.util.List;

public interface TeamWebServiceInterface
{
    Response addTeam(final Team team);

    Response getTeam(final String teamName);

    Response removeTeam(final String teamName);

    Response getAllTeams();

    Response getAllUsersFromTeam(final String teamName);
}
