package se.leanbit.ticketsystem.model;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import se.leanbit.ticketsystem.model.abstra.ModelEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.*;

@Entity
public class Team extends ModelEntity
{
	@Column(name = "team_name", unique = true)
	@Expose
	String teamName;

	@OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
	@Expose
	private Collection<User> users;

	protected Team()
	{
	}

	public Team(final String teamName)
	{
		this.teamName = teamName;
		users = new ArrayList<>();
	}

	public String getTeamName()
	{
		return this.teamName;
	}

	public void setTeamName(final String teamName)
	{
		this.teamName = teamName;
	}

	public List<User> getTeamMembers()
	{
		return (List<User>) users;
	}

	public List<WorkItem> getAllWorkItems()
	{
		List<WorkItem> workItems = new ArrayList<>();
		for (User user : users)
		{
			workItems.addAll(user.getAllWorkItems());
		}
		return workItems;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	public final boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		} else if (other instanceof Team)
		{
			final Team otherTeam = (Team) other;
			if (this.getId().equals(otherTeam.getId()) && this.getTeamName().equals(otherTeam.getTeamName()))
			{
				return true;
			}
		}
		return false;
	}
}
