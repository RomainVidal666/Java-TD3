package esisar.java.td3;

/**
 * @author Romain Une variable
 *
 */
public class Variable implements Expression {

	private double valeur;
	private String nom;
	
	/**
	 * Creer une variable.
	 * 
	 * @param nom Le nom
	 */
	public Variable(String nom) {
		super();
		this.nom = nom;
	}
	
	@Override
	public double valeur() {
		return valeur;
	}

	/**
	 * @return le nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * @param valeur La valeur
	 */
	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	@Override
	public String toString() {
		return nom;
	}
}
