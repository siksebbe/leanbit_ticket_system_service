package se.leanbit.ticketsystem.model;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import se.leanbit.ticketsystem.model.abstra.ModelEntity;

import java.util.Date;

import javax.persistence.*;

@Entity
public class WorkItem extends ModelEntity
{
	@Expose
	@Column(unique = true)
	private String name;
	@Expose
	private String description;
	@Expose
	private String status;
	@Expose
	private int priority;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(unique = false)
	@Expose
	private Issue issue;
	
	@Column(name = "created_date")
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();
	
	@Column(name = "modified_date")
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date modefied = new Date();

	protected WorkItem()
	{
	}

	public WorkItem(final String name, final String description, final String status, final int priority)
	{
		this.name = name;
		this.description = description;
		this.status = status;
		this.priority = priority;
	}

	public WorkItem(final String name, final String description, final String status, final int priority,
			final Issue issue)
	{
		this.name = name;
		this.description = description;
		this.status = status;
		this.priority = priority;
		this.issue = issue;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(final String status)
	{
		this.status = status;
	}

	public int getPriority()
	{
		return this.priority;
	}

	public void setPriority(final int priority)
	{
		this.priority = priority;
	}

	public void setIssue(final Issue issue)
	{
		this.issue = issue;
	}

	public Issue getIssue()
	{
		return this.issue;
	}

	public boolean hasIssue()
	{
		if (null != issue)
		{
			return true;
		}
		return false;
	}
	
	public Date getModefied()
	{
		return modefied;
	}
	
	public void setModefied(Date modefied)
	{
		this.modefied = modefied;
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
		} else if (other instanceof WorkItem)
		{
			final WorkItem otherWorkItem = (WorkItem) other;
			if (this.getId() == otherWorkItem.getId() && this.name.equals(otherWorkItem.name)
					&& this.description.equals(otherWorkItem.description) && this.priority == otherWorkItem.priority)
			{
				if (null != this.issue && null != otherWorkItem.issue)
				{
					if (this.issue.equals(otherWorkItem.issue))
					{
						return true;
					}
					return false;
				} else if (null == this.issue && null == otherWorkItem.issue)
				{
					return true;
				} else
				{
					return false;
				}
			}
		}
		return false;
	}
}
