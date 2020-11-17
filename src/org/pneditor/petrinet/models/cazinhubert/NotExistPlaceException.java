package org.pneditor.petrinet.models.cazinhubert;

public class NotExistPlaceException extends Exception {

	public NotExistPlaceException()
	{
		super("The place doesn't exist");
	}
}
