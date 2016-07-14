package se.leanbit.ticketsystem;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import se.leanbit.ticketsystem.config.InfraStructureConfig;
import se.leanbit.ticketsystem.model.Issue;
import se.leanbit.ticketsystem.model.Team;
import se.leanbit.ticketsystem.model.User;
import se.leanbit.ticketsystem.model.WorkItem;
import se.leanbit.ticketsystem.repository.IssueRepository;
import se.leanbit.ticketsystem.repository.TeamRepository;
import se.leanbit.ticketsystem.repository.UserRepository;
import se.leanbit.ticketsystem.repository.WorkItemRepository;
import se.leanbit.ticketsystem.service.TeamService;
import se.leanbit.ticketsystem.service.TicketSystemService;
import se.leanbit.ticketsystem.service.UserService;

import java.util.List;

public class Main
{
	public static void main(String[] args)
	{
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext())
		{
			context.scan("se.leanbit.ticketsystem.config");
			context.refresh();

			TicketSystemService ts = context.getBean(TicketSystemService.class);

			List<User> users = ts.getAllUsersNotPaged();
			for(User user: users)
			{
				System.out.println(user);
			}
		}
		
	}
}
