package org.pneditor.petrinet.models.cazinhubert;
import java.util.LinkedList;

/**
 *  La classe Transition g�re le fonctionnement des transitions, le tirage et le remplissage des places reli�es par les arcs adjacents
 *  
 * @author Corentin Hubert, R�mi Cazin
 */
public class Transition {
	
	private LinkedList<ArcTransitionToPlace> arcsSortants;
	private LinkedList<ArcPlaceToTransition> arcsEntrants;
	
	private static int NB_Transition;
	private String name;
	
	public Transition()
	{
		/** La transition est construite reli�e � aucune place */
		this.arcsEntrants = new LinkedList<ArcPlaceToTransition>();
		this.arcsSortants = new LinkedList<ArcTransitionToPlace>();
		
		NB_Transition++;
		this.name = "T_"+NB_Transition;
	}
	
	public LinkedList<ArcPlaceToTransition> getArcsEntrants()
	{
		/** retourne la liste des arcs qui arrive dans la transition **/
		
		return this.arcsEntrants;
	}
	
	public LinkedList<ArcTransitionToPlace> getArcsSortants()
	{
		/** retourne la liste des arcs qui partent de la transition **/
		return arcsSortants;
	}
	
	/**Les m�thodes addArcEntrant et addArcSortants ajoutents l'arc pass� en argument aux listes en attribut de la classe */
	public void addArcEntrant(ArcPlaceToTransition arc)
	{
		
		arcsEntrants.add(arc);
	}
	
	public void addArcSortant(ArcTransitionToPlace arc)
	{
		arcsSortants.add(arc);
	}
	
	/** Les m�thodes removeArcSortant et removeArcEntrant enl�vent l'arc pass� en argument aux listes en attribut de la classe 
	 * @throws NotExistArcException */
	public void removeArcSortant(ArcTransitionToPlace arc) throws NotExistArcException
	{
		if(arcsSortants.contains(arc))
			this.arcsSortants.remove(arc);
		else
			throw new NotExistArcException();
	}
	
	public void removeArcEntrant(ArcPlaceToTransition arc) throws NotExistArcException
	{
		if(arcsEntrants.contains(arc))
			this.arcsEntrants.remove(arc);
		else
			throw new NotExistArcException();
	}
	
	/**
	 * Fonction qui v�rifie s'il existe un arc entrant entre la place donn�e en param�tre et la transition 
	 */
	public boolean checkPossibilityEnter(Place place) {
		for (ArcPlaceToTransition arc : arcsEntrants) {
			if (arc.getPlace() == place) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Fonction qui v�rifie s'il existe un arc sortant entre la place donn�e en param�tre et la transition 
	 */
	public boolean checkPossibilityExit(Place place)
	{
		for(ArcTransitionToPlace arc: arcsSortants)
		{
			if(arc.getPlace().equals(place))
				return true;
		}
		return false;
	}
	
	public boolean pullTransition(){
		/**V�rifie si les arcs entrants permettent d'activer la transition (si les places remplissent les conditions) */
		
		for(ArcPlaceToTransition arc : arcsEntrants){
			if (!arc.pullArc()) {return false;}
		}
		
		/** Si c'est v�rifier, on d�place les jetons**/
		for(ArcPlaceToTransition arc : arcsEntrants){
			arc.moveTokenArc();
		}
		
		return true;
	}
	
	public void fillInTransition()
	{
		/** Envoie le nombre de jetons n�cessaires dans chaque arc sortant */
		for(ArcTransitionToPlace arc : arcsSortants)
		{
			arc.fillInArc();
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}
