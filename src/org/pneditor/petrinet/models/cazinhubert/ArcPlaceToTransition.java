package org.pneditor.petrinet.models.cazinhubert;
/**
 * La classe abstraite ArcPlaceToTransition g�re les arcs qui partent des places pour aller vers les transitions. 
 * elle intervient �galement dans le tirage des jetons des places.
 * 
 * @author Corentin Hubert, R�mi Cazin
 */
public abstract class ArcPlaceToTransition extends Arc {
	
	public ArcPlaceToTransition(Place p, Transition t)
	{
		/** Lors de la construction, l'arc est associ� � une place. **/
		super(p, t);
		t.addArcEntrant(this);
	}
	
	/** moceTokenArc est la fonction permettant de retirer les jetons d'une place lorsque la transition
	 * li�e � l'arc est tirable.
	 */
	public abstract void moveTokenArc();
	
	/** pullArc est la fonction qui demande � la place si elle a un nombre suffisant de jetons pour �tre tir�e **/
	public abstract boolean pullArc();
}
