package org.pneditor.petrinet.models.cazinhubert;
/**
 * La classe ArcZero h�rite de ArcPlaceToTransition. Elle concerne tous les arcs sont z�ro, c'est � dire qui activent la transition uniquement si la place est vide.
 * Lors du tirage, si la transition est activ�e, elle ne fait rien.
 * 
 * @author Corentin Hubert, R�mi Cazin
 *
 */
public class ArcZero extends ArcPlaceToTransition{
	
	public ArcZero(Place place, Transition t)
	{
		super(place, t);
	}
	
	
	@Override
	public boolean pullArc()
	{
		/** Renvoie true uniquement si la place associ�e ne contient aucun jeton */
		return this.getPlace().getNbToken()==0;
	}

	//n'est jamais utilis� car l'arc ne retire pas de jetons.
	@Override
	public void moveTokenArc() {
		// ne fait rien
	}
	
}
