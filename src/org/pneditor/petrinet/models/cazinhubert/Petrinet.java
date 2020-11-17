package org.pneditor.petrinet.models.cazinhubert;
/**
 * La classe Petrinet a pour objectif de g�rer la construction d'un Petrinet, elle re�oit les informations
 * de l'utilisateur, les traite et renvoie la solution � chaque �tape.
 * 
 * @author Corentin Hubert, R�mi Cazin
 */

import java.util.LinkedList;

import java.util.Collections;

public class Petrinet {
	
	/** Les attributs de la classe sont les listes des �l�ments qui forme le Petrinet,
	c'est � dire les places, les diff�rents arcs et les transitions**/
	private LinkedList<Place> places;
	private LinkedList<ArcTransitionToPlace> arcsTToP;
	private LinkedList<ArcPlaceToTransition> arcsPToT;
	private LinkedList<Transition> transitions;
	private LinkedList<Transition> transitionsActivated;
	
	/** Constructeur de la classe **/
	public Petrinet() {
		this.places = new LinkedList<Place>();
		this.arcsTToP = new LinkedList<ArcTransitionToPlace>();
		this.arcsPToT = new LinkedList<ArcPlaceToTransition>();
		this.transitions = new LinkedList<Transition>();
		this.transitionsActivated = new LinkedList<Transition>();
	}
	
	/**
	 * Les trois prochaines fonctions permettent de cr�er une nouvelle place, par d�faut on cr�e une place sans
	 * jetons, si un entier est mis en param�tre, alors on met un nombre de jetons dans la place �gale � cet entier
	 * On peut aussi choisir d'ajouter une place cr�� pr�alablement.
	 */
	public void addPlace() {
		Place place = new Place();
		this.places.add(place);
	}
	public void addPlace(int nbTokenInit) throws NegativeTokensException {
		Place place = new Place(nbTokenInit);
		this.places.add(place);
	}
	
	public void addPlace(Place p)
	{
		this.places.add(p);
	}
	
	/**Fonction permettant de retirer une place, ainsi que tous les arcs qui lui sont associ�s
	 * @throws NotExistPlaceException 
	 * @throws NotExistArcException **/
	public void removePlace(Place place) throws NotExistPlaceException, NotExistArcException {
		if(places.contains(place))
		{
			this.places.remove(place);
			for (ArcTransitionToPlace arc: arcsTToP) { //on retire les premiers arcs qui lui sont associ�s
				if (arc.getPlace() == place) {
					removeArcTToP(arc);
				}
			}
			for (ArcPlaceToTransition arc: arcsPToT){ //on retire les arcs restants
				if (arc.getPlace() == place) {
					removeArcPToT(arc);
				}
			}
		}
		else
			throw new NotExistPlaceException();
		
	}
	
	/**Fonction permettant de cr�er un arc reliant une transition avec une place
	 * On ajoute l'arc � la liste de la transition
	 */
	public void addArcTToP(Transition transition, Place place, int poids) {
		if(!transition.checkPossibilityExit(place)) //Uniquement s'il existe pas d'arc sortant de la transition vers la place
		{
			ArcTransitionToPlace arc = new ArcTransitionToPlace(transition, place, poids);
			this.arcsTToP.add(arc);
		}
		
	}
	
	/**
	 * Fonction permettant de retirer un arc reliant une transition avec une place,
	 * @throws NotExistArcException 
	 */
	public void removeArcTToP(ArcTransitionToPlace arc) throws NotExistArcException {
		if(arcsTToP.contains(arc))
		{
			this.arcsTToP.remove(arc);
			for (Transition transition: transitions) { //on retire l'arc de la liste des arcs de la transition
				for (ArcTransitionToPlace arcTransition: transition.getArcsSortants()) {
					if (arc == arcTransition) {
						transition.removeArcSortant(arc);
					}
				}
			}
		}
		else
		{
			throw new NotExistArcException();
		}
	}
	
	/**
	 * la classe Petrinet peut cr�er des arcs videurs, z�ros ou classiques reliant une place � une transition 
	 * � partir des trois fonctions suivantes. Cependant, une fois qu'ils sont cr��s, la classe ne sait plus 
	 * faire la distinction entre ces trois types d'arc.
	 */
	public void addArcVideur(Place place, Transition transition) {
		if (!transition.checkPossibilityEnter(place)) {
			ArcPlaceToTransition arc = new ArcBouncer(place, transition);
			this.arcsPToT.add(arc);
		}
	}
	
