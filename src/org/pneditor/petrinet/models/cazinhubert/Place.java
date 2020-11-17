package org.pneditor.petrinet.models.cazinhubert;


/**
 * La classe Place g�re le fonctionnement des places du Petrinet, notamment la gestion du nombre de jetons dans la place
 * 
 * @author Corentin Hubert, R�mi Cazin
 *
 */
public class Place {

	private int Tokens;
	private String name;
	private static int nb_Places;
	
	public Place(int nbTokensInit) throws NegativeTokensException
	{
		/** Ce constructeur initialise le nombre de jetons dans la place **/
		if(nbTokensInit>=0)
		{
			this.Tokens = nbTokensInit;
			nb_Places++;
			this.name = "Place_" + nb_Places;
		}
		else
			throw new NegativeTokensException();
		
	}
	
	public Place()
	{
		/** Ce constructeur initialise le nombre de jetons � 0 */
		this.Tokens = 0;
		nb_Places++;
		this.name = "Place_"+ nb_Places;
	}
	
	public Place(int TokensInit, String nameInit) throws NegativeTokensException
	{
		/** Ce constructeur initialise les attributs de la place en entr�e **/
		if(TokensInit>=0)
		{
			this.Tokens = TokensInit;
			nb_Places++;
			this.name = nameInit;	
		}
		else
			throw new NegativeTokensException();
		
		
	}
	
	public void addTokens(int newTokens)
	{
		/** permet d'ajouter des jetons dans la place **/
		if(newTokens>=0)
		{
			this.Tokens += newTokens;
		}
		else
		{
			this.removeTokens(-newTokens);
		}
		
	}
	
	public void removeTokens(int nbTokens)
	{
		if(nbTokens<=0)
		{
			this.addTokens(-nbTokens);
		}
		else
		{
			/** permet de retirer des jetons de la place, si il n'y a pas suffisamment de jetons, on retire tout **/
			if (this.Tokens < nbTokens) {
				this.Tokens = 0;
			}
			else
			{
				this.Tokens -= nbTokens;
			}
			
		}
		
	}
	
	public int getNbToken()
	{
		/** Retourne le nombre de jetons actuellement dans la place **/
		return this.Tokens;
	}
	
	public String getName()
	{
		/** retourne le nom de la place **/
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean equals(Object obj)
	{
		if(this.getClass()!=obj.getClass())
			return false;
		else
		{
			Place p = (Place)obj;
			if(this.name!=p.name)
				return false;
			if(this.Tokens!=p.Tokens)
				return false;
			return true;
		}
	}
}
