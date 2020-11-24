package org.pneditor.petrinet.adapters.cazinhubert;



import java.util.LinkedList;

import org.pneditor.petrinet.AbstractArc;
import org.pneditor.petrinet.AbstractNode;
import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.PetriNetInterface;
import org.pneditor.petrinet.ResetArcMultiplicityException;
import org.pneditor.petrinet.UnimplementedCaseException;
import org.pneditor.petrinet.models.cazinhubert.Arc;
import org.pneditor.petrinet.models.cazinhubert.ArcBouncer;
import org.pneditor.petrinet.models.cazinhubert.ArcClassic;
import org.pneditor.petrinet.models.cazinhubert.ArcPlaceToTransition;
import org.pneditor.petrinet.models.cazinhubert.ArcTransitionToPlace;
import org.pneditor.petrinet.models.cazinhubert.ArcZero;
import org.pneditor.petrinet.models.cazinhubert.NotExistArcException;
import org.pneditor.petrinet.models.cazinhubert.NotExistTransitionException;
import org.pneditor.petrinet.models.cazinhubert.Petrinet;
import org.pneditor.petrinet.models.cazinhubert.Place;
import org.pneditor.petrinet.models.cazinhubert.Transition;

public class PetriNetAdapter extends PetriNetInterface{
	
	private Petrinet pn;
	private LinkedList<TransitionAdapter> transitions;

	public PetriNetAdapter()
	{
		pn = new Petrinet();
		transitions = new LinkedList<TransitionAdapter>();
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
		TransitionAdapter tA = new TransitionAdapter(t);
		this.transitions.add(tA);
		return tA;

	}

	@Override
	public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination) throws UnimplementedCaseException {
		
			if(source instanceof PlaceAdapter && destination instanceof TransitionAdapter)
			{
				PlaceAdapter p = (PlaceAdapter) source;
				TransitionAdapter t = (TransitionAdapter) destination;
				
				pn.addArcClassique(1, p.getPlace(), t.getTransition());
				
				System.out.println("ajout d'un arc p to t");
				System.out.println(pn.getArcPToT());
				return new ArcAdapter(pn.getArcPToT().getLast(), p, t, 1);
			}
			else if(source instanceof TransitionAdapter && destination instanceof PlaceAdapter)
			{
				PlaceAdapter p = (PlaceAdapter) destination;
				TransitionAdapter t = (TransitionAdapter) source;
				
				pn.addPlace(p.getPlace());
				pn.addTransition(t.getTransition());
				
				pn.addArcTToP(t.getTransition(), p.getPlace(), 1);
				
				System.out.println("ajout d'un arc t to p");
				return new ArcAdapter(pn.getArcTToP().getLast(), t, p, 1);
			}
			return null;
			
	}

	@Override
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		try {
			PlaceAdapter p = (PlaceAdapter) place;
			TransitionAdapter t = (TransitionAdapter) transition;
			
			pn.addArcZero(p.getPlace(), t.getTransition());
			System.out.println("ajout d'un arc zero (p to t)");
			return new ArcAdapter(pn.getArcPToT().getLast(), place, transition, -1);
		}
		catch (Exception e) {
			System.err.println("Impossible de créer l'arc zero");
			System.out.println(e);
		}
		return null;
	}

	@Override
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
		try {
			PlaceAdapter p = (PlaceAdapter) place;
			TransitionAdapter t = (TransitionAdapter) transition;
			
			pn.addArcVideur(p.getPlace(), t.getTransition());
			System.out.println("ajout d'un arc videur (p to t)");
			return new ArcAdapter(pn.getArcPToT().getLast(), place, transition, -1);
		}
		catch (Exception e) {
			System.err.println("Impossible de créer l'arc videur");
			System.out.println(e);
		}
		return null;
	}

	@Override
	public void removePlace(AbstractPlace place) {
		try
		{
			PlaceAdapter p = (PlaceAdapter) place;
			pn.removePlace(p.getPlace());
			System.out.println("Place supprimée");
		}
		catch(Exception e)
		{
			System.err.println("Impossible de supprimer la place");
			System.out.println(e);
		}
		
	}

	@Override
	public void removeTransition(AbstractTransition transition){
		try {
			TransitionAdapter t = (TransitionAdapter) transition;
			pn.removeTransition(t.getTransition());
			System.out.println("Transition supprimée");
		}
		catch(Exception e)
		{
			System.err.println("Impossible de supprimer la transition");
			System.out.println(e);
		}
			
	}

	@Override
	public void removeArc(AbstractArc arc) {
		try
		{
			Arc a = ((ArcAdapter) arc).getArc();
			if(a instanceof ArcTransitionToPlace)
			{
				ArcAdapter aA = (ArcAdapter) arc;
				TransitionAdapter tA = (TransitionAdapter)aA.getSource();
				ArcTransitionToPlace attop = (ArcTransitionToPlace) a;
				System.out.println("Arc ttopp supprimé");
				pn.removeArcTToP(attop);
				tA.removeArcSortant(aA);
			}
			else
			{
				ArcAdapter aA = (ArcAdapter) arc;
				TransitionAdapter tA = (TransitionAdapter)aA.getDestination();
				ArcPlaceToTransition aptot = (ArcPlaceToTransition) a;
				System.out.println("Arc ptot supprimé");
				pn.removeArcPToT(aptot);
				tA.removeArcEntrant(aA);
				
			}				
		}
		catch (Exception e) {
			System.err.println("Impossible de supprimer l'arc");
			System.out.println(e);
		}
	}

	@Override
	public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
		try {
			return ((TransitionAdapter) transition).isEnabled();
		} catch (Exception e) {
			System.err.println("Impossible d'évaluer la tirabilité de la transition");
			System.out.println(e);
		}
		return false;
	}

	@Override
	public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
		try {
			((TransitionAdapter) transition).fire();
		}
		catch (Exception e) {
			System.err.println("Impossible de tirer la transition");
			System.out.println(e);
		}
		
	}

}
