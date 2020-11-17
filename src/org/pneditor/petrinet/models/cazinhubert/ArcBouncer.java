package org.pneditor.petrinet.models.cazinhubert;
/**
 * La classe ArcVideur h�rite de ArcPlaceToTransition. Elle concerne tous les arcs sont videurs.
 * Lors du tirage, si la transition est activ�e, elle enl�ve de la place associ�e tous les jetons.
 * @author remic
 *
 */
public class ArcBouncer extends ArcPlaceToTransition {

	public ArcBouncer(Place place, Transition t)
	{
		super(place, t);
	}
	
	@Override
	public boolean pullArc()
	{
		/** renvoie true uniquement si la place associ�e renvoie un nombre de jetons au moins �gal � 1 */
		return super.getPlace().getNbToken() > 0;
	}
	
	@Override
	public void moveTokenArc()
	{
		/** Vide la place associ�e, envoie tous les jetons */
		this.getPlace().removeTokens(this.getPlace().getNbToken());
	}
}
