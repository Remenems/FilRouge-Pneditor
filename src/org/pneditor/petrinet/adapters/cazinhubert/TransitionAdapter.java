package org.pneditor.petrinet.adapters.cazinhubert;

import java.util.LinkedList;

import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.cazinhubert.Transition;

public class TransitionAdapter extends AbstractTransition{

	private Transition t;
	
	private LinkedList<ArcAdapter> arcsEntrants;
	private LinkedList<ArcAdapter> arcsSortants;
	
	public TransitionAdapter(Transition t)
	{
		super(t.getName());
		this.t = t;
		
		arcsEntrants = new LinkedList<ArcAdapter>();
		arcsSortants = new LinkedList<ArcAdapter>();
		
	}
	
	public TransitionAdapter(String label) {
		super(label);
		t = new Transition();
		t.setName(label);
	}
	
	public Transition getTransition()
	{
		return t;
	}
	
	public void addArcEntrant(ArcAdapter arc)
	{
		this.arcsEntrants.add(arc);
	}
	
	public void addArcSortant(ArcAdapter arc)
	{
		arcsSortants.add(arc);
	}
	
	public void removeArcSortant(ArcAdapter arc)
	{
		arcsSortants.remove(arc);
	}
	
	public void removeArcEntrant(ArcAdapter arc)
	{
		arcsEntrants.remove(arc);
	}
	
	public boolean isEnabled()
	{
		for (ArcAdapter a : arcsEntrants) {
			if(!a.isEnabled())
				return false;
		}
		return true;
	}
	
	public void fire()
	{
		if(this.isEnabled())
		{
			for(ArcAdapter ae : arcsEntrants) {
				ae.moveTokenArc();
			}
			for(ArcAdapter as : arcsSortants)
			{
				as.moveTokenArc();
			}
		}
	}

}
