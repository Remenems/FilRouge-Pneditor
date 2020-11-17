package org.pneditor.petrinet.models.cazinhubert;

public class NotExistTransitionException extends Exception {
	
	public NotExistTransitionException()
	{
		super("The transition doesn't exist");
	}

}
