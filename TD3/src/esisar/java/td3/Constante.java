package esisar.java.td3;

/**
 * @author Romain Une constante
 *
 */
public class Constante implements Expression {

	private double valeur;
	
	/**
	 * @param valeur Le valeur de la constante.
	 */
	public Constante(double valeur) {
		super();
		this.valeur = valeur;
	}
	
	@Override
	public double valeur() {
		return valeur;
	}

	@Override
	public String toString() {
		return "" + valeur;
	}

}
