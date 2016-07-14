package se.leanbit.ticketsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import se.leanbit.ticketsystem.model.abstra.ModelEntity;

@Entity
public class Token extends ModelEntity
{
	@Column(unique = true)
	private String token;
	
	@OneToOne
	private User user;
	
	public Token(){
		super();
	};
	
	public Token(String token, User user){
		this.token = token;
		this.user = user;
	};
	
	public String getToken()
	{
		return token;
	}
}
