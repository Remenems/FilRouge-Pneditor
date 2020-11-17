package org.pneditor.petrinet.models.cazinhubert;
/**
 * La classe ArcClassique h�rite de ArcPlaceToTransition. Elle concerne tous les arcs qui ne sont ni videurs, ni z�ro.
 * Lors du tirage, si la transition est activ�e, elle enl�ve de la place associ�e le nombre de jetons �quivalent � son poids.
 * @author Corentin Hubert, R�mi Cazin
 *
 */
public class ArcClassic extends ArcPlaceToTransition {

	int poids;
	
	public ArcClassic(int poids, Place p, Transition t)
	{
		/**initialise le poids de l'arc */
		super(p,t);
		this.poids = poids;
	}
	
	@Override
	public void moveTokenArc()
	{
		/** enl�ve le nombre de jetons � la place �quivalent au poids de l'arc **/
		this.getPlace().removeTokens(poids);
	}
	
	@Override
	public boolean pullArc()
	{
		/** Renvoie true uniquement si le nombre de jetons contenues dans la place associ�e est sup�rieur au poids de l'arc */
		return this.getPlace().getNbToken() >= poids;
	}
	
	public int getPoids()
	{
		return this.poids;
	}
}
