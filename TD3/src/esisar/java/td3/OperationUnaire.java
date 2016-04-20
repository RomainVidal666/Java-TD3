package esisar.java.td3;

/**
 * @author Romain Operation avec une seule operande.
 *
 */
public abstract class OperationUnaire implements Expression {

	protected Expression exp;
	
	/**
	 * @param exp L'expression sur laquelle effectuer l'operantion.
	 */
	public OperationUnaire(Expression exp) {
		super();
		this.exp = exp;
	}
	
	@Override
	public abstract double valeur();

}
