package esisar.java.td3;

/**
 * @author Romain
 *
 * Une operation a deux operande
 */
public abstract class OperationBinaire implements Expression {

	protected Expression exp1;
	protected Expression exp2;

	/**
	 * Creer une operation binaire contenant deux expressions
	 * 
	 * @param exp1 Expression gauche
	 * @param exp2 Expression droite
	 */
	public OperationBinaire(Expression exp1, Expression exp2) {
		super();
		this.exp1 = exp1;
		this.exp2 = exp2;
	}
	
	@Override
	public abstract double valeur();

}
