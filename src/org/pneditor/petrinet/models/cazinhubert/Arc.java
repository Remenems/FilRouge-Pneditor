package org.pneditor.petrinet.models.cazinhubert;
/**
 * La classe Arc est la classe m�re de toutes les classes Arc, g�re les accesseurs et la liaison avec la place
 * 
 * @author Corentin Hubert; R�mi Cazin
 */

public class Arc {
	private Place place;
	
	private String name;
	private static int NB_Arc;
	
	/**Constructeur de la classe**/
	public Arc(Place p, Transition t) {
		this.place = p;
		this.name = "A_"+NB_Arc;
		NB_Arc++;
	}
	
	public Place getPlace()
	{
		/** Retourne la place associ�e � l'arc **/
		return this.place;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj.getClass()!=this.getClass())
			return false;
		else
		{
			Arc a = (Arc) obj; 
			return a.name == this.name;
		}		
	}
}
