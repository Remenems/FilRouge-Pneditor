package org.pneditor.petrinet.adapters.cazinhubert;

import org.pneditor.petrinet.AbstractTransition;
import org.pneditor.petrinet.models.cazinhubert.Transition;

public class TransitionAdapter extends AbstractTransition{

	private Transition t;
	
	public TransitionAdapter(Transition t)
	{
		super(t.getName());
		this.t = t;
		
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

}
