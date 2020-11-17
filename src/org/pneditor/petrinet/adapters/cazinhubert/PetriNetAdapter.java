package org.pneditor.petrinet.adapters.cazinhubert;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.PetriNetInterface;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.UnimplementedCaseException;
import org.pneditor.petrinet.models.cazinhubert.Arc;
import org.pneditor.petrinet.models.cazinhubert.ArcClassic;
import org.pneditor.petrinet.models.cazinhubert.ArcTransitionToPlace;
import org.pneditor.petrinet.models.cazinhubert.NotExistArcException;
import org.pneditor.petrinet.models.cazinhubert.NotExistTransitionException;
import org.pneditor.petrinet.models.cazinhubert.Petrinet;
import org.pneditor.petrinet.models.cazinhubert.Place;
import org.pneditor.petrinet.models.cazinhubert.Transition;

public class PetriNetAdapter extends PetriNetInterface{
	
	private Petrinet pn;

	public PetriNetAdapter()
	{
		this.pn = new Petrinet();
	}
	
	@Override
	public AbstractPlace addPlace() {
		Place p = new Place();   
		pn.addPlace(p);
		System.out.println("Ajout de place");
		return new PlaceAdapter(p);
	}

	@Override
	public AbstractTransition addTransition() {
		Transition t = new Transition();
		pn.addTransition(t);
		System.out.println("Ajout de transition");
		return new TransitionAdapter(t);

	}

	@Override
	public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
		try
		{
			if(source instanceof PlaceAdapter && destination instanceof TransitionAdapter)
			{
				PlaceAdapter p = (PlaceAdapter) source;
				TransitionAdapter t = (TransitionAdapter) destination;
				
				pn.addPlace(p.getPlace());
				pn.addTransition(t.getTransition());
				
				pn.addArcClassique(1, p.getPlace(), t.getTransition());
				
				System.out.println("ajout d'un arc p to t");
				return new ArcAdapter(new ArcClassic(1, p.getPlace(), t.getTransition()), p, t);
			}
			else if(source instanceof TransitionAdapter && destination instanceof PlaceAdapter)
			{
				PlaceAdapter p = (PlaceAdapter) destination;
				TransitionAdapter t = (TransitionAdapter) source;
				
				pn.addPlace(p.getPlace());
				pn.addTransition(t.getTransition());
				
				pn.addArcTToP(t.getTransition(), p.getPlace(), 1);
				
				System.out.println("ajout d'un arc t to p");
				return new ArcAdapter(new ArcTransitionToPlace(t.getTransition(), p.getPlace(), 1), t, p);
			}
			throw new UnimplementedCaseException("");
				
		}
		catch(Exception e)
		{
			System.err.println("Impossible de créer l'arc classique");
		}
		return null;
	}

	@Override
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePlace(AbstractPlace place) {
		try
		{
			PlaceAdapter p = (PlaceAdapter) place;
			pn.removePlace(p.getPlace());
		}
		catch(Exception e)
		{
			System.err.println("Impossible de supprimer la place");
		}
		
	}

	@Override
	public void removeTransition(AbstractTransition transition){
		try {
			TransitionAdapter t = (TransitionAdapter) transition;
			pn.removeTransition(t.getTransition());
		}
		catch(Exception e)
		{
			System.err.println("Impossible de supprimer la transition");
		}
			
	}

	@Override
	public void removeArc(AbstractArc arc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
		// TODO Auto-generated method stub
		
	}

}
