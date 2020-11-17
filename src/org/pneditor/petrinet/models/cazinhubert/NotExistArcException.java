package org.pneditor.petrinet.models.cazinhubert;

public class NotExistArcException extends Exception {
	
	public NotExistArcException()
	{
		super("The arc doesn't exist");
	}
}
