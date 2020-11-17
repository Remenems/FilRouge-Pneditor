package org.pneditor.petrinet.adapters.cazinhubert;

import org.pneditor.petrinet.AbstractPlace;
import org.pneditor.petrinet.models.cazinhubert.Place;

public class PlaceAdapter extends AbstractPlace{

	private Place p;
	
	public PlaceAdapter(String label) {
		super(label);
		p = new Place();
		p.setName(label);
	}
	
	public PlaceAdapter(Place p)
	{
		super(p.getName());
		this.p = p;
	}

	@Override
	public void addToken() {
		p.addTokens(1);
		
	}

	@Override
	public void removeToken() {
		p.removeTokens(1);
		
	}

	@Override
	public int getTokens() {
		return p.getNbToken();
	}

	@Override
	public void setTokens(int tokens) {
		int nbtoken = p.getNbToken();
		if(nbtoken - tokens > 0 )
			p.addTokens(nbtoken - tokens);
		else if(nbtoken - tokens < 0)
			p.removeTokens(nbtoken - tokens);
		else
			return;
		
	}

}
