package org.pneditor.petrinet.adapters.cazinhubert;

import static org.hamcrest.CoreMatchers.instanceOf;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.models.cazinhubert.Arc;
import org.pneditor.petrinet.models.cazinhubert.ArcBouncer;
import org.pneditor.petrinet.models.cazinhubert.ArcClassic;
import org.pneditor.petrinet.models.cazinhubert.ArcPlaceToTransition;
import org.pneditor.petrinet.models.cazinhubert.ArcTransitionToPlace;
import org.pneditor.petrinet.models.cazinhubert.ArcZero;

public class ArcAdapter extends AbstractArc{

	private Arc arc;
	private AbstractNode source;
    private AbstractNode destination;
    private int multiplicity;
	
	public ArcAdapter(Arc a, AbstractNode source, AbstractNode destination, int multiplicity)
    {
        this.arc = a;
        this.source = source ;
        this.destination = destination;
        if((arc instanceof ArcClassic)||(arc instanceof ArcTransitionToPlace))
			this.multiplicity = multiplicity;
        else
        	this.multiplicity = -1;
        
        if(arc instanceof ArcTransitionToPlace)
        	((TransitionAdapter) source).addArcSortant(this);
        else
        	((TransitionAdapter) destination).addArcEntrant(this);
        	
    }
	
	public Arc getArc()
	{
		return this.arc;
	}
	
	@Override
	public AbstractNode getSource() {
		return this.source;
	}

	@Override
	public AbstractNode getDestination() {
		return this.destination;
	}

	@Override
	public boolean isReset() {
		return this.arc instanceof ArcBouncer;
	}

	@Override
	public boolean isRegular() {
		return (this.arc instanceof ArcClassic)||(this.arc instanceof ArcTransitionToPlace) ;
	}

	@Override
	public boolean isInhibitory() {
		return this.arc instanceof ArcZero;
	}

	@Override
	public int getMultiplicity() throws ResetArcMultiplicityException {
		return this.multiplicity;
		/*try
		{
			if(multiplicity != -1)
				return multiplicity;
			else
				throw new ResetArcMultiplicityException();
		}
		catch(ResetArcMultiplicityException e)
		{
			System.err.println("Impossible de récuperer la multiplicité, arc zero ou arc videur");
		}
		return this.multiplicity;*/
	}

	@Override
	public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
		try {
			if(arc instanceof ArcClassic)
			{
				PlaceAdapter p = (PlaceAdapter) this.source;
				TransitionAdapter t = (TransitionAdapter) this.destination;
				this.multiplicity = multiplicity;
				
				arc = new ArcClassic(multiplicity, p.getPlace(), t.getTransition());
			}
			else if(arc instanceof ArcTransitionToPlace)
			{
				PlaceAdapter p = (PlaceAdapter) this.destination;
				TransitionAdapter t = (TransitionAdapter) this.source;
				this.multiplicity = multiplicity;
				
				arc = new ArcTransitionToPlace(t.getTransition(), p.getPlace(), multiplicity);
			}
			else if(arc instanceof ArcZero)
			{
				PlaceAdapter p = (PlaceAdapter) this.source;
				TransitionAdapter t = (TransitionAdapter) this.destination;
				this.multiplicity = 0;
				
				arc = new ArcZero(p.getPlace(), t.getTransition());
			}
			else if(arc instanceof ArcBouncer)
			{
				PlaceAdapter p = (PlaceAdapter) this.source;
				TransitionAdapter t = (TransitionAdapter) this.destination;
				this.multiplicity = -1;
				
				arc = new ArcBouncer(p.getPlace(), t.getTransition());
			}
			else
				throw new ResetArcMultiplicityException();
		}
		catch(Exception e)
		{
			System.out.println(e);
			System.err.println("Impossible de modifier la multiplicité");
		}
		
		
	}
	
	public void moveTokenArc()
	{
		if(this.arc instanceof ArcPlaceToTransition)
			((ArcPlaceToTransition) arc).moveTokenArc();
		else if(this.arc instanceof ArcTransitionToPlace)
			((ArcTransitionToPlace) arc).fillInArc();
	}
	
	public boolean isEnabled()
	{
		if(this.arc instanceof ArcZero) {
			System.out.println(((PlaceAdapter) source).getTokens());
			return ((PlaceAdapter) source).getTokens() == 0;}
		if(this.arc instanceof ArcPlaceToTransition)
			return ((ArcPlaceToTransition) arc).pullArc();
		else
			return false;
	}

}
