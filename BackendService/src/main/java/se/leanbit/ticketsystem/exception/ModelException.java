package se.leanbit.ticketsystem.exception;

public class ModelException extends IllegalArgumentException
{
	private static final long serialVersionUID = 1L;

	public ModelException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public ModelException(final String message)
	{
		super(message);
	}
}
