package org.pneditor.petrinet.models.cazinhubert;
/**
 * La classe ArcTransitionToPlace g�re les arcs qui partent des transitions pour aller vers les places.
 * Intervient �galement dans le remplissage des places.
 * @author Corentin Hubert, R�mi Cazin
 *
 */
public class ArcTransitionToPlace extends Arc{
	
	private int poids;
	
	public ArcTransitionToPlace(Transition t, Place p, int poids)
	{
		/** Construit un arc d'un certain poids, en le reliant � un place et une transition**/
		
		super(p, t);
		this.poids = poids;
		t.addArcSortant(this);
	}
	
	public void fillInArc()
	{
		/** s'occupe de remplir la place associ�e � l'arc */
		this.getPlace().addTokens(poids);
	}
}