	public void addArcZero(Place place, Transition transition) {
		if (!transition.checkPossibilityEnter(place)) {
			ArcPlaceToTransition arc = new ArcZero(place, transition);
			this.arcsPToT.add(arc);
		}
	}
	
	public void addArcClassique(int poids, Place place, Transition transition) {
		if (!transition.checkPossibilityEnter(place)) {
			ArcPlaceToTransition arc = new ArcClassic(poids, place, transition);
			this.arcsPToT.add(arc);
		}
	}
	
	/**
	 * Fonction permettant de retirer un arc reliant une place avec une transition,
	 * si on retire un arc il faut indiquer � la transition qu'elle n'est plus reli� � la place correspondante
	 * @throws NotExistArcException 
	 */
	public void removeArcPToT(ArcPlaceToTransition arc) throws NotExistArcException {
		if(arcsPToT.contains(arc))
		{
			this.arcsPToT.remove(arc);
			for (Transition transition: transitions) { //on retire l'arc de la liste des arcs de la transition
				for (ArcPlaceToTransition arcTransition: transition.getArcsEntrants()) {
					if (arc == arcTransition) {
						transition.removeArcEntrant(arc);
					}
				}
			}	
		}
		else
		{
			throw new NotExistArcException();
		}
	}
	
	/**2 Fonctions permettant d'ajouter une transition
	 * On peut d�cider d'ajouter une transition cr�� pr�alablement.
	 **/
	public void addTransition() {
		Transition transition = new Transition();
		this.transitions.add(transition);
	}
	
	public void addTransition(Transition t)
	{
		this.transitions.add(t);
	}
	
	/**Fonction permettant de retirer une transition, ainsi que tous les arcs qui lui sont associ�s
	 * @throws NotExistTransitionException 
	 * @throws NotExistArcException **/
	public void removeTransition(Transition transition) throws NotExistTransitionException, NotExistArcException {
		if(transitions.contains(transition))
		{
			this.transitions.remove(transition);
			for (ArcPlaceToTransition arc: transition.getArcsEntrants()) {
				removeArcPToT(arc);
			}
			for (ArcTransitionToPlace arc: transition.getArcsSortants()){
				removeArcTToP(arc);
			}	
		}
		else
		{
			throw new NotExistTransitionException();
		}
		
	}
	
	/**Fonction permettant d'ajouter un jeton � une place donn�e en param�tre.**/
	public void addTokens(Place place, int tokens) {
		place.addTokens(tokens);
	}
	
	/**Fonction permettant de retirer des jetons � une place donn�e en param�tre.**/
	public void removeTokens(Place place, int tokens) {
		place.removeTokens(tokens);
	}
	
	/** La fonction PUllAll() permet de r�aliser la premi�re �tape dans un r�seau de P�tri,
	 *  celle o� l'on active les transitions, et ou on tire les jetons des places.
	 */
	public void PullAll() {
		Collections.shuffle(transitions); //on m�lange les �l�ments de transitions pour pouvoir les parcourrir de mani�re al�atoire et permettre la concurrence
		for (Transition transition : transitions) {
			if(transition.pullTransition()) // pullTransition tire les jetons si la transition est activable
				transitionsActivated.add(transition);
		}
	}
	
	/** La fonction FillInAll() permet de r�aliser la deuxi�me �tape dans un r�seau de P�tri,
	 *  elle o� l'on remplit les places � partir des transitions activ�es
	 */
	public void FillInAll() {
		for (Transition transition : transitionsActivated) {
			transition.fillInTransition();
			transitionsActivated.remove(transition);
		}
	}
	
	/**
	 * On d�finit maintenant des accesseurs utilis�s dans les tests
	 */
	
	public LinkedList<Place> getPlaces(){
		return this.places;
	}
	
	public LinkedList<Transition> getTransition(){
		return this.transitions;
	}
	
	public LinkedList<Transition> getTransitionActivated(){
		return this.transitionsActivated;
	}
	
	public LinkedList<ArcTransitionToPlace> getArcTToP(){
		return this.arcsTToP;
	}
	
	public LinkedList<ArcPlaceToTransition> getArcPToT(){
		return this.arcsPToT;
	}
	
	public static void main(String[] a)
	{
		Petrinet pn = new Petrinet();
		Place p = new Place();
		try {
			pn.removePlace(p);
		} catch (NotExistPlaceException e) {
			System.out.println("L'exception NotExistPlaceException est lev�e");
		} catch (NotExistArcException e) {
			System.out.println("L'exception NotExistArcException est lev�e");
		}
	}
}

















