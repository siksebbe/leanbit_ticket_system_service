package se.leanbit.ticketsystem.service;

import org.springframework.beans.factory.annotation.Autowired;

import se.leanbit.ticketsystem.model.Token;
import se.leanbit.ticketsystem.repository.TokenRepository;
import se.leanbit.ticketsystem.repository.UserRepository;

public class TokenService
{
	@Autowired
	private TokenRepository tokenRepository;
	
	public Token getToken(final String token)
	{
		return tokenRepository.getToken(token);
	}
	
	public Token addToken(Token token)
	{
		return tokenRepository.save(token);
	}

	public void removeToken(final String token)
	{
		tokenRepository.removeToken(token);
	}
}
